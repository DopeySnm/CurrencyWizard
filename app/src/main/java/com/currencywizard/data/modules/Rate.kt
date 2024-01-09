package com.currencywizard.data.modules


data class Rate(
    val id: Int = 0,
    val base: String,
    val target: String,
    val date: String,
    val coefficient: Double
)
