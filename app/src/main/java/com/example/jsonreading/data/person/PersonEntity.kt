package com.example.jsonreading.data.person

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "persons")
data class PersonEntity(
    @PrimaryKey @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "age") var age: String
)