package defdeveu.lab.android.pinning.service

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit


object RetrofitFactory {
    fun createService(): Service {
        val okHttpClient = OkHttpClient().newBuilder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .addInterceptor(HttpLoggingInterceptor().also {
                    it.level = HttpLoggingInterceptor.Level.BASIC
                }).build()

        return Retrofit.Builder()
                .baseUrl("http://zs.labs.defdev.eu")
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(okHttpClient)
                .build().create(Service::class.java)
    }
}