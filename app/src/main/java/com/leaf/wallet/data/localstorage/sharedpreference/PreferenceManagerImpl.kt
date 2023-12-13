package com.leaf.wallet.data.localstorage.sharedpreference

import android.content.SharedPreferences

class PreferenceManagerImpl(private val sharedPreferences: SharedPreferences) : PreferenceManager {

    override suspend fun storeBitcoin(bitcoinValue: String) {
        val sharedPreferencesEditor = sharedPreferences.edit()
        sharedPreferencesEditor.putString(BITCOIN_VALUE_KEY, bitcoinValue).apply()
    }

    override suspend fun retrieveBitcoin(): Double {
        val bitcoinValue = sharedPreferences
            .getString(BITCOIN_VALUE_KEY,"")?:""

        return if(bitcoinValue.isEmpty()) 0.0 else bitcoinValue.toDouble()
    }

    override suspend fun isInitialSetupComplete(): Boolean {
        return sharedPreferences
            .getBoolean(SETUP_COMPLETE, false)
    }

    override suspend fun initialSetupComplete(completed: Boolean) {
        val sharedPreferencesEditor = sharedPreferences.edit()
        sharedPreferencesEditor.putBoolean(SETUP_COMPLETE, completed).apply()
    }

    companion object {
        const val BITCOIN_VALUE_KEY = "BITCOIN_VALUE_KEY"
        const val SETUP_COMPLETE = "SETUP_COMPLETE"
    }
}
