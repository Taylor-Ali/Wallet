package com.leaf.wallet.ui.setup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leaf.wallet.data.localstorage.sharedpreference.PreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SetupViewModel @Inject constructor(private val preferenceManager: PreferenceManager) :
    ViewModel() {

    private val bitCoinValueMutableLiveData = MutableLiveData<Double>()
    val bitCoinValueLiveData: LiveData<Double> = bitCoinValueMutableLiveData

    /**
     * storeBitcoinValue
     * stores bitcoin value.
     * @param bitcoinValue
     */
    fun storeBitcoinValue(bitcoinValue: String) {
        viewModelScope.launch(Dispatchers.IO) {
            preferenceManager.storeBitcoin(bitcoinValue)
        }
    }

    /**
     * retrieveStoredBitcoinValue
     * retrieves bitcoin value and updates live data.
     * bitcoinValue
     */
    fun retrieveStoredBitcoinValue() {
        viewModelScope.launch(Dispatchers.IO) {
            bitCoinValueMutableLiveData.postValue(preferenceManager.retrieveBitcoin())
        }
    }

    /**
     * completeInitialSetup
     * once the bitcoin value is added setup now is complete
     * and a flag gets stored in local storage.
     * @param isSetupComplete
     */
    fun completeInitialSetup(isSetupComplete: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            preferenceManager.initialSetupComplete(isSetupComplete)
        }
    }


    /**
     * validateBitCoinsAddedIsValid
     * value dates string value coming from the text field.
     * @param valueSupplied
     * @return [Boolean] if value supplied is valid
     */
    fun validateBitCoinsAddedIsValid(valueSupplied: String): Boolean {
        val regex = REGEX_PATTERN.toRegex()
        return valueSupplied.matches(regex) && valueSupplied.isNotEmpty()
    }

    companion object {
        const val REGEX_PATTERN = "-?[0-9]+(\\.[0-9]+)?"
    }
}