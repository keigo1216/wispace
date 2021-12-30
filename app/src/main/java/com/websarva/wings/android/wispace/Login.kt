package com.websarva.wings.android.wispace

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        login.setOnClickListener {
            val emailEditText = findViewById<EditText>(R.id.username)
            val emailText = emailEditText.text.toString()

            val passEditText = findViewById<EditText>(R.id.password)
            val passText = passEditText.text.toString()

            auth.signInWithEmailAndPassword(emailText, passText)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            baseContext, "Login 成功",
                            Toast.LENGTH_SHORT
                        ).show()

                        Log.d("sample", "hello1")
//                        val intent = Intent(this@Login, Select::class.java)
//                        startActivity(intent)
                        val intent = Intent(this@Login, StudyRoom::class.java)
                        startActivity(intent)
                        Log.d("sample", "hello2")
                        finish()

                    } else {
                        Toast.makeText(
                            baseContext, "Login 失敗",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }
        }
    }

    fun register(view: View){
        val intent = Intent(this@Login, Register::class.java)
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        finish()
    }
}