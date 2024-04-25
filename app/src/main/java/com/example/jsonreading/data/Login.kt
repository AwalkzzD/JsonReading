package com.example.jsonreading.data

import com.fasterxml.jackson.annotation.JsonProperty

data class Login(
    val password: String,
    @JsonProperty("user_id")
    val userId: Long,
    val email: String,
)
