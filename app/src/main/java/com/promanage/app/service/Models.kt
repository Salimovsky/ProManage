package com.promanage.app.service

import java.util.*

data class Material(val sku: String, val quantity: Int)
data class Labor(val name: String, val description: String, val hours: Int, val rate: Int)