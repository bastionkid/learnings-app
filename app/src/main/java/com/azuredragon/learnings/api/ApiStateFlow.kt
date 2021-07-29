package com.azuredragon.learnings.api

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

typealias ApiFlow<T> = Flow<ApiResponse<T>>

fun <T> apiStateFlowDefault(): MutableStateFlow<ApiResponse<T>> = MutableStateFlow(ApiResponse.Default)

fun <T> apiStateFlowInProgress(): MutableStateFlow<ApiResponse<T>> = MutableStateFlow(ApiResponse.InProgress)