package com.websarva.wings.android.wispace

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun login(view: View){
        val intent = Intent(this@MainActivity, Login::class.java)
        startActivity(intent)
    }

    fun register(view: View){
        val intent = Intent(this@MainActivity, Register::class.java)
        startActivity(intent)
    }

    fun studyRoom(view: View){
        val intent = Intent(this@MainActivity, StudyRoom::class.java)
        startActivity(intent)
    }
}