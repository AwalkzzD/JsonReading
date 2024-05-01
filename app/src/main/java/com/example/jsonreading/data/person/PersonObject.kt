package com.example.jsonreading.data.person

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

open class PersonObject(
    @PrimaryKey var id: ObjectId? = ObjectId(), var name: String = "", var age: String = ""
) : RealmObject {
    constructor() : this(null, "", "")
}