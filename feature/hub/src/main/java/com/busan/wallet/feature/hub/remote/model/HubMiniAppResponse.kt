package com.busan.wallet.feature.hub.remote.model

import com.google.gson.annotations.SerializedName

/**
 * Server response model for mini-app from Hub API
 */
data class HubMiniAppResponse(
    @SerializedName("appId")
    val appId: String,
    
    @SerializedName("fileName")
    val fileName: String,
    
    @SerializedName("type")
    val type: String, // "BLOCKCHAIN" or "APP"
    
    @SerializedName("createdAt")
    val createdAt: String,
    
    @SerializedName("name")
    val name: String,
    
    @SerializedName("version")
    val version: String,
    
    @SerializedName("iconUrl")
    val iconUrl: String? = null
)