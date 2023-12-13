package com.leaf.wallet.di

import android.content.Context
import android.content.SharedPreferences
import com.leaf.wallet.data.localstorage.sharedpreference.PreferenceManager
import com.leaf.wallet.data.localstorage.sharedpreference.PreferenceManagerImpl
import com.leaf.wallet.data.remote.BitcoinWalletServices
import com.leaf.wallet.repository.ForeignExchangeRatesRepository
import com.leaf.wallet.repository.ForeignExchangeRatesRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AndroidModule {
    private const val SHARED_PREFERENCES_NAME = "WalletApp"

    @Provides
    @Singleton
    fun providesSharedPreferences(@ApplicationContext appContext: Context): SharedPreferences {
        return appContext.getSharedPreferences(
            SHARED_PREFERENCES_NAME,
            Context.MODE_PRIVATE
        )
    }
    @Provides
    @Singleton
    fun providesBitcoinWalletServices(retrofit: Retrofit): BitcoinWalletServices {
        return retrofit.create(BitcoinWalletServices::class.java)
    }

    @Provides
    @Singleton
    fun providesBitcoinStorage(sharedPreferences: SharedPreferences): PreferenceManager {
        return PreferenceManagerImpl(sharedPreferences)
    }

    @Provides
    @Singleton
    fun providesForeignExchangeRatesRepository(bitcoinWalletServices: BitcoinWalletServices): ForeignExchangeRatesRepository {
        return ForeignExchangeRatesRepositoryImpl(bitcoinWalletServices)
    }

}
