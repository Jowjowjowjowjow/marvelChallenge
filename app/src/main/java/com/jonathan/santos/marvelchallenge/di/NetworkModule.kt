package com.jonathan.santos.marvelchallenge.di

import com.jonathan.santos.marvelchallenge.BuildConfig
import com.jonathan.santos.marvelchallenge.services.CharactersService
import com.jonathan.santos.marvelchallenge.util.CalculateHash
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val baseURL = "http://gateway.marvel.com/v1/public/"
val AUTH_INTERCEPTOR = named("AUTH_INTERCEPTOR")
val API_CACHE = named("API_CACHE")

val networkModule = module {

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<OkHttpClient> {
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(get(AUTH_INTERCEPTOR))
        builder.addInterceptor(get(API_CACHE))
        builder.build()
    }

    single<CharactersService> {
        get<Retrofit>().create(CharactersService::class.java)
    }

    single<Interceptor>(AUTH_INTERCEPTOR) {
        Interceptor { chain ->
            val originalUrl = chain.request().url()
            val currentTimeStamp = System.currentTimeMillis().toString()
            val newUrl =
                originalUrl.newBuilder().addQueryParameter(
                    "hash",
                    CalculateHash.calculateHash(
                        currentTimeStamp,
                        BuildConfig.PRIVATE_KEY,
                        BuildConfig.PUBLIC_KEY
                    )
                ).addQueryParameter(
                    "apiKey",
                    BuildConfig.PUBLIC_KEY
                ).addQueryParameter(
                    "ts",
                    currentTimeStamp
                ).toString()

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