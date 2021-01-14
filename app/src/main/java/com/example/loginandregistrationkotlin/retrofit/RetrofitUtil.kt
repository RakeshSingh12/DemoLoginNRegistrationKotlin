package com.example.loginandregistrationkotlin.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object RetrofitUtil {
    private var retrofit: Retrofit? = null
    //val BASE_URL = "http://13.232.62.239/solarapp/api/v1/"
   val LIVE_BASE_URL="https://oakridge.co.in/suntrack/api/v1/"



    fun getRetrofitInstance(): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                    .baseUrl(LIVE_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getInterceptor())
                    .build()
        }
        return retrofit!!
    }

    fun getInterceptor(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
//        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        var client = OkHttpClient.Builder().connectTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES)
                .writeTimeout(5, TimeUnit.MINUTES)
                .addInterceptor(interceptor)
                .build()
//        val okHttpClient = OkHttpClient.Builder()
//                .readTimeout(60, TimeUnit.SECONDS)
//                .connectTimeout(60, TimeUnit.SECONDS)
//                .build()

        return client as OkHttpClient
    }

}