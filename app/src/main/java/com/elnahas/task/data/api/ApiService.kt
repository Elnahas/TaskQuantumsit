package com.elnahas.task.data.api

import com.elnahas.task.data.model.RawBodyModel
import com.elnahas.task.data.model.UserModel
import com.elnahas.task.data.model.aboutus.AboutusModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST("account/checkCredentials")
    suspend fun getRoute(@Body rawBodyModel: RawBodyModel): Response<UserModel>

    @GET("aboutus/aboutUs")
    suspend fun getAboutUs(): Response<AboutusModel>
}