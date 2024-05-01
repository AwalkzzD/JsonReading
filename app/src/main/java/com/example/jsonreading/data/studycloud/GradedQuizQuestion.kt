package com.example.jsonreading.data.studycloud

import com.google.gson.annotations.SerializedName

data class GradedQuizQuestion(
    @SerializedName("question_image")
    val questionImage: String?,
    @SerializedName("question_type")
    val questionType: String,
    val sequence: Long,
    @SerializedName("question_image_url")
    val questionImageUrl: String,
    @SerializedName("question_math_eq")
    val questionMathEq: String?,
    @SerializedName("question_data_type")
    val questionDataType: String,
    @SerializedName("option_detail_json")
    val optionDetailJson: List<OptionDetailJson>,
    @SerializedName("question_information")
    val questionInformation: String,
    @SerializedName("question_mark")
    val questionMark: Long,
    val question: String,
    @SerializedName("answers_data_type")
    val answersDataType: String,
    @SerializedName("answer_image")
    val answerImage: String?,
    @SerializedName("question_category")
    val questionCategory: String,
    @SerializedName("question_id")
    val questionId: Long,
)
