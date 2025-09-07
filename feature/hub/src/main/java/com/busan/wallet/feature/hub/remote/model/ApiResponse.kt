package com.busan.wallet.feature.hub.remote.model

import com.google.gson.annotations.SerializedName

/**
 * Generic API response wrapper from Hub server
 */
data class ApiResponse<T>(
    @SerializedName("success")
    val success: Boolean,
    
    @SerializedName("message")
    val message: String,
    
    @SerializedName("data")
    val data: T?
)