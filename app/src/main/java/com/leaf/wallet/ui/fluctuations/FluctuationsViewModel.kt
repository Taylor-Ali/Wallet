package com.leaf.wallet.ui.fluctuations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.leaf.wallet.R
import com.leaf.wallet.data.remote.NetworkResult
import com.leaf.wallet.data.remote.model.CurrencyFluctuations
import com.leaf.wallet.data.remote.model.ForeignExchangeFluctuationsRateResponse
import com.leaf.wallet.repository.ForeignExchangeRatesRepository
import com.leaf.wallet.ui.wallet.Rate
import com.leaf.wallet.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FluctuationsViewModel @Inject constructor(
    private val repository: ForeignExchangeRatesRepository
) : ViewModel() {

    private val currencyFluctuationsRateMutableLiveData = MutableLiveData<Fluctuation>()
    val currencyFluctuationsLiveData: LiveData<Fluctuation> =
        currencyFluctuationsRateMutableLiveData

    private val showErrorMessageMutableLiveData = MutableLiveData<Boolean>()
    val showErrorMessageLiveData: LiveData<Boolean> = showErrorMessageMutableLiveData


    /**
     * getFluctuationRate
     * gets the fluctuation rate of the base currency when converted to a currency.
     * it updates the live data
     * @param rate [Rate]
     */
    fun getFluctuationRate(rate: Rate) {
        val dateToday = Utils.getDateToday()
        val dateYesterday = Utils.getYesterdaysDate()
        val baseCurrency = "BTC"
        val convertedCurrency =
            Utils.formatAmountIntoCurrency(rate.countryLocale, rate.conversionRateAmount)

        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getForeignExchangeRatesFluctuations(
                dateYesterday,
                dateToday,
                baseCurrency,
                rate.currencyName
            )
            when (response) {
                is NetworkResult.Success -> {
                    val currencyFluctuations =
                        convertRawJSONToCurrencyFluctuations(response, rate.currencyName)

                    val fluctuation = Fluctuation(
                        baseCurrency = baseCurrency,
                        convertedCurrency = convertedCurrency,
                        startDate = dateYesterday,
                        endDate = dateToday,
                        change = Utils.formatAmountIntoCurrency(
                            rate.countryLocale,
                            currencyFluctuations.change
                        ),
                        changePercentage = Utils.formatDoubleToIntoPercentage(currencyFluctuations.changePCT),
                        startRate = Utils.formatAmountIntoCurrency(
                            rate.countryLocale,
                            currencyFluctuations.startRate
                        ),
                        endRate = Utils.formatAmountIntoCurrency(
                            rate.countryLocale,
                            currencyFluctuations.endRate
                        ),
                        countryLocale = rate.countryLocale,
                        imageIndicator =
                        when {
                            currencyFluctuations.changePCT > 0.0 -> R.drawable.baseline_trending_up_48
                            currencyFluctuations.changePCT == 0.0 -> R.drawable.baseline_trending_flat_48
                            else -> R.drawable.baseline_trending_down_48
                        }
                    )

                    currencyFluctuationsRateMutableLiveData.postValue(fluctuation)
                }

                is NetworkResult.Exception -> {
                    showErrorMessageMutableLiveData.postValue(true)
                }

                else -> {}
            }
        }
    }


    /**
     * convertRawJSONToCurrencyFluctuations
     * converts json with dynamics is into the [CurrencyFluctuations] object.
     * @param response
     * @param currencySymbol represent the key for deserialization of this particular object
     * @return [CurrencyFluctuations]
     */
    private fun convertRawJSONToCurrencyFluctuations(
        response: NetworkResult.Success<ForeignExchangeFluctuationsRateResponse>,
        currencySymbol: String
    ): CurrencyFluctuations {
        val rawRateString = Gson().toJson(response.data.rates)
        val jsonElement: JsonElement = Gson().fromJson(rawRateString, JsonElement::class.java)
            .asJsonObject
            .getAsJsonObject(currencySymbol)

        return Gson().fromJson(jsonElement, CurrencyFluctuations::class.java)
    }
}