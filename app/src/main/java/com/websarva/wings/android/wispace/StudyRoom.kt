package com.websarva.wings.android.wispace

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView

class StudyRoom : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_study_room)
    }

    //ボタンが押された時の処理
    fun seat_bt(view: View){
        val intent = Intent(this@madatukuttenai, Login::class.java)
        startActivity(intent)
//        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        finish()
    }
}