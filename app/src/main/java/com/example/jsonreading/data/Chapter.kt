package com.example.jsonreading.data

import com.fasterxml.jackson.annotation.JsonProperty

data class Chapter(
    @JsonProperty("chapter_quiz_count")
    val chapterQuizCount: Long,
    @JsonProperty("chapter_name")
    val chapterName: String,
    @JsonProperty("chapter_sequence")
    val chapterSequence: Long,
    @JsonProperty("chapter_color")
    val chapterColor: String,
    @JsonProperty("graded_quiz")
    val gradedQuiz: List<GradedQuiz>,
    val topics: List<Topic>,
    @JsonProperty("chapter_id")
    val chapterId: Long,
    @JsonProperty("chapter_videos_count")
    val chapterVideosCount: Long,
    @JsonProperty("media_files")
    val mediaFiles: List<MediaFile>,
)
