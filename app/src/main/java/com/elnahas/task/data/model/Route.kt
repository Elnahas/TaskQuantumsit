package com.elnahas.task.data.model

data class Route(
    val Id: Int,
    val created_at: String,
    val name: String,
    val routePath: List<RoutePath>,
    val timeToDest: Int,
    val updated_at: String
)