package com.incwell.blackforest.data.model

data class History(
    val common: Common,
    var product: List<Order>
)