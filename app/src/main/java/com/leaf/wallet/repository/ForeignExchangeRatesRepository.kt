package com.leaf.wallet.repository


import com.leaf.wallet.data.remote.NetworkResult
import com.leaf.wallet.data.remote.model.ForeignExchangeFluctuationsRateResponse
import com.leaf.wallet.data.remote.model.ForeignExchangeResponse

/**
 * ForeignExchangeRatesRepository
 */
interface ForeignExchangeRatesRepository {
    /**
     * getLatestForeignExchangeRates
     * gets the latest foreign exchange rates
     * repository layer function which makes the api call and uses the network result wrapper to return a response
     * to the calling layer in this case it would be the viewModel.
     * @param base
     * @param symbols
     * @return [NetworkResult][ForeignExchangeResponse]
     */
    suspend fun getLatestForeignExchangeRates(base: String, symbols: String): NetworkResult<ForeignExchangeResponse>

    /**
     * getForeignExchangeRatesFluctuations
     * gets the latest foreign exchange rate fluctuations.
     * repository layer function which makes the api call and uses the network result wrapper to return a response
     * to the calling layer in this case it would be the viewModel.
     * @param startDate
     * @param endDate
     * @param base
     * @param symbols
     * @return [NetworkResult][ForeignExchangeFluctuationsRateResponse]
     */
    suspend fun getForeignExchangeRatesFluctuations(
        startDate: String,
        endDate: String,
        base: String,
        symbols: String
    ): NetworkResult<ForeignExchangeFluctuationsRateResponse>
}