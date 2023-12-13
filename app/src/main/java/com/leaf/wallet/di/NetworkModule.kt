package com.leaf.wallet.di

import android.content.Context
import com.leaf.wallet.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    @Named("HttpConnectTimeout")
    fun providesHttpConnectTimeout(@ApplicationContext applicationContext: Context): Int {
        return applicationContext.resources
            .getInteger(R.integer.http_connect_timeout_in_seconds)
    }

    @Provides
    @Singleton
    @Named("HttpReadTimeout")
    fun providesHttpReadTimeout(@ApplicationContext applicationContext: Context): Int {
        return applicationContext.resources
            .getInteger(R.integer.http_read_timeout_in_seconds)
    }

    @Provides
    @Singleton
    @Named("HttpWriteTimeout")
    fun providesHttpWriteTimeout(@ApplicationContext applicationContext: Context): Int {
        return applicationContext.resources
            .getInteger(R.integer.http_write_timeout_in_seconds)
    }

    @Provides
    @Singleton
    fun providesHttpLoggingInterceptor(): Interceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    @Provides
    @Singleton
    fun providesAuthInterceptor(): AuthInterceptor {
        return AuthInterceptor()
    }

    @Provides
    @Singleton
    fun providesHttpClient(
        @ApplicationContext applicationContext: Context,
        httpLoggingInterceptor: Interceptor,
        authInterceptor: AuthInterceptor,
        @Named("HttpConnectTimeout") connectTimeout: Int,
        @Named("HttpReadTimeout") readTimeout: Int,
        @Named("HttpWriteTimeout") writeTimeout: Int
    ): OkHttpClient {
        val cacheSize = (5 * 1024 * 1024).toLong()
        val myCache = Cache(applicationContext.cacheDir, cacheSize)
        val builder = OkHttpClient.Builder()
            .cache(myCache)
            .connectTimeout(connectTimeout.toLong(), TimeUnit.SECONDS)
            .readTimeout(readTimeout.toLong(), TimeUnit.SECONDS)
            .writeTimeout(writeTimeout.toLong(), TimeUnit.SECONDS)
            .addInterceptor(authInterceptor)
            .addInterceptor(httpLoggingInterceptor)
        return builder.build()
    }

    @Provides
    @Singleton
    fun providesRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.apilayer.com/fixer/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}