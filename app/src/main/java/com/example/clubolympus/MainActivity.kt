package com.example.clubolympus

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.clubolympus.data.ClubOlympusContract.MemberEntry
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fabAddMember.setOnClickListener {
            startActivity(Intent(this@MainActivity, AddMemberActivity::class.java))
        }


    }

    override fun onStart() {
        super.onStart()
        displayData()
    }

    fun displayData() {
        val projection = arrayOf(
            MemberEntry.COLUMN_ID,
            MemberEntry.COLUMN_FIRST_NAME,
            MemberEntry.COLUMN_LAST_NAME,
            MemberEntry.COLUMN_GENDER,
            MemberEntry.COLUMN_SPORT_GROUP
        )

        val cursor = contentResolver.query(MemberEntry.CONTENT_URI, projection, null, null, null)
        tvData.text = "All members \n\n"
        tvData.append(
            "${MemberEntry.COLUMN_ID}, " +
                    "${MemberEntry.COLUMN_FIRST_NAME}, " +
                    "${MemberEntry.COLUMN_LAST_NAME}, " +
                    "${MemberEntry.COLUMN_GENDER},  " +
                    "${MemberEntry.COLUMN_SPORT_GROUP} \n\n"
        )

        if (cursor != null) {
            while (cursor.moveToNext()) {
                tvData.append(cursor.getInt(cursor.getColumnIndex(MemberEntry.COLUMN_ID)).toString())
                tvData.append(cursor.getString(cursor.getColumnIndex(MemberEntry.COLUMN_FIRST_NAME)))
                tvData.append(cursor.getString(cursor.getColumnIndex(MemberEntry.COLUMN_LAST_NAME)))
                tvData.append(cursor.getInt(cursor.getColumnIndex(MemberEntry.COLUMN_GENDER)).toString())
                tvData.append(cursor.getString(cursor.getColumnIndex(MemberEntry.COLUMN_SPORT_GROUP)))
                tvData.append("\n")
            }
        }
        cursor.close()
    }

}
