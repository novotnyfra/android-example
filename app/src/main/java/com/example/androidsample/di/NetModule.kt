package com.example.androidsample.di

import com.example.androidsample.BuildConfig
import com.example.androidsample.data.remote.ExampleApi
import com.example.androidsample.data.resource.LiveDataCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*

val netModule = module {

    // Moshi
    single { createMoshi() }

    // OKHttp ClientResource
    single { createOkHttpClient() }

    single { createAuthInterceptor() }

    // DemoApp API service
    single { createWebService<ExampleApi>(get(), get(), get(), BuildConfig.API_BASE_URL) }

}

fun createMoshi() = Moshi.Builder()
    // ... add your own JsonAdapters and factories ...
    .add(KotlinJsonAdapterFactory())
    .add(Date::class.java, Rfc3339DateJsonAdapter().nullSafe())
    .build()

fun createOkHttpClient() = OkHttpClient()

fun createAuthInterceptor()
        = Interceptor { chain ->
        val original = chain.request()
        val requestBuilder = original.newBuilder()

//        // TODO adjust to API
//        if (userSession.apiToken.isNotEmpty()) {
//            requestBuilder.addHeader("Authorization", apiToken)
//        }
        requestBuilder.addHeader("Accept", "application/json")
        requestBuilder.method(original.method(), original.body())

        chain.proceed(requestBuilder.build())
    }

inline fun <reified T> createWebService(okHttpClient: OkHttpClient, authInterceptor: Interceptor, moshi: Moshi, url: String): T {
    val httpClientBuilder = okHttpClient.newBuilder()

    httpClientBuilder.addInterceptor(authInterceptor)

    if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        httpClientBuilder.addInterceptor(loggingInterceptor)
    }

    return Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(LiveDataCallAdapterFactory())
        .callFactory(httpClientBuilder.build())
        .build().create(T::class.java)
}