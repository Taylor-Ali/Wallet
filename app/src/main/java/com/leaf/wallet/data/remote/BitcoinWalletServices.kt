package com.leaf.wallet.data.remote

import com.leaf.wallet.data.remote.model.ForeignExchangeFluctuationsRateResponse
import com.leaf.wallet.data.remote.model.ForeignExchangeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * BitcoinWalletServices
 * Retrofit Services for getting the latest exchanges rates by the fixer api.
 * Please see https://apilayer.com/marketplace/fixer-api#documentation-tab documentation.
 */
interface BitcoinWalletServices {
    /**
     * getLatestForeignExchangeRates
     * Gets the latest exchange rates
     * @param base currency to compare against
     * @param symbols are the currency the base currency will get exchange rates against.
     * @return [Response][ForeignExchangeResponse]
     */
    @GET("latest")
    suspend fun getLatestForeignExchangeRates(
        @Query("base") base: String,
        @Query("symbols") symbols: String
    ): Response<ForeignExchangeResponse>

    /**
     * getForeignExchangeRatesFluctuations
     * Gets the latest exchange rate fluctuations based on a specific start and end date
     * @param startDate [String]
     * @param endDate [String]
     * @param base currency to compare against
     * @param symbols are the currency the base currency will get exchange rates against.
     * @return [Response][ForeignExchangeFluctuationsRateResponse]
     */
    @GET("fluctuation")
    suspend fun getForeignExchangeRatesFluctuations(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("base") base: String,
        @Query("symbols") symbols: String
    ): Response<ForeignExchangeFluctuationsRateResponse>
}