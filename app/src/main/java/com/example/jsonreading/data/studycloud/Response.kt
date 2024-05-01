package com.example.jsonreading.data.studycloud

import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("expire_datetime")
    val expireDatetime: String,
    val logins: List<Login>,
    @SerializedName("user_details")
    val userDetails: List<UserDetail>,
    @SerializedName("institute_name")
    val instituteName: String,
    @SerializedName("institute_id")
    val instituteId: Long,
)
