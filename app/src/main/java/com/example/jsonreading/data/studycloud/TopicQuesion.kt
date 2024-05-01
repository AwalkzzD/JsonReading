package com.example.jsonreading.data.studycloud

import com.google.gson.annotations.SerializedName

data class TopicQuesion(
    @SerializedName("option_detail_json")
    val optionDetailJson: List<OptionDetailJson>,
    @SerializedName("question_category")
    val questionCategory: String,
    @SerializedName("question_image")
    val questionImage: String?,
    @SerializedName("chapter_sequence")
    val chapterSequence: Long,
    @SerializedName("question_information")
    val questionInformation: String,
    @SerializedName("question_mark")
    val questionMark: Long,
    @SerializedName("chapter_id")
    val chapterId: Long,
    val question: String,
    @SerializedName("answers_data_type")
    val answersDataType: String,
    @SerializedName("answer_image")
    val answerImage: String?,
    @SerializedName("question_math_eq")
    val questionMathEq: String?,
    @SerializedName("chapter_name")
    val chapterName: String,
    @SerializedName("question_data_type")
    val questionDataType: String,
    @SerializedName("question_type")
    val questionType: String,
    @SerializedName("topic_id")
    val topicId: Long,
    val sequence: Long,
    @SerializedName("question_id")
    val questionId: Long,
)