package com.leaf.wallet.utils

import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


/**
 * Utils
 * Basic Utility class used for general purpose functions.
 */
object Utils {
    private const val DATE_FORMAT = "yyyy-MM-dd"
    private const val PERCENTAGE_SYMBOL = "%"
    private const val DECIMAL_FORMAT = "#.##"
    private const val LANGUAGE = "en"

    /**
     * getDateToday
     * returns date today in format yyyy-MM-dd.
     * @return [String]
     */
    fun getDateToday(): String {
        val simpleDateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
        val date = Date()
        return simpleDateFormat.format(date)
    }

    /**
     * getYesterdaysDate
     * returns yesterdays date in format yyyy-MM-dd.
     * @return [String]
     */
    fun getYesterdaysDate(): String {
        val yesterdayDate = Date().time - 24 * 60 * 60 * 1000
        val simpleDateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
        return simpleDateFormat.format(yesterdayDate)
    }

    /**
     * formatAmountIntoCurrency
     * @param countryLocale countries local [String]
     * @param value represented in [Double] to be formatted.
     * @return currency in [String]
     */
    fun formatAmountIntoCurrency(countryLocale: String, value: Double): String {
        return android.icu.text.NumberFormat.getCurrencyInstance(Locale(LANGUAGE, countryLocale))
            .format(value)
    }

    /**
     * formatAmountIntoCurrencyWithCustomSymbol
     * formats amount to decimal
     * @param value represented in [Double] to be formatted.
     * @return decimal in [String]
     */
    fun formatAmountIntoDecimal(value: Double): String {
        val dec = DecimalFormat("#,###.00")
        return  dec.format(value)
    }

    /**
     * formatDoubleToIntoPercentage
     * @param value of [Double] to be formatted.
     * @return percentage in [String] with percent symbol
     */
    fun formatDoubleToIntoPercentage(value: Double): String {
        val decimalFormat = DecimalFormat(DECIMAL_FORMAT)
        decimalFormat.roundingMode = RoundingMode.CEILING
        return decimalFormat.format(value) + PERCENTAGE_SYMBOL
    }
}