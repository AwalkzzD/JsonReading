package com.example.jsonreading.data

import com.fasterxml.jackson.annotation.JsonProperty

data class Response(
    @JsonProperty("expire_datetime")
    val expireDatetime: String,
    val logins: List<Login>,
    @JsonProperty("user_details")
    val userDetails: List<UserDetail>,
    @JsonProperty("institute_name")
    val instituteName: String,
    @JsonProperty("institute_id")
    val instituteId: Long,
)
