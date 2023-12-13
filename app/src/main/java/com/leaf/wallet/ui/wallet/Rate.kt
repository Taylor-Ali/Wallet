package com.leaf.wallet.ui.wallet

import android.os.Parcel
import android.os.Parcelable

/**
 * Rate
 * data class used to model a single Rate of one Currency the base currency is being converted to.
 * @param countryLocale [String] the locale of that country.
 * @param currencyName [String] three letter symbol representing that countries currency eg.USD
 * @param rateValue [Double] represents one single unit of the base currency converted into the another.
 * @param conversionRateAmount [Double] represents the the total amount base currency multiplied rateValue.
 */
data class Rate(
    val countryLocale: String,
    val currencyName: String,
    val rateValue: Double,
    val conversionRateAmount: Double
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readDouble(),
        parcel.readDouble()
    ) {
    }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(p0: Parcel, p1: Int) {
        TODO("Not yet implemented")
    }

    companion object CREATOR : Parcelable.Creator<Rate> {
        override fun createFromParcel(parcel: Parcel): Rate {
            return Rate(parcel)
        }

        override fun newArray(size: Int): Array<Rate?> {
            return arrayOfNulls(size)
        }
    }

}