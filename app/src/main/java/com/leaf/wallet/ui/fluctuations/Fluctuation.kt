package com.leaf.wallet.ui.fluctuations


/**
 * Fluctuation
 * data class used to display Fluctuation information
 * @param baseCurrency [String] is the base currency set the wallet holder in this case its BTC
 * @param convertedCurrency [String] this is symbol currency the user has selected
 * it has convert the bitcoin value into eg. dollars.
 * @param startRate [Double] represents the initial rate the base currency was at.
 * @param endRate [String] represents the current rate the base currency is at.
 * @param change [String] represents the fluctuation change in units.
 * @param imageIndicator [Int] visual indication of fluctions trends represent as a icon.
 * @param changePercentage [String] represents the fluctuations percentage.
 * @param countryLocale [String] used to get the currency of that country selected.
 */
data class Fluctuation(
    val baseCurrency: String,
    val convertedCurrency: String,
    val startDate: String,
    val endDate: String,
    val startRate: String,
    val endRate: String,
    val imageIndicator: Int,
    val change: String,
    val changePercentage: String,
    val countryLocale: String
)
