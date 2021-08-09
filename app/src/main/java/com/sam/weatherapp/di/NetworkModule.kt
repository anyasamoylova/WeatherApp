package com.sam.weatherapp.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.sam.weatherapp.BuildConfig
import com.sam.weatherapp.network.AppInterceptor
import com.sam.weatherapp.network.RetrofitClient
import com.sam.weatherapp.network.WeatherApiInterface
import dagger.Module
import dagger.Provides
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

@Module
abstract class NetworkModule {
    companion object {
        @Provides
        fun provideOkHttpClient(): OkHttpClient {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            return OkHttpClient.Builder()
                .addInterceptor(AppInterceptor())
                .addInterceptor(loggingInterceptor)
                .build()
        }

        @Provides
        fun provideRetrofitClient(client: OkHttpClient): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(RetrofitClient.json.asConverterFactory(RetrofitClient.contentType))
                .client(client)
                .build()
        }

        @Provides
        fun provideApiService(retrofit: Retrofit): WeatherApiInterface {
            return retrofit.create(WeatherApiInterface::class.java)
        }
    }
}