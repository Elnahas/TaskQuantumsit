package com.elnahas.task.viewmodels

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.elnahas.task.data.repository.AppRepository
import androidx.lifecycle.viewModelScope
import com.elnahas.task.data.model.UserModel
import com.elnahas.task.data.model.aboutus.AboutusModel
import com.elnahas.task.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel @ViewModelInject constructor(application: Application, private val repository: AppRepository) : AndroidViewModel(application) {

    val routeLiveData: MutableLiveData<Resource<UserModel>> = MutableLiveData()
    val aboutUsLiveData: MutableLiveData<Resource<AboutusModel>> = MutableLiveData()


    init {
        getRouteData()
    }

    fun getRouteData() = viewModelScope.launch {
        routeLiveData.postValue(Resource.Loading())
        val response = repository.getRouteData()
        routeLiveData.postValue(handleRouteResponse(response))
    }

    private fun handleRouteResponse(response: Response<UserModel>): Resource<UserModel>? {


        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->

                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())

    }


    fun getAboutUsData() = viewModelScope.launch {
        aboutUsLiveData.postValue(Resource.Loading())
        val response = repository.getAboutUsData()
        aboutUsLiveData.postValue(handleAboutUsResponse(response))
    }

    private fun handleAboutUsResponse(response: Response<AboutusModel>): Resource<AboutusModel>? {


        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->

                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())

    }


}