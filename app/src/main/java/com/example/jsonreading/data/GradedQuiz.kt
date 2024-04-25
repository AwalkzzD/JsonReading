package com.example.jsonreading.data

import com.fasterxml.jackson.annotation.JsonProperty

data class GradedQuiz(
    @JsonProperty("total_time")
    val totalTime: String,
    val status: String,
    @JsonProperty("graded_quiz_questions")
    val gradedQuizQuestions: List<GradedQuizQuestion>,
    @JsonProperty("graded_quiz_result")
    val gradedQuizResult: List<Any?>,
    @JsonProperty("total_mark")
    val totalMark: Long,
    @JsonProperty("graded_quiz_name")
    val gradedQuizName: String,
    @JsonProperty("graded_quiz_id")
    val gradedQuizId: Long,
    @JsonProperty("is_completed")
    val isCompleted: Boolean,
    @JsonProperty("is_active")
    val isActive: Boolean,
)
