package com.example.clubolympus


import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import com.example.clubolympus.data.ClubOlympusContract.MemberEntry
import kotlinx.android.synthetic.main.activity_add_member.*

class AddMemberActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<Cursor> {

    var gender = 0
    private val editMemberLoader = 123
    private lateinit var currentMemberUri: Uri
    private var currUri: Uri? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_member)
        currUri = intent.data
        if (currUri == null) {
            invalidateOptionsMenu()
            title = "Add a member"
        } else {
            currentMemberUri = currUri ?: MemberEntry.CONTENT_URI
            title = "Edit the member"
            LoaderManager.getInstance(this).initLoader(editMemberLoader, null, this)
        }

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
                val genderStr = parent?.getItemAtPosition(position) as String
                gender = when (genderStr) {
                    getString(R.string.male) -> MemberEntry.GENDER_MALE
                    getString(R.string.female) -> MemberEntry.GENDER_FEMALE
                    else -> MemberEntry.GENDER_UNKNOWN
                }
            }
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        super.onPrepareOptionsMenu(menu)
        if (currUri == null) {
            menu?.findItem(R.id.menuDeleteMember)?.isVisible = false
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.edit_members_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menuSaveMember -> {
                saveMember()
                return true
            }
            R.id.menuDeleteMember -> {
                showDeleteDialog()
                return true
            }
            android.R.id.home -> {
                NavUtils.navigateUpFromSameTask(this)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showDeleteDialog() {
        val builder = AlertDialog.Builder(this).apply {
            setMessage("Do you wan't delete member?")
            setPositiveButton("Yes") { _, _ ->
                deleteMember()

            }
            setNegativeButton("No") { _, _ ->
                Toast.makeText(this@AddMemberActivity, "Not deleted", Toast.LENGTH_SHORT).show()
            }
        }
        val alertDialog = builder.create()
        alertDialog.show()


    }

    private fun deleteMember() {
        if (contentResolver.delete(currentMemberUri, null, null) != 0) {
            Toast.makeText(this, "Member deleted", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, "Deleted failed", Toast.LENGTH_SHORT).show()
        }

    }


    private fun saveMember() {
        if (etFirstName.text.toString().trim().isEmpty()) {
            Toast.makeText(this, "Enter first name", Toast.LENGTH_SHORT).show()
        } else if (etLastName.text.toString().trim().isEmpty()) {
            Toast.makeText(this, "Enter last name", Toast.LENGTH_SHORT).show()
        } else if (etGroup.text.toString().trim().isEmpty()) {
            Toast.makeText(this, "Enter sport group", Toast.LENGTH_SHORT).show()
        } else {
            val contentValues = ContentValues().apply {
                put(MemberEntry.COLUMN_FIRST_NAME, etFirstName.text.toString().trim())
                put(MemberEntry.COLUMN_LAST_NAME, etLastName.text.toString().trim())
                put(MemberEntry.COLUMN_GENDER, gender)
                put(MemberEntry.COLUMN_SPORT_GROUP, etGroup.text.toString().trim())
            }
            if (currUri == null) {
                if (contentResolver.insert(MemberEntry.CONTENT_URI, contentValues) != null) {
                    Toast.makeText(this, "Saved data", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(
                        this,
                        "Insertion of data in the table failed",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            } else {
                if (contentResolver.update(currentMemberUri, contentValues, null, null) != 0) {
                    Toast.makeText(this, "Member updated", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Updated failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        val projection = arrayOf(
            MemberEntry.COLUMN_ID,
            MemberEntry.COLUMN_FIRST_NAME,
            MemberEntry.COLUMN_LAST_NAME,
            MemberEntry.COLUMN_GENDER,
            MemberEntry.COLUMN_SPORT_GROUP
        )
        return CursorLoader(this, currentMemberUri, projection, null, null, null)
    }


    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        if (data != null && data.moveToFirst()) {
            etFirstName.setText(data.getString(data.getColumnIndex(MemberEntry.COLUMN_FIRST_NAME)))
            etLastName.setText(data.getString(data.getColumnIndex(MemberEntry.COLUMN_LAST_NAME)))
            spGender.setSelection(
                when (data.getInt(data.getColumnIndex(MemberEntry.COLUMN_GENDER))) {
                    MemberEntry.GENDER_MALE -> 0
                    MemberEntry.GENDER_FEMALE -> 1
                    else -> 2
                }
            )
            etGroup.setText(data.getString(data.getColumnIndex(MemberEntry.COLUMN_SPORT_GROUP)))
        }
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
