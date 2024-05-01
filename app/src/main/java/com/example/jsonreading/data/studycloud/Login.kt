package com.example.jsonreading.data.studycloud

import com.google.gson.annotations.SerializedName

data class Login(
    val password: String,
    @SerializedName("user_id")
    val userId: Long,
    val email: String,
)
