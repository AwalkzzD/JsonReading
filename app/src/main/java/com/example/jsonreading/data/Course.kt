package com.example.jsonreading.data

import com.google.gson.annotations.SerializedName

data class Course(
    @SerializedName("topic_quesions")
    val topicQuesions: List<TopicQuesion>,
    @SerializedName("course_name")
    val courseName: String,
    @SerializedName("chapter_icon_url")
    val chapterIconUrl: String,
    @SerializedName("course_id")
    val courseId: Long,
    @SerializedName("color_class")
    val colorClass: List<String>,
    @SerializedName("total_quiz")
    val totalQuiz: Long,
    @SerializedName("total_video")
    val totalVideo: Long,
    val chapters: List<Chapter>,
    @SerializedName("full_portion")
    val fullPortion: List<FullPortion>,
)
