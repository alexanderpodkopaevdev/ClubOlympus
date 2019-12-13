package com.example.clubolympus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class AddMemberActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_member)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
