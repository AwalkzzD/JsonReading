package com.example.jsonreading.data

import com.google.gson.annotations.SerializedName

data class Chapter(
    @SerializedName("chapter_quiz_count")
    val chapterQuizCount: Long,
    @SerializedName("chapter_name")
    val chapterName: String,
    @SerializedName("chapter_sequence")
    val chapterSequence: Long,
    @SerializedName("chapter_color")
    val chapterColor: String,
    @SerializedName("graded_quiz")
    val gradedQuiz: List<GradedQuiz>,
    val topics: List<Topic>,
    @SerializedName("chapter_id")
    val chapterId: Long,
    @SerializedName("chapter_videos_count")
    val chapterVideosCount: Long,
    @SerializedName("media_files")
    val mediaFiles: List<MediaFile>,
)
