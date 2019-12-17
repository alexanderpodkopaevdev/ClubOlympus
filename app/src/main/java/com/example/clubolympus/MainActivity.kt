package com.example.clubolympus

import android.content.ContentUris
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import com.example.clubolympus.data.ClubOlympusContract.MemberEntry
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<Cursor> {
    private lateinit var membersCursorAdapter: MemberCursorAdapter
    private val memberLoader = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fabAddMember.setOnClickListener {
            startActivity(Intent(this@MainActivity, AddMemberActivity::class.java))
        }
        membersCursorAdapter = MemberCursorAdapter(this, null)
        listView.adapter = membersCursorAdapter
        listView.setOnItemClickListener { _, _, _, id ->
            val intent = Intent(this@MainActivity,AddMemberActivity::class.java)
            val currentUri = ContentUris.withAppendedId(MemberEntry.CONTENT_URI,id)
            intent.data = currentUri
            startActivity(intent)
        }

        LoaderManager.getInstance(this).initLoader(memberLoader,null,this)


    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        val projection = arrayOf(
            MemberEntry.COLUMN_ID,
            MemberEntry.COLUMN_FIRST_NAME,
            MemberEntry.COLUMN_LAST_NAME,
            MemberEntry.COLUMN_GENDER,
            MemberEntry.COLUMN_SPORT_GROUP
        )
        return CursorLoader(this, MemberEntry.CONTENT_URI, projection, null, null, null)
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        membersCursorAdapter.swapCursor(data)
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        membersCursorAdapter.swapCursor(null)
    }

}
