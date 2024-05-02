package com.example.jsonreading.utils.db.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.jsonreading.data.person.Person

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val query = ("CREATE TABLE $TABLE_NAME ($NAME_COl TEXT PRIMARY KEY,$AGE_COL TEXT)")
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addPerson(name: String, age: String) {
        val values = ContentValues()

        values.put(NAME_COl, name)
        values.put(AGE_COL, age)

        val db = this.writableDatabase

        db.insertWithOnConflict(TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE)

        db.close()
    }

    fun getPersonData(): MutableList<Person> {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        val personList: MutableList<Person> = mutableListOf()
        if (cursor != null) {
            if (cursor.count > 0) {
                while (cursor.moveToNext()) {
                    personList.add(
                        Person(
                            cursor.getString(cursor.getColumnIndex(NAME_COl)),
                            cursor.getString(cursor.getColumnIndex(AGE_COL))
                        )
                    )
                }
            }
            cursor.close()
        }

        return personList
    }

    fun deleteAllData() {
        this.readableDatabase.execSQL("DELETE FROM $TABLE_NAME")
    }

    companion object {
        private val DATABASE_NAME = "testsqlite"

        private val DATABASE_VERSION = 1

        val TABLE_NAME = "person_data"

        val NAME_COl = "name"

        val AGE_COL = "age"
    }
}