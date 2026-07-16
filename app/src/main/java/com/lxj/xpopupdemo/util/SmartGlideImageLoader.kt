package com.lxj.xpopupdemo.util

import android.content.Context
import android.graphics.Matrix
import android.graphics.PointF
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View
import android.view.View.OnLongClickListener
import android.widget.ImageView
import android.widget.ProgressBar
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.davemorrissey.labs.subscaleview.ImageSource
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView.DefaultOnStateChangedListener
import com.lxj.xpopup.core.ImageViewerPopupView
import com.lxj.xpopup.interfaces.XPopupImageLoader
import com.lxj.xpopup.photoview.OnMatrixChangedListener
import com.lxj.xpopup.photoview.PhotoView
import com.lxj.xpopup.util.XPopupUtils
import java.io.File

/**
 * 支持加载超长，超大的图片，你能OOM就算我输！！！
 * 注意：默认不支持超大超长图片加载，如8000x10000，传入bigImage为true时则使用SubsamplingScaleImageView加载大图；
 * SubsamplingScaleImageView虽然能加载超大图，但是加载Gif的时候不会动
 */
class SmartGlideImageLoader : XPopupImageLoader {
    private var errImg = 0
    private var mBigImage = false

    constructor()

    constructor(errImgRes: Int) {
        errImg = errImgRes
    }

    constructor(bigImage: Boolean, errImgRes: Int) : this(errImgRes) {
        mBigImage = bigImage
    }

    override fun loadImage(
        position: Int, url: Any, popupView: ImageViewerPopupView,
        snapshot: PhotoView, progressBar: ProgressBar
    ): View {
        progressBar.setVisibility(View.VISIBLE)
        val imageView = if (mBigImage)
            buildBigImageView(popupView, progressBar, position)
        else
            buildPhotoView(popupView, snapshot, position)
        val context = imageView.getContext()
        if (snapshot.getDrawable() != null && (snapshot.getTag() as Int) == position) {
            if (imageView is PhotoView) {
                try {
                    imageView.setImageDrawable(
                        snapshot.getDrawable().getConstantState()!!.newDrawable()
                    )
                } catch (e: Exception) {
                }
            } else {
                XPopupUtils.view2Bitmap(snapshot)?.let {
                    (imageView as SubsamplingScaleImageView).setImage(ImageSource.bitmap(it))
                }
            }
        }
        Glide.with(imageView).downloadOnly().load(url)
            .into(object : ImageDownloadTarget() {
                public override fun onLoadFailed(errorDrawable: Drawable?) {
                    super.onLoadFailed(errorDrawable)
                    progressBar.setVisibility(View.GONE)
                    if (imageView is PhotoView) {
                        imageView.setImageResource(errImg)
                        imageView.isZoomable = true
                    } else {
                        (imageView as SubsamplingScaleImageView).setImage(
                            ImageSource.resource(
                                errImg
                            )
                        )
                    }
                }

                public override fun onResourceReady(
                    resource: File,
                    transition: Transition<in File?>?
                ) {
                    super.onResourceReady(resource, transition)
                    val maxW = XPopupUtils.getAppWidth(context) * 2
                    val maxH = XPopupUtils.getScreenHeight(context) * 2

                    val size = XPopupUtils.getImageSize(resource)
                    val degree = XPopupUtils.getRotateDegree(resource.getAbsolutePath())
                    //photo view加载
                    if (imageView is PhotoView) {
                        progressBar.setVisibility(View.GONE)
                        imageView.isZoomable = true
                        if (size[0] > maxW || size[1] > maxH) {
                            //TODO: 可能导致大图GIF展示不出来
                            val rawBmp = XPopupUtils.getBitmap(resource, maxW, maxH)
                            rawBmp?.let {
                                imageView.setImageBitmap(
                                    XPopupUtils.rotate(
                                    it,
                                    degree,
                                    size[0] / 2f,
                                    size[1] / 2f
                                    )
                                )
                            }
                            //                                Glide.with(imageView).load(rawBmp)
//                                        .apply(new RequestOptions().error(errImg).override(size[0], size[1])).into(((PhotoView) imageView));
                        } else {
                            Glide.with(imageView).load(resource)
                                .apply(RequestOptions().error(errImg).override(size[0], size[1]))
                                .into(
                                    imageView
                                )
                        }
                    } else {
                        //大图加载
                        val bigImageView = imageView as SubsamplingScaleImageView
                        var longImage = false
                        if (size[1] * 1f / size[0] > XPopupUtils.getScreenHeight(context) * 1f / XPopupUtils.getAppWidth(
                                context
                            )
                        ) {
                            longImage = true
                            bigImageView.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_START)
                        } else {
                            longImage = false
                            bigImageView.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CENTER_INSIDE)
                        }

                        val s2 = size[0] * size[1]
                        //优化点击缩放
                        if (s2 != 0) {
                            val i = XPopupUtils.getScreenWidth(context) * XPopupUtils.getAppWidth(
                                context
                            ) / s2
                            if (i > 0) bigImageView.setDoubleTapZoomDpi(320 / i)
                        }

                        bigImageView.setOrientation(degree)
                        bigImageView.setOnImageEventListener(
                            SSIVListener(
                                bigImageView,
                                progressBar,
                                errImg,
                                longImage,
                                resource
                            )
                        )
                        val preview = XPopupUtils.getBitmap(
                            resource,
                            XPopupUtils.getAppWidth(context),
                            XPopupUtils.getScreenHeight(context)
                        )
                        bigImageView.setImage(
                            ImageSource.uri(Uri.fromFile(resource)).dimensions(size[0], size[1]),
                            if (preview != null) ImageSource.cachedBitmap(preview) else null
                        )
                    }
                }
            })
        return imageView
    }

    private fun buildBigImageView(
        popupView: ImageViewerPopupView,
        progressBar: ProgressBar?,
        realPosition: Int
    ): SubsamplingScaleImageView {
        val ssiv = SubsamplingScaleImageView(popupView.getContext())
        ssiv.setMinimumDpi(1)
        ssiv.setMaximumDpi(320)
        ssiv.setDoubleTapZoomDuration(250)
        ssiv.setDoubleTapZoomStyle(SubsamplingScaleImageView.ZOOM_FOCUS_CENTER)
        ssiv.setOnStateChangedListener(object : DefaultOnStateChangedListener() {
            override fun onCenterChanged(newCenter: PointF?, origin: Int) {
                super.onCenterChanged(newCenter, origin)
                //TODO 同步SubsamplingScaleImageView的滚动给snapshot
//                    Log.e("tag", "y: " + newCenter.y   + " vh: "+ ssiv.getMeasuredHeight()
//                    + "  dy: "+ (newCenter.y - ssiv.getMeasuredHeight()/2));
            }
        })
        ssiv.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                popupView.dismiss()
            }
        })
        if (popupView.longPressListener != null) {
            ssiv.setOnLongClickListener(object : OnLongClickListener {
                override fun onLongClick(v: View?): Boolean {
                    popupView.longPressListener?.onLongPressed(popupView, realPosition)
                    return false
                }
            })
        }
        return ssiv
    }

    private fun buildPhotoView(
        popupView: ImageViewerPopupView,
        snapshotView: PhotoView?,
        realPosition: Int
    ): PhotoView {
        val photoView = PhotoView(popupView.getContext())
        photoView.isZoomable = false
        photoView.setOnMatrixChangeListener(object : OnMatrixChangedListener {
            override fun onMatrixChanged(rect: RectF?) {
                if (snapshotView != null) {
                    val matrix = Matrix()
                    photoView.getSuppMatrix(matrix)
                    snapshotView.setSuppMatrix(matrix)
                }
            }
        })
        photoView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                popupView.dismiss()
            }
        })
        if (popupView.longPressListener != null) {
            photoView.setOnLongClickListener(object : OnLongClickListener {
                override fun onLongClick(v: View?): Boolean {
                    popupView.longPressListener?.onLongPressed(popupView, realPosition)
                    return false
                }
            })
        }
        return photoView
    }

    override fun loadSnapshot(uri: Any, snapshot: PhotoView, srcView: ImageView?) {
        if (mBigImage) {
            if (srcView != null && srcView.getDrawable() != null) {
                try {
                    snapshot.setImageDrawable(
                        srcView.getDrawable().getConstantState()!!.newDrawable()
                    )
                } catch (e: Exception) {
                }
            }
            Glide.with(snapshot).downloadOnly().load(uri)
                .into(object : ImageDownloadTarget() {
                    public override fun onLoadFailed(errorDrawable: Drawable?) {
                        super.onLoadFailed(errorDrawable)
                    }

                    public override fun onResourceReady(
                        resource: File,
                        transition: Transition<in File?>?
                    ) {
                        super.onResourceReady(resource, transition)
                        val degree = XPopupUtils.getRotateDegree(resource.getAbsolutePath())
                        val maxW = XPopupUtils.getAppWidth(snapshot.getContext())
                        val maxH = XPopupUtils.getScreenHeight(snapshot.getContext())
                        val size = XPopupUtils.getImageSize(resource)
                        if (size[0] > maxW || size[1] > maxH) {
                            //缩放加载
                            val rawBmp = XPopupUtils.getBitmap(resource, maxW, maxH)
                            rawBmp?.let {
                                snapshot.setImageBitmap(
                                    XPopupUtils.rotate(
                                    it,
                                    degree,
                                    size[0] / 2f,
                                    size[1] / 2f
                                    )
                                )
                            }
                        } else {
                            Glide.with(snapshot).load(resource)
                                .apply(RequestOptions().override(size[0], size[1])).into(snapshot)
                        }
                    }
                })
        } else {
            Glide.with(snapshot).load(uri).override(Target.SIZE_ORIGINAL).into(snapshot)
        }
    }


    override fun getImageFile(context: Context, uri: Any): File? {
        try {
            return Glide.with(context).downloadOnly().load(uri).submit().get()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}
