package com.example.jsonreading.utils.common

import com.example.jsonreading.data.person.Person
import com.example.jsonreading.data.person.PersonEntity
import com.example.jsonreading.data.person.PersonObject


fun PersonEntity.toPerson(): Person = Person(name = name, age = age)

fun Person.toPersonEntity(): PersonEntity = PersonEntity(name = name, age = age)

fun PersonObject.toPerson(): Person = Person(name = name, age = age)

fun Person.toPersonObject(): PersonObject = PersonObject(name = name, age = age)