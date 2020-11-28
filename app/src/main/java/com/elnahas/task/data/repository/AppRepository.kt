package com.elnahas.task.data.repository

import com.elnahas.task.data.api.ApiService
import com.elnahas.task.data.model.RawBodyModel
import javax.inject.Inject

class AppRepository @Inject constructor(val api: ApiService){

    suspend fun getRouteData() = api.getRoute(RawBodyModel("" , "bola_d" , "1234"))

    suspend fun getAboutUsData() = api.getAboutUs()

}