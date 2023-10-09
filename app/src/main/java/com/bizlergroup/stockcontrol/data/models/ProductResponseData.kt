package com.bizlergroup.stockcontrol.data.models

data class ProductResponseData(
    val amount: Int,
    val category: String,
    val dateTime: String,
    val id: Int,
    val imageUrl: String,
    val name: String,
    val price: Int,
    val size : String,
    val unit: String
)