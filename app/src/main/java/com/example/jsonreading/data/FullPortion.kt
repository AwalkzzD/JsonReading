package com.example.jsonreading.data

import com.fasterxml.jackson.annotation.JsonProperty


data class FullPortion(
    @JsonProperty("total_time")
    val totalTime: String,
    val status: String,
    @JsonProperty("full_portion_test_result")
    val fullPortionTestResult: List<Any?>,
    @JsonProperty("total_mark")
    val totalMark: Long,
    @JsonProperty("is_completed")
    val isCompleted: Boolean,
    @JsonProperty("full_portion_test_id")
    val fullPortionTestId: Long,
    @JsonProperty("full_portion_test_questions")
    val fullPortionTestQuestions: List<FullPortionTestQuestion>,
    @JsonProperty("is_active")
    val isActive: Boolean,
    @JsonProperty("full_portion_test_name")
    val fullPortionTestName: String,
)
