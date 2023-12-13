package com.leaf.wallet.data.remote.model

import com.google.gson.annotations.SerializedName

/**
 * ForeignExchangeResponse
 * Response class used to get the fluctuations data returned from the /latest endpoint
 * @param success [Boolean] true of false if the api call was success or not.
 * @param timestamp [Long] returns  timestamp of api call.
 * @param base [String] returns base currency.
 * @param date [String] return the date of when the api call was made.
 * @param rates [Rate] returns the rates of the currency symbols selected in the api call.
 */
data class ForeignExchangeResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("timestamp")
    val timestamp: Long?,
    @SerializedName("base")
    val base: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("rates")
    val rates: Rate,
)
