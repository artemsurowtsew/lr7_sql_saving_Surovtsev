package com.example.lr7_sql_saving_surovtsev

import java.io.Serializable

data class Customer(
    val id: Int? = null,
    val firstName: String,
    val lastName: String,
    val email: String,
    val purchaseHistory: String,
    val orderCount: Int,
    val discount: Double
) : Serializable
