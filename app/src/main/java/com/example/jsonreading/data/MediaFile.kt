package com.example.jsonreading.data

import com.fasterxml.jackson.annotation.JsonProperty

data class MediaFile(
    val description: Any?,
    val sequence: Long,
    @JsonProperty("file_type")
    val fileType: String,
    @JsonProperty("file_name")
    val fileName: String,
    @JsonProperty("material_type")
    val materialType: String,
    val seen: Boolean,
    val id: Long,
    val size: Long,
    val title: String,
    val bookmark: Boolean,
    @JsonProperty("file_path")
    val filePath: String,
    val length: String,
    val thumbnail: String,
)
