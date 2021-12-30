package com.websarva.wings.android.wispace

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_time_line.*

class TimeLine : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_line)

        val user = Firebase.auth.currentUser

        user?.let { // Name, email address, and profile photo Url
            val uid = user.uid
            Log.d("sample", "${uid}")
        }

        tweet.setOnClickListener {
            val intent = Intent(this@TimeLine, Tweet::class.java)
            startActivity(intent)
            finish()
        }
    }
}