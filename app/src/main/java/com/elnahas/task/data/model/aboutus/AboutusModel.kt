package com.elnahas.task.data.model.aboutus


import com.google.gson.annotations.SerializedName

data class AboutusModel(
    @SerializedName("InnerData")
    val innerData: List<InnerData>,
    @SerializedName("Message")
    val message: String,
    @SerializedName("Status")
    val status: Boolean
)