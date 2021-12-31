package com.websarva.wings.android.wispace

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_tweet.*

class Tweet : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tweet)

        send_button_tweet.setOnClickListener {
            setRoomFirebaseDatabase()
        }

        back_button_tweet.setOnClickListener {
            finish()
        }
    }

    private fun setRoomFirebaseDatabase(){

        val roomName = roomname_tweet.text.toString()
        val discription = discription_tweet.text.toString()

        if(roomName.isEmpty() || discription.isEmpty()){
            Toast.makeText(this, "fill out roomname or discription", Toast.LENGTH_SHORT)
            return
        }

        val ref = FirebaseDatabase.getInstance().getReference("/studyroom").push()
        val room = Room(ref.key!!, roomName, discription)
        ref.setValue(room)
            .addOnSuccessListener {
                finish()
            }
            .addOnFailureListener{
                Toast.makeText(this, "失敗しました", Toast.LENGTH_SHORT)
            }
    }
}

class Room(val roomId: String, val roomName: String, val discription: String){
    constructor(): this("", "", "")
}