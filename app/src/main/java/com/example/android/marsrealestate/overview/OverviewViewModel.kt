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

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.marsrealestate.network.MarsApi
import com.example.android.marsrealestate.network.MarsProperty
import kotlinx.coroutines.*

/**
 * The [ViewModel] that is attached to the [OverviewFragment].
 */
class OverviewViewModel : ViewModel() {

    // The internal MutableLiveData String that stores the status of the most recent request
    private val _status = MutableLiveData<FetchStatus>()
    val status: LiveData<FetchStatus>
        get() = _status

    private val _marsProperties = MutableLiveData<List<MarsProperty>>()
    val marsProperties: LiveData<List<MarsProperty>>
        get() = _marsProperties

    private val coroutineScope = CoroutineScope(Dispatchers.Main + Job())

    /**
     * Call getMarsRealEstateProperties() on init so we can display status immediately.
     */
    init {
        getMarsRealEstateProperties()
    }

    /**
     * Sets the value of the status LiveData to the Mars API status.
     */
    private fun getMarsRealEstateProperties() {
        coroutineScope.launch {
//            val reqProperties = async { MarsApi.retrofitService.getProperties() }
            _status.value = FetchStatus.LOADING

            try {
//                _response.value = "Data size ${reqProperties.await().size}"

                val marsGroundList = MarsApi.retrofitService.getProperties()
//                _response.value = "Data size ${reqProperties.size}"
                if (marsGroundList.isNotEmpty()) {
                    _marsProperties.value = marsGroundList
                }
                _status.value = FetchStatus.DONE


            }catch (e: Exception) {
                _status.value = FetchStatus.ERROR
                _marsProperties.value = arrayListOf()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        coroutineScope.cancel()
    }
}

enum class FetchStatus {
    LOADING,
    DONE,
    ERROR
}