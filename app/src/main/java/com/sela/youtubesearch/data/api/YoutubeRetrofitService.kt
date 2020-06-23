package com.sela.youtubesearch.data.api

import com.squareup.moshi.Moshi
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


/**
 * Youtube Retrofit Service - make RestApi call by Retrofit
 */
object YoutubeRetrofitService {

    private const val KEY = "key"
    private const val BASE_URL = "https://www.googleapis.com/youtube/v3/"

    private val logInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val client = OkHttpClient.Builder().apply {
        addInterceptor(
            Interceptor { chain ->
                val original = chain.request()
                val originalHttpUrl: HttpUrl = original.url()

                val url = originalHttpUrl.newBuilder()
                    .addQueryParameter(KEY, YouTubeConfig.API_KEY)
                    .build()

                val request  = original.newBuilder()
                    .url(url).build()
                return@Interceptor chain.proceed(request)

            })
        addInterceptor(logInterceptor)
    }.build()


    private  val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .client(client)
    .build()

    fun<T> buildService(service: Class<T>): T{
        return retrofit.create(service)
    }


}