package com.example.clubolympus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import kotlinx.android.synthetic.main.activity_add_member.*

class AddMemberActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_member)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val genderAdapter = ArrayAdapter.createFromResource(this,R.array.gender_list,android.R.layout.simple_spinner_item)
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spGender.adapter = genderAdapter
    }
}
