package com.leaf.wallet.ui.wallet


/**
 * ConversionRate
 * data class used to display ConversionRate information.
 * @param baseCurrency [String] is the base currency set the wallet holder in this case its BTC
 * @param baseCurrencyAmount [Double] represents how many units of the base the user currently has.
 * @param rates [List][Rate]
 */
data class ConversionRate(
    val baseCurrency: String,
    val baseCurrencyAmount: Double,
    val rates: List<Rate>
)

