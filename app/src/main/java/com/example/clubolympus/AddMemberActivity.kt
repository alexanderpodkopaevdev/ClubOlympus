package com.example.clubolympus

import android.content.ContentResolver
import android.content.ContentValues
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import com.example.clubolympus.data.ClubOlympusContract.MemberEntry
import kotlinx.android.synthetic.main.activity_add_member.*

class AddMemberActivity : AppCompatActivity() {

    var gender = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_member)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val genderAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.gender_list,
            android.R.layout.simple_spinner_item
        )
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spGender.adapter = genderAdapter
        spGender.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                var genderStr = parent?.getItemAtPosition(position) as String
                gender = when (genderStr) {
                    getString(R.string.male) -> MemberEntry.GENDER_MALE
                    getString(R.string.female) -> MemberEntry.GENDER_FEMALE
                    else -> MemberEntry.GENDER_UNKNOWN
                }
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.edit_members_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menuSaveMember -> {
                insertMember()
                return true
            }
            R.id.menuDeleteMember -> {
                return true
            }
            android.R.id.home -> {
                NavUtils.navigateUpFromSameTask(this)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    fun insertMember() {
        val contentValues = ContentValues().apply {
            put(MemberEntry.COLUMN_FIRST_NAME,etFirstName.text.toString().trim())
            put(MemberEntry.COLUMN_LAST_NAME,etLastName.text.toString().trim())
            put(MemberEntry.COLUMN_GENDER,gender)
            put(MemberEntry.COLUMN_SPORT_GROUP,etGroup.text.toString().trim())
        }

        if (contentResolver.insert(MemberEntry.CONTENT_URI,contentValues)!= null) {
            Toast.makeText(this,"Saved data",Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this,"Insertion of data in the table failed",Toast.LENGTH_SHORT).show()

        }

    }
}
