package com.websarva.wings.android.wispace

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.sql.Time

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val uid = FirebaseAuth.getInstance().uid
        if(uid == null){
            setContentView(R.layout.activity_main)
        }else{
            setContentView(R.layout.activity_main_logged)
        }

    }

    fun login(view: View){
        val intent = Intent(this@MainActivity, Login::class.java)
        startActivity(intent)
        finish()
    }

    fun register(view: View){
        val intent = Intent(this@MainActivity, Register::class.java)
        startActivity(intent)
        finish()
    }

    fun studyRoom(view: View){
        val intent = Intent(this@MainActivity, StudyRoom::class.java)
        startActivity(intent)
    }

    fun start(view: View){
        val intent = Intent(this, TimeLine::class.java)
        startActivity(intent)
        finish()
    }
}