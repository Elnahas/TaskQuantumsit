package com.elnahas.task.data.model

data class Bus(
    val Id: Int,
    val busNumber: String,
    val capacity: Int,
    val created_at: String,
    val driverId: Int,
    val isactive: Int,
    val organizationId: Int,
    val route: Route,
    val routeId: Int,
    val supervisorId: Int,
    val updated_at: String
)