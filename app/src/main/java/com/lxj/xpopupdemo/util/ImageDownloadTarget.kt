/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2018 Piasy
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.lxj.xpopupdemo.util

import android.graphics.drawable.Drawable
import com.bumptech.glide.request.Request
import com.bumptech.glide.request.target.SizeReadyCallback
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.bumptech.glide.util.Util
import java.io.File

abstract class ImageDownloadTarget private constructor(
    private val width: Int,
    private val height: Int
) : Target<File?> {
    private var request: Request? = null

    protected constructor() : this(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)

    override fun onResourceReady(resource: File, transition: Transition<in File?>?) {
    }

    override fun onLoadCleared(placeholder: Drawable?) {
    }

    override fun onLoadStarted(placeholder: Drawable?) {
    }

    override fun onLoadFailed(errorDrawable: Drawable?) {
    }

    /**
     * Immediately calls the given callback with the sizes given in the constructor.
     *
     * @param cb {@inheritDoc}
     */
    override fun getSize(cb: SizeReadyCallback) {
        require(Util.isValidDimensions(width, height)) {
            ("Width and height must both be > 0 or Target#SIZE_ORIGINAL, but given" + " width: "
                    + width + " and height: " + height + ", either provide dimensions in the constructor"
                    + " or call override()")
        }
        cb.onSizeReady(width, height)
    }

    override fun removeCallback(cb: SizeReadyCallback) {
        // Do nothing, we never retain a reference to the callback.
    }

    override fun setRequest(request: Request?) {
        this.request = request
    }

    override fun getRequest(): Request? {
        return request
    }

    override fun onStart() {
        // Do nothing.
    }

    override fun onStop() {
        // Do nothing.
    }

    override fun onDestroy() {
        // Do nothing.
    }
}
