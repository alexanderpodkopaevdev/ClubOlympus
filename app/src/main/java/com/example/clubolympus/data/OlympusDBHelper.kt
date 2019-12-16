package com.example.clubolympus.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.clubolympus.data.ClubOlympusContract.MemberEntry

class OlympusDBHelper(context: Context?) :
    SQLiteOpenHelper(context, ClubOlympusContract.DB_NAME, null, ClubOlympusContract.DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE ${MemberEntry.TABLE_NAME} (" +
                "${MemberEntry.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "${MemberEntry.COLUMN_FIRST_NAME} text, " +
                "${MemberEntry.COLUMN_LAST_NAME} text, " +
                "${MemberEntry.COLUMN_GENDER} INTEGER NOTNULL," +
                "${MemberEntry.COLUMN_SPORT_GROUP} text)")

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${MemberEntry.TABLE_NAME} ")
        onCreate(db)
    }
}