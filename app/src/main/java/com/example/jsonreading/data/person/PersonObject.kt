package com.example.jsonreading.data.person

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

open class PersonObject(
    @PrimaryKey var name: String = "", var age: String = ""
) : RealmObject {
    constructor() : this("", "")
}