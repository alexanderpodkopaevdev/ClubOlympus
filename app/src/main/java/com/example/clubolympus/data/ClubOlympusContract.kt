package com.example.clubolympus.data

import android.content.ContentResolver
import android.net.Uri

class ClubOlympusContract {

    companion object{
        const val DB_NAME = "olympus"
        const val DB_VERSION = 2

        private const val SCHEME = "content://"
        const val AUTHORITY = "com.example.clubolympus"
        const val PATH_MEMBERS = "members"

        val BASE_CONTENT_URI = Uri.parse(SCHEME + AUTHORITY)!!



    }

    class MemberEntry{
        companion object{
            const val TABLE_NAME = "members"
            const val COLUMN_ID = "_id"
            const val COLUMN_FIRST_NAME = "firstName"
            const val COLUMN_LAST_NAME = "lastName"
            const val COLUMN_GENDER = "gender"
            const val COLUMN_SPORT_GROUP = "sportGroup"

            const val GENDER_UNKNOWN = 2
            const val GENDER_MALE = 0
            const val GENDER_FEMALE = 1

            val CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_MEMBERS)!!

            const val CONTENT_MULTIPLE_ITEMS = "${ContentResolver.CURSOR_DIR_BASE_TYPE}/$AUTHORITY/$PATH_MEMBERS"
            const val CONTENT_SINGLE_ITEM = "${ContentResolver.CURSOR_ITEM_BASE_TYPE}/$AUTHORITY/$PATH_MEMBERS"


        }
    }
}