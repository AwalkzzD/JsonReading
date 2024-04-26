package com.example.jsonreading.data

import com.google.gson.annotations.SerializedName

data class UserDetail(
    val username: String,
    @SerializedName("phone_number")
    val phoneNumber: String,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    @SerializedName("user_id")
    val userId: Long,
    @SerializedName("medium_id")
    val mediumId: Long,
    val courses: List<Course>,
    @SerializedName("board_id")
    val boardId: Long,
    val gender: String,
    @SerializedName("grade_id")
    val gradeId: Long,
    @SerializedName("board_name")
    val boardName: String,
    @SerializedName("last_activity")
    val lastActivity: Map<String, Any>,
    @SerializedName("grade_name")
    val gradeName: String,
    @SerializedName("birth_date")
    val birthDate: Any?,
    @SerializedName("middle_name")
    val middleName: String,
    @SerializedName("medium_name")
    val mediumName: String,
    val email: String,
)
