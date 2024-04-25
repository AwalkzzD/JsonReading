package com.example.jsonreading.data

import com.fasterxml.jackson.annotation.JsonProperty

data class Course(
    @JsonProperty("topic_quesions")
    val topicQuesions: List<TopicQuesion>,
    @JsonProperty("course_name")
    val courseName: String,
    @JsonProperty("chapter_icon_url")
    val chapterIconUrl: String,
    @JsonProperty("course_id")
    val courseId: Long,
    @JsonProperty("color_class")
    val colorClass: List<String>,
    @JsonProperty("total_quiz")
    val totalQuiz: Long,
    @JsonProperty("total_video")
    val totalVideo: Long,
    val chapters: List<Chapter>,
    @JsonProperty("full_portion")
    val fullPortion: List<FullPortion>,
)
