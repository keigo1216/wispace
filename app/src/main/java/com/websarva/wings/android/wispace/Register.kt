package com.websarva.wings.android.wispace

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.TextureView
import android.view.View
import android.widget.EditText

class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
    }

    //ボタンが押された時の処理
    fun register_bt(view: View){
        val et_name = findViewById<EditText>(R.id.username)
        val et_address = findViewById<EditText>(R.id.mailaddress)
        val et_pass = findViewById<EditText>(R.id.password)

        val username: String = et_name.getText().toString()
        val address: String = et_address.getText().toString()
        val pass: String = et_pass.getText().toString()

        Log.d("sample", "${username}")
        Log.d("sample", "${address}")
        Log.d("sample", "${pass}")
    }
}