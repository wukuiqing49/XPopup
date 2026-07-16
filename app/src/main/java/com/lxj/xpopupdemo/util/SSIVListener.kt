package com.lxj.xpopupdemo.util

import android.view.View
import android.widget.ProgressBar
import com.davemorrissey.labs.subscaleview.ImageSource
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView.OnImageEventListener
import com.lxj.xpopup.util.XPopupUtils
import java.io.File

class SSIVListener(
    private val ssiv: SubsamplingScaleImageView,
    private val progressBar: ProgressBar,
    private val errorImage: Int,
    private val longImage: Boolean,
    private val resource: File?
) : OnImageEventListener {
    override fun onReady() {}

    override fun onImageLoaded() {
        progressBar.setVisibility(View.INVISIBLE)
        if (longImage) {
            ssiv.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_START)
        } else {
            ssiv.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CENTER_INSIDE)
        }
    }

    override fun onPreviewLoadError(e: Exception?) {}

    override fun onImageLoadError(e: Exception?) {
//        ssiv.animate().alpha(1f).setDuration(500).start();
//        e.printStackTrace();
        val bitmap =
            XPopupUtils.getBitmap(resource, ssiv.getMeasuredWidth(), ssiv.getMeasuredHeight())
        ssiv.setImage(
            if (bitmap == null) ImageSource.resource(errorImage) else ImageSource.bitmap(
                bitmap
            )
        )
        progressBar.setVisibility(View.INVISIBLE)
    }

    override fun onTileLoadError(e: Exception?) {}

    override fun onPreviewReleased() {}
}
