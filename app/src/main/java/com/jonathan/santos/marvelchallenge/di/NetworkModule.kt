package com.jonathan.santos.marvelchallenge.di

import com.jonathan.santos.marvelchallenge.BuildConfig
import com.jonathan.santos.marvelchallenge.data.services.CharactersService
import com.jonathan.santos.marvelchallenge.util.CalculateHash
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val baseURL = "https://gateway.marvel.com/v1/public/"
val AUTH_INTERCEPTOR = named("AUTH_INTERCEPTOR")
val API_CACHE = named("API_CACHE")
val HTTP_LOGGING_QUALIFIER = named("HTTP_LOGGING_QUALIFIER")

val networkModule = module {

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(baseURL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<OkHttpClient> {
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(get(AUTH_INTERCEPTOR))
        builder.addInterceptor(get(API_CACHE))
        builder.addInterceptor(get(HTTP_LOGGING_QUALIFIER))
        builder.build()
    }

    single<CharactersService> {
        get<Retrofit>().create(CharactersService::class.java)
    }

    single<Interceptor>(HTTP_LOGGING_QUALIFIER) {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    single<Interceptor>(AUTH_INTERCEPTOR) {
        Interceptor { chain ->
            val originalUrl = chain.request().url()
            val currentTimeStamp = System.currentTimeMillis().toString()
            val newUrl =
                originalUrl.newBuilder().addQueryParameter(
                    "ts",
                    currentTimeStamp
                ).addQueryParameter(
                    "apikey",
                    BuildConfig.PUBLIC_KEY
                ).addQueryParameter(
            "hash",
            CalculateHash.calculateHash(
                currentTimeStamp,
                BuildConfig.PRIVATE_KEY,
                BuildConfig.PUBLIC_KEY
            )).toString()

            chain.proceed(chain.request().newBuilder().url(newUrl).build())
        }
    }

    single<Interceptor>(API_CACHE) {
        Interceptor { chain ->
            val originalResponse = chain.proceed(chain.request())
            val maxAge = 60 * 5
            originalResponse.newBuilder().header("Cache-Control", "public, max-age=$maxAge").build()
        }
    }


}