package com.bizlergroup.stockcontrol.data.models

data class EditProductRequestData(
    val amount:Int,
    val category:String,
    val imageUrl:String,
    val name:String,
    val price:Int,
    val size: String,
    val unit:String
)
