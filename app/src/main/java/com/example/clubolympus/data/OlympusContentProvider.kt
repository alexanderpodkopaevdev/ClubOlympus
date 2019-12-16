package com.example.clubolympus.data

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.util.Log
import com.example.clubolympus.data.ClubOlympusContract.MemberEntry

class OlympusContentProvider : ContentProvider() {
    private val MEMBERS = 1
    private val MEMBER_ID = 2

    private lateinit var dbHelper: OlympusDBHelper
    private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
        addURI(ClubOlympusContract.AUTHORITY, ClubOlympusContract.PATH_MEMBERS, MEMBERS)
        addURI(ClubOlympusContract.AUTHORITY, ClubOlympusContract.PATH_MEMBERS + "/#", MEMBER_ID)
    }

    override fun onCreate(): Boolean {
        dbHelper = OlympusDBHelper(context)
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {
        val db = dbHelper.readableDatabase
        val cursor = when (sUriMatcher.match(uri)) {
            MEMBERS -> {
                db.query(
                    MemberEntry.TABLE_NAME,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    sortOrder
                )
            }
            MEMBER_ID -> {
                val selection = "${MemberEntry.COLUMN_ID}=?"
                val selectionArgs = arrayOf(ContentUris.parseId(uri).toString())
                db.query(
                    MemberEntry.TABLE_NAME,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    sortOrder
                )
            }
            else -> {
                throw IllegalArgumentException("Can't query incorrect URI $uri")
            }
        }
        cursor.setNotificationUri(context.contentResolver,uri)
        return cursor
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val db = dbHelper.writableDatabase
        when (sUriMatcher.match(uri)) {
            MEMBERS -> {
                val id = db.insert(MemberEntry.TABLE_NAME, null, values)
                if (id == (-1).toLong()) {
                    Log.e("insertMethod", "Insertion of data in the table failed for $uri")
                    return null
                }
                context.contentResolver.notifyChange(uri,null)
                return ContentUris.withAppendedId(uri, id)
            }
            else -> {
                throw IllegalArgumentException("Insertion of data in the table failed for $uri")
            }
        }
    }


    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        val db = dbHelper.writableDatabase
        val count=  when (sUriMatcher.match(uri)) {
            MEMBERS -> {
                db.update(
                    MemberEntry.TABLE_NAME,
                    values,
                    selection,
                    selectionArgs
                )
            }
            MEMBER_ID -> {
                val selection = "${MemberEntry.COLUMN_ID}=?"
                val selectionArgs = arrayOf(ContentUris.parseId(uri).toString())
                db.update(
                    MemberEntry.TABLE_NAME,
                    values,
                    selection,
                    selectionArgs
                )
            }
            else -> {
                throw IllegalArgumentException("Can't update URI $uri")
            }
        }
        if (count !=0 ) {
            context.contentResolver.notifyChange(uri, null)
        }
        return count
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val db = dbHelper.writableDatabase
        val count = when (sUriMatcher.match(uri)) {
            MEMBERS -> {
                db.delete(
                    MemberEntry.TABLE_NAME,
                    selection,
                    selectionArgs
                )
            }
            MEMBER_ID -> {
                val selection = "${MemberEntry.COLUMN_ID}=?"
                val selectionArgs = arrayOf(ContentUris.parseId(uri).toString())
                db.delete(
                    MemberEntry.TABLE_NAME,
                    selection,
                    selectionArgs
                )
            }
            else -> {
                throw IllegalArgumentException("Can't delete URI $uri")
            }
        }
        if (count !=0 ) {
            context.contentResolver.notifyChange(uri, null)
        }
        return count
    }

    override fun getType(uri: Uri): String? {
        return when (sUriMatcher.match(uri)) {
            MEMBERS -> MemberEntry.CONTENT_MULTIPLE_ITEMS
            MEMBER_ID -> MemberEntry.CONTENT_SINGLE_ITEM
            else -> throw IllegalArgumentException("Unknown URI $uri")
        }
    }
}