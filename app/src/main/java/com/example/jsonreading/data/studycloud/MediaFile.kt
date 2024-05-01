package com.example.jsonreading.data.studycloud

import com.google.gson.annotations.SerializedName

data class MediaFile(
    val description: Any?,
    val sequence: Long,
    @SerializedName("file_type")
    val fileType: String,
    @SerializedName("file_name")
    val fileName: String,
    @SerializedName("material_type")
    val materialType: String,
    val seen: Boolean,
    val id: Long,
    val size: Long,
    val title: String,
    val bookmark: Boolean,
    @SerializedName("file_path")
    val filePath: String,
    val length: String,
    val thumbnail: String,
)
