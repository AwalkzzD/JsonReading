package com.example.jsonreading.utils.db.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.jsonreading.data.person.PersonEntity

@Dao
interface PersonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPerson(person: PersonEntity)

    @Query("SELECT * FROM persons")
    fun getPersonData(): List<PersonEntity>

    @Query("DELETE FROM persons")
    fun deleteAllData()

    @Update
    fun updatePerson(person: PersonEntity)

}