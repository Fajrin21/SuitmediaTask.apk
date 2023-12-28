package com.example.suitmediaapk.dataclass

import androidx.resourceinspection.annotation.Attribute.IntMap
import com.google.gson.annotations.SerializedName


data class data(
    val id: Int,
    val email: String,
    val first_name: String,
    val last_name: String,
    val avatar: String
)

data class UserResponse(
    var page: Int,
    var per_page: Int,
    var total: Int,
    var total_pages: Int,
    var data: List<data>
)