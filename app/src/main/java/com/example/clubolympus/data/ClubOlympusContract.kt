package com.example.clubolympus.data

class ClubOlympusContract {

    private constructor()

    companion object{
        const val DB_NAME = "olympus"
        const val DB_VERSION = 1
    }

    class MemberEntry{
        companion object{
            const val TABLE_NAME = "members"
            const val COLUMN_ID = "_id"
            const val COLUMN_FIRST_NAME = "firstName"
            const val COLUMN_LAST_NAME = "lastName"
            const val COLUMN_GENDER = "gender"
            const val COLUMN_SPORT_GROUP = "sportGroup"

            const val GENDER_UNKNOWN = 0
            const val GENDER_MALE = 1
            const val GENDER_FEMALE = 2


        }
    }
}