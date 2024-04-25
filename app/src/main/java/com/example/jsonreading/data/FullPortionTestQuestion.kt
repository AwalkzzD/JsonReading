package com.example.jsonreading.data

import com.fasterxml.jackson.annotation.JsonProperty

data class FullPortionTestQuestion(
    @JsonProperty("option_detail_json")
    val optionDetailJson: List<OptionDetailJson>,
    @JsonProperty("question_category")
    val questionCategory: String,
    @JsonProperty("question_image")
    val questionImage: String?,
    @JsonProperty("chapter_sequence")
    val chapterSequence: Long,
    @JsonProperty("question_information")
    val questionInformation: String,
    @JsonProperty("question_mark")
    val questionMark: Long,
    @JsonProperty("chapter_id")
    val chapterId: Long,
    val question: String,
    @JsonProperty("answers_data_type")
    val answersDataType: String,
    @JsonProperty("answer_image")
    val answerImage: String?,
    @JsonProperty("question_math_eq")
    val questionMathEq: String?,
    @JsonProperty("chapter_name")
    val chapterName: String,
    @JsonProperty("question_data_type")
    val questionDataType: String,
    @JsonProperty("question_type")
    val questionType: String,
    val sequence: Long,
    @JsonProperty("question_id")
    val questionId: Long,
)
