package com.leaf.wallet.data.remote.model

import com.google.gson.annotations.SerializedName

/**
 * CurrencyFluctuations
 * Response class used to get the fluctuations data returned from the /fluctuations endpoint
 * @param startRate [Double] represents the initial rate the base currency was at.
 * @param endRate [String] represents the current rate the base currency is at.
 * @param change represents the fluctuation change in units.
 * @param changePCT represents the fluctuations percentage.
 */
data class CurrencyFluctuations(
    @SerializedName("start_rate")
    val startRate: Double,
    @SerializedName("end_rate")
    val endRate: Double,
    @SerializedName("change")
    val change: Double,
    @SerializedName("change_pct")
    val changePCT: Double,
)