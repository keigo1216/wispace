package com.websarva.wings.android.wispace

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_time_line.*

class TimeLine : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_line)

        tweet.setOnClickListener {
            val intent = Intent(this@TimeLine, Tweet::class.java)
            startActivity(intent)
            finish()
        }
    }
}