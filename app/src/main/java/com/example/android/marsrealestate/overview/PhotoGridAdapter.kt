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

package com.example.android.marsrealestate.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.marsrealestate.databinding.GridViewItemBinding
import com.example.android.marsrealestate.network.MarsProperty

class MarsPropertyAdapter(private val marsPropertyListener: MarsPropertyListener)
    : ListAdapter<MarsProperty, MarsPropertyAdapter.MarsPropertyViewHolder>(MarsPropertyDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MarsPropertyViewHolder.from(parent)


    override fun onBindViewHolder(holder: MarsPropertyViewHolder, position: Int) {
        val marsItem = getItem(position)
        holder.bind(marsItem, marsPropertyListener)
    }

    class MarsPropertyViewHolder private constructor(private val binding: GridViewItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MarsProperty, marsPropertyListener: MarsPropertyListener) {
            binding.marsProperty = item
//            binding.marsImage.setOnClickListener{marsPropertyListener.onClick(item)}
            binding.marsPropertyClickListener = marsPropertyListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MarsPropertyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = GridViewItemBinding.inflate(
                        layoutInflater, parent, false)

                return MarsPropertyViewHolder(binding)
            }
        }
    }

}

class MarsPropertyListener(val clickCallback: (marsProperty: MarsProperty) -> Unit) {
    fun onClick(marsProperty: MarsProperty) = clickCallback(marsProperty)
}

class MarsPropertyDiffCallback : DiffUtil.ItemCallback<MarsProperty>() {
    override fun areItemsTheSame(oldItem: MarsProperty, newItem: MarsProperty) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: MarsProperty, newItem: MarsProperty) = oldItem == newItem
}
