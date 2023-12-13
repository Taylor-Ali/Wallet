package com.leaf.wallet.data.remote.model

import com.google.gson.annotations.SerializedName

/**
 * Rate
 * Response class used to get the fluctuations data returned from the /latest endpoint
 * @param usd [Double] represents the usd currency symbol.
 * @param zar [Double] represents the zar currency symbol.
 * @param aud [Double] represents the aud currency symbol.
 */
data class Rate(
    @SerializedName("USD") val usd: Double,
    @SerializedName("ZAR") val zar: Double,
    @SerializedName("AUD") val aud: Double,
)