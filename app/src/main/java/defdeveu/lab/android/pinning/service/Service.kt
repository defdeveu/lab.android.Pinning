package defdeveu.lab.android.pinning.service

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface Service {
    @GET
    suspend fun getText(@Url url: String): Response<String>
}