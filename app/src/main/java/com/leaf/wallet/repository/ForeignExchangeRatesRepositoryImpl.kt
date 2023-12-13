package com.leaf.wallet.repository

import com.leaf.wallet.data.remote.ApiHandler
import com.leaf.wallet.data.remote.BitcoinWalletServices
import com.leaf.wallet.data.remote.NetworkResult
import com.leaf.wallet.data.remote.model.ForeignExchangeFluctuationsRateResponse
import com.leaf.wallet.data.remote.model.ForeignExchangeResponse


class ForeignExchangeRatesRepositoryImpl(
    private val bitcoinWalletServices: BitcoinWalletServices,
) : ForeignExchangeRatesRepository, ApiHandler {
    override suspend fun getLatestForeignExchangeRates(
        base: String,
        symbols: String
    ): NetworkResult<ForeignExchangeResponse> {
        return handleApi { bitcoinWalletServices.getLatestForeignExchangeRates(base, symbols) }
    }

    override suspend fun getForeignExchangeRatesFluctuations(
        startDate: String,
        endDate: String,
        base: String,
        symbols: String
    ): NetworkResult<ForeignExchangeFluctuationsRateResponse> {
        return handleApi {
            bitcoinWalletServices.getForeignExchangeRatesFluctuations(
                startDate,
                endDate,
                base,
                symbols
            )
        }
    }
}