package com.elnahas.task.data.model.aboutus


import com.google.gson.annotations.SerializedName

data class InnerData(
    @SerializedName("content")
    val content: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("Id")
    val id: Int,
    @SerializedName("updated_at")
    val updatedAt: String
)