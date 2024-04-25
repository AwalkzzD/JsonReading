package com.example.jsonreading.data

import com.fasterxml.jackson.annotation.JsonProperty

data class UserDetail(
    val username: String,
    @JsonProperty("phone_number")
    val phoneNumber: String,
    @JsonProperty("first_name")
    val firstName: String,
    @JsonProperty("last_name")
    val lastName: String,
    @JsonProperty("user_id")
    val userId: Long,
    @JsonProperty("medium_id")
    val mediumId: Long,
    val courses: List<Course>,
    @JsonProperty("board_id")
    val boardId: Long,
    val gender: String,
    @JsonProperty("grade_id")
    val gradeId: Long,
    @JsonProperty("board_name")
    val boardName: String,
    @JsonProperty("last_activity")
    val lastActivity: Map<String, Any>,
    @JsonProperty("grade_name")
    val gradeName: String,
    @JsonProperty("birth_date")
    val birthDate: Any?,
    @JsonProperty("middle_name")
    val middleName: String,
    @JsonProperty("medium_name")
    val mediumName: String,
    val email: String,
)
