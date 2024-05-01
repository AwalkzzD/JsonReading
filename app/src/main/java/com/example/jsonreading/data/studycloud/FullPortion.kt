package com.example.jsonreading.data.studycloud

import com.google.gson.annotations.SerializedName

data class FullPortion(
    @SerializedName("total_time")
    val totalTime: String,
    val status: String,
    @SerializedName("full_portion_test_result")
    val fullPortionTestResult: List<Any?>,
    @SerializedName("total_mark")
    val totalMark: Long,
    @SerializedName("is_completed")
    val isCompleted: Boolean,
    @SerializedName("full_portion_test_id")
    val fullPortionTestId: Long,
    @SerializedName("full_portion_test_questions")
    val fullPortionTestQuestions: List<FullPortionTestQuestion>,
    @SerializedName("is_active")
    val isActive: Boolean,
    @SerializedName("full_portion_test_name")
    val fullPortionTestName: String,
)
