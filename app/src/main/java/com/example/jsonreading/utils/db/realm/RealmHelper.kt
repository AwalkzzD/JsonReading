package com.example.jsonreading.utils.db.realm

import com.example.jsonreading.data.person.Person
import com.example.jsonreading.data.person.PersonObject
import com.example.jsonreading.utils.common.toPerson
import com.example.jsonreading.utils.common.toPersonObject
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query

class RealmHelper {
    private var personRealm: Realm =
        Realm.open(RealmConfiguration.create(schema = setOf(PersonObject::class)))

    fun insertData(person: Person) {
        personRealm.writeBlocking {
            copyToRealm(
                person.toPersonObject(), updatePolicy = UpdatePolicy.ALL
            )
        }
    }

    fun getData(): MutableList<Person> {
        val personList: MutableList<Person> = mutableListOf()
        if (personRealm.query<PersonObject>().find().isNotEmpty()) {
            personList.addAll(personRealm.query<PersonObject>().find().map { personObject ->
                personObject.toPerson()
            })
        }
        return personList
    }

    fun deleteAllData() {
        personRealm.writeBlocking {
            deleteAll()
        }
    }
}