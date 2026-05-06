package com.example.lostandfoundapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    lateinit var btnCreateAdvert: Button
    lateinit var btnShowItems: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnCreateAdvert = findViewById(R.id.btnCreateAdvert)
        btnShowItems = findViewById(R.id.btnShowItems)

        btnCreateAdvert.setOnClickListener {
            startActivity(Intent(this, AddItemActivity::class.java))
        }

        btnShowItems.setOnClickListener {
            startActivity(Intent(this, ViewItemsActivity::class.java))
        }
    }
}