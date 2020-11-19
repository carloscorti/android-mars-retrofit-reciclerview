/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.example.android.marsrealestate

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.android.marsrealestate.overview.FetchStatus

@BindingAdapter("imgSource")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
                .load(imgUri)
                .apply(RequestOptions()
                        .placeholder(R.drawable.loading_animation)
                        .error(R.drawable.ic_broken_image))
                .into(imgView)

    }
}

@BindingAdapter("marsApiStatus")
fun bindStatus(statusImg: ImageView, status: FetchStatus?) {
    status?.let {
        when(status) {
            FetchStatus.LOADING -> {
                statusImg.layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
                statusImg.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
                statusImg.visibility = View.VISIBLE
                statusImg.setImageResource(R.drawable.loading_animation)

            }

            FetchStatus.DONE -> {
                statusImg.visibility = View.GONE
            }

            FetchStatus.ERROR -> {
                statusImg.layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
                statusImg.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
                statusImg.visibility = View.VISIBLE
                statusImg.setImageResource(R.drawable.ic_connection_error)
            }
        }
    }
}