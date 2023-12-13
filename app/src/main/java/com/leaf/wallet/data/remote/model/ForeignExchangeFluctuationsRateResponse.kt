package com.leaf.wallet.data.remote.model

import com.google.gson.annotations.SerializedName

/**
 * ForeignExchangeFluctuationsRateResponse
 * Response class used to get the fluctuations data returned from the /fluctuations endpoint
 * @param success [Boolean] true of false if the api call was success or not.
 * @param base [String] represents the base currency.
 * @param fluctuation [Boolean] displays if there was any fluctuation between the start and end date.
 * @param startDate [String] date range given to the api to get the fluctuation data.
 * @param endDate [String] date range given to the api to get the fluctuation data.
 */
data class ForeignExchangeFluctuationsRateResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("base")
    val base: String,
    @SerializedName("fluctuation")
    val fluctuation: Boolean,
    @SerializedName("start_date")
    val startDate: String?,
    @SerializedName("end_date")
    val endDate: String?,
    @SerializedName("rates")
    val rates: Any
)