package com.example.nit3213connect.data.remote

import com.google.gson.JsonElement
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface Nit3213Api {

    @POST("{campus}/auth")
    suspend fun login(
        @Path("campus") campus: String,
        @Body body: LoginRequest
    ): LoginResponse

    @GET("dashboard/{keypass}")
    suspend fun getDashboard(@Path("keypass") keypass: String): DashboardResponse

    // Raw JSON fallback (object OR array)
    @GET("dashboard/{keypass}")
    suspend fun getDashboardRaw(@Path("keypass") keypass: String): JsonElement
}
