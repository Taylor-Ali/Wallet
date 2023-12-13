package com.leaf.wallet.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leaf.wallet.data.localstorage.sharedpreference.PreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(private val preferenceManager: PreferenceManager) :
    ViewModel() {

    private val setupCompleteMutableLiveData = MutableLiveData<Boolean>()
    val setupCompleteLiveData: LiveData<Boolean> = setupCompleteMutableLiveData

    /**
     * isInitialSetupComplete
     * checks to see if the initial setup of the app is complete
     * if setup has completed the app will perform when it opens up and redirect the user
     * redirect to the wallet screen so the user does not have to see the welcome screen on app start up.
     * it achieves this by checking the initial setup flag in local storage.
     * then it updates the view using live data a redirects the user to wallet screen.
     */
    fun isInitialSetupComplete() {
        viewModelScope.launch(Dispatchers.IO) {
            setupCompleteMutableLiveData.postValue(
                preferenceManager.isInitialSetupComplete()
            )
        }
    }
}