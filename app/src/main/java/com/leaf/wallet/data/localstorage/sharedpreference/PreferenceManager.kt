package com.leaf.wallet.data.localstorage.sharedpreference


/**
 * PreferenceManager
 * Used to persist key value pairs to local storage.
 */
interface PreferenceManager {
    /**
     * storeBitcoin
     * @param bitcoinValue[String]
     * stores bitcoin value
     */
    suspend fun storeBitcoin(bitcoinValue: String)

    /**
     * retrieveBitcoin
     * retrieves bitcoin value from local storage.
     * @return bitcoinValue[Double]
     */
    suspend fun retrieveBitcoin(): Double

    /**
     * isInitialSetupComplete
     * retrieves flag value to signify if the initial setup of the app is complete.
     * @return [Boolean]
     */
    suspend fun isInitialSetupComplete(): Boolean

    /**
     * initialSetupComplete
     * stores flag that will signify if the initial setup of the app is complete.
     * @param completed [Boolean]
     */
    suspend fun initialSetupComplete(completed: Boolean)
}