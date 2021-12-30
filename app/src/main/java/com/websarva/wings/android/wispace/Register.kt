package com.websarva.wings.android.wispace

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.TextureView
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_select_menu.*
import java.util.*

class Register : AppCompatActivity() {

    var selectPhotoUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val auth = FirebaseAuth.getInstance()

        select_icon_bt.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            //起動して閉じられたら、onAcitivityResultに結果を返す
            //正常に終了したらRESULT_OKがセットされる
            startActivityForResult(intent, 0)
        }

        register.setOnClickListener {
            peformRegister()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 0 && resultCode == Activity.RESULT_OK && data != null){
            //画像をurlで保存する形式
            selectPhotoUri = data.data

            //bitに変換
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectPhotoUri)

            selectphoto_imageview.setImageBitmap(bitmap)
            select_icon_bt.alpha = 0f
        }
    }

    private fun peformRegister(){

        val email = mailaddress.text.toString()
        val password = password.text.toString()
        val username = username.text.toString()

        if(email.isEmpty() || password.isEmpty() || username.isEmpty()){
            Toast.makeText(this, "Please enter text in email/pw", Toast.LENGTH_SHORT)
            return
        }

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if(!it.isSuccessful) return@addOnCompleteListener

                uploadImageFirebaseStorage()
            }

//        auth.createUserWithEmailAndPassword(email, password)
//            .addOnCompleteListener(this) { task ->
//                if (task.isSuccessful) {
//                    Toast.makeText(
//                        baseContext, "SignUp 成功",
//                        Toast.LENGTH_SHORT
//                    ).show()
//
////                        intent = Intent(this@Register, Select::class.java)
////                        startActivity(intent)
////                        finish()
//
//                } else {
//                    Toast.makeText(
//                        baseContext, "SignUp 失敗",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//            }
    }

    private fun uploadImageFirebaseStorage(){

        if(selectPhotoUri == null) return

        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")

        ref.putFile(selectPhotoUri!!)
            .addOnSuccessListener {
                ref.downloadUrl.addOnSuccessListener {
                    savaUserToFirebaseDatabase(it.toString())
                }
            }

    }

    private fun savaUserToFirebaseDatabase(profileImageUri: String){
        val uid = FirebaseAuth.getInstance().uid ?:return
        val username = username.text.toString()
        val ref = FirebaseDatabase.getInstance().getReference("/user/$uid")

        val user = User(uid, username, profileImageUri)

        ref.setValue(user)
            .addOnSuccessListener {

                val intent = Intent(this, StudyRoom::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)

            }
    }

    private fun login(view: View){
        intent = Intent(this@Register, Login::class.java)
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        finish()
    }
}