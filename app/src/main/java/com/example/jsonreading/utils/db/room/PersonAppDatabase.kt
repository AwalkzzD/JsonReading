package com.example.jsonreading.utils.db.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.jsonreading.data.person.PersonEntity
import com.example.jsonreading.utils.db.room.dao.PersonDao

@Database(
    version = 1, entities = [PersonEntity::class]
)
abstract class PersonAppDatabase : RoomDatabase() {

    abstract fun personDao(): PersonDao

    companion object {
        private var INSTANCE: PersonAppDatabase? = null
        private val mLock = Any()

        fun getInstance(context: Context): PersonAppDatabase = INSTANCE ?: synchronized(mLock) {
            Room.databaseBuilder(
                context.applicationContext, PersonAppDatabase::class.java, "test_room"
            ).allowMainThreadQueries().build().apply { INSTANCE = this }
        }
    }
}