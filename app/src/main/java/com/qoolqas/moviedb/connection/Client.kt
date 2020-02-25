package com.qoolqas.moviedb.connection

import com.loopj.android.http.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Client {
//    val api: String = BuildConfig.API_KEY
    val api:String = "8a9be9d05bd9f9d5ca154aaf42504303"
    fun getClient(): Retrofit {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client : OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()
        return Retrofit.Builder()
            .client(client)
            .baseUrl(api)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    fun getApi():Service{
        return getClient().create(Service::class.java)
    }
}