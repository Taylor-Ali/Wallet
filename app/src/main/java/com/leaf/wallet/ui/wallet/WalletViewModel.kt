package com.leaf.wallet.ui.wallet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leaf.wallet.data.localstorage.sharedpreference.PreferenceManager
import com.leaf.wallet.data.remote.NetworkResult
import com.leaf.wallet.repository.ForeignExchangeRatesRepository
import com.leaf.wallet.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WalletViewModel @Inject constructor(
    private val preferenceManager: PreferenceManager,
    private val repository: ForeignExchangeRatesRepository
) : ViewModel() {

    private val bitCoinValueMutableLiveData = MutableLiveData<String>()
    val bitCoinValueLiveData: LiveData<String> = bitCoinValueMutableLiveData

    private val conversionRateMutableLiveData = MutableLiveData<ConversionRate>()
    val conversionLiveData: LiveData<ConversionRate> = conversionRateMutableLiveData

    private val showErrorMessageMutableLiveData = MutableLiveData<Boolean>()
    val showErrorMessageLiveData: LiveData<Boolean> = showErrorMessageMutableLiveData

    /**
     * retrieveStoredBitcoinValue
     * retrieves bitcoin value and updates live data.
     * bitcoinValue
     */
    fun getBitcoinValueFromStorage() {
        viewModelScope.launch(Dispatchers.IO) {
            val value = preferenceManager.retrieveBitcoin()
            val bitcoinValue = Utils.formatAmountIntoDecimal(value)
            bitCoinValueMutableLiveData.postValue(bitcoinValue)
        }
    }


    /**
     * getForeignExchangeRates
     * gets ForeignExchangeRates formats the data and updates live data.
     */
    fun getForeignExchangeRates() {
        viewModelScope.launch(Dispatchers.IO) {
            val bitcoinValue = preferenceManager.retrieveBitcoin()
            val response = repository.getLatestForeignExchangeRates(
                BASE_CURRENCY,
                "$USD_SYMBOL,$ZAR_SYMBOL, $AUD_SYMBOL"
            )

            when (response) {
                is NetworkResult.Success -> {
                    conversionRateMutableLiveData.postValue(
                        ConversionRate(
                            baseCurrency = BASE_CURRENCY,
                            baseCurrencyAmount = bitcoinValue,
                            listOf(
                                Rate(
                                    countryLocale = ZA_LOCALE,
                                    currencyName = ZAR_SYMBOL,
                                    rateValue = response.data.rates.zar,
                                    conversionRateAmount = bitcoinValue * response.data.rates.zar
                                ),
                                Rate(
                                    countryLocale = US_LOCALE,
                                    currencyName = USD_SYMBOL,
                                    rateValue = response.data.rates.usd,
                                    conversionRateAmount = bitcoinValue * response.data.rates.usd
                                ),
                                Rate(
                                    countryLocale = AUD_LOCALE,
                                    currencyName = AUD_SYMBOL,
                                    rateValue = response.data.rates.aud,
                                    conversionRateAmount = bitcoinValue * response.data.rates.aud
                                )
                            )
                        )
                    )
                }

                is NetworkResult.Exception -> {
                    showErrorMessageMutableLiveData.postValue(true)
                }

                else -> {}
            }
        }
    }


    companion object {
        const val BASE_CURRENCY = "BTC"
        const val USD_SYMBOL = "USD"
        const val ZAR_SYMBOL = "ZAR"
        const val AUD_SYMBOL = "AUD"
        const val AUD_LOCALE = "AU"
        const val US_LOCALE = "US"
        const val ZA_LOCALE = "ZA"
    }
}