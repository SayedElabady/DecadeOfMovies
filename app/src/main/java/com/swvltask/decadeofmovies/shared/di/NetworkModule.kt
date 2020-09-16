package com.swvltask.decadeofmovies.shared.di

import android.content.Context
import com.google.gson.Gson
import com.swvltask.decadeofmovies.shared.Constants
import com.swvltask.decadeofmovies.shared.store.sources.api.CachePolicy
import com.swvltask.decadeofmovies.shared.store.sources.api.IFlickrService
import com.swvltask.decadeofmovies.shared.store.sources.api.JsonInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {

    single { CachePolicy() }
    single { Gson() }
    single { JsonInterceptor() }

    single<Retrofit> {
        val context: Context = get()
        val cachePolicy: CachePolicy = get()
        val gson: Gson = get()
        val jsonInterceptor: JsonInterceptor = get()
        val loggerInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val cacheSize = cachePolicy.provideCacheSize()
        val dir = context.cacheDir
        val cache = Cache(dir, cacheSize.toLong())

        val client = OkHttpClient.Builder()
            .addInterceptor(jsonInterceptor)
            .addInterceptor(loggerInterceptor)
            .cache(cache)
            .build()

        Retrofit.Builder()
            .baseUrl(Constants.FLICKR_BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
    }

    single<IFlickrService> {
        val retrofit: Retrofit = get()

        retrofit.create(IFlickrService::class.java)
    }
}