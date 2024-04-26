package com.example.jsonreading.data

import com.google.gson.annotations.SerializedName

data class GradedQuiz(
    @SerializedName("total_time")
    val totalTime: String,
    val status: String,
    @SerializedName("graded_quiz_questions")
    val gradedQuizQuestions: List<GradedQuizQuestion>,
    @SerializedName("graded_quiz_result")
    val gradedQuizResult: List<Any?>,
    @SerializedName("total_mark")
    val totalMark: Long,
    @SerializedName("graded_quiz_name")
    val gradedQuizName: String,
    @SerializedName("graded_quiz_id")
    val gradedQuizId: Long,
    @SerializedName("is_completed")
    val isCompleted: Boolean,
    @SerializedName("is_active")
    val isActive: Boolean,
)
