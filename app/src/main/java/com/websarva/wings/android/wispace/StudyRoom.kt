package com.websarva.wings.android.wispace

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_study_room.*

class StudyRoom : AppCompatActivity() {

    var mButton = Array(4, {arrayOfNulls<ImageButton>(3)}) //ボタンのインスタンスを作成
    var mApper = Array(4, { arrayOfNulls<Boolean>(3)})
    var flag = false //今入っているかどうか
    var row_index: Int = -1 //0に意味はない、ただの初期化
    var col_index: Int = -1
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_study_room)

        val database = FirebaseDatabase.getInstance()
        val ref = database.getReference("room")
        val user = Firebase.auth.currentUser

        listenForState()

//
        mButton[0][0] = findViewById(R.id.bt_11)
        mButton[0][1] = findViewById(R.id.bt_12)
        mButton[0][2] = findViewById(R.id.bt_13)
        mButton[1][0] = findViewById(R.id.bt_21)
        mButton[1][1] = findViewById(R.id.bt_22)
        mButton[1][2] = findViewById(R.id.bt_23)
        mButton[2][0] = findViewById(R.id.bt_31)
        mButton[2][1] = findViewById(R.id.bt_32)
        mButton[2][2] = findViewById(R.id.bt_33)
        mButton[3][0] = findViewById(R.id.bt_41)
        mButton[3][1] = findViewById(R.id.bt_42)
        mButton[3][2] = findViewById(R.id.bt_43)

        //init mApper in false
        for(i in (0..3)){
            for(j in (0..2)){
                mApper[i][j] = false
            }
        }

        whether_resumu()

        for(i in (0..3)){
            for(j in (0..2)){
                mButton[i][j]!!.setOnClickListener{
                    if(flag){ //すでにクリックしているとき
                        if(col_index == i && row_index == j){ //自分が押しているボタンの時
                            user?.let {
                                var uid = user.uid
                                ref.child("${uid}").removeValue()//
                                flag = false//flagを初期化
//                                mButton[i][j]!!.setBackgroundResource(R.drawable.microsoftteams_image__2_) //画像をもとに戻す
                            }
                        }
                    }else{

                        if(mApper[i][j] == false){
                            user?.let { // Name, email address, and profile photo Url
                                var uid = user.uid
                                col_index = i
                                row_index = j
                                flag = true
//                            mButton[i][j]!!.setBackgroundResource(R.drawable.studyroom_clicked) //画像を変更
//                            ref.child("${uid}").setValue(3 * i + j) //データベースに追加
                                ref.child("$uid").setValue(UserInRoom(uid, 3 * i + j))
                            }
                        }
                    }
                }
            }
        }

        chat_button.setOnClickListener {
            val intent = Intent(this, ChatLog::class.java)
            startActivity(intent)
        }

    }

    private fun whether_resumu(){
        val uid = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("/room/$uid")
        ref.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val roomstate = snapshot.getValue(UserInRoom::class.java)
                if(roomstate == null) return

                Log.d("StudyRoom", "${roomstate}")

                col_index = roomstate.location / 3
                row_index = roomstate.location % 3
                flag = true
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun listenForState(){

        val ref = FirebaseDatabase.getInstance().getReference("/room")

        ref.addChildEventListener(object: ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val roomstate = snapshot.getValue(UserInRoom::class.java)

                Log.d("test", "hellolllllll")

                if(roomstate != null){
                    val i = roomstate.location / 3
                    val j = roomstate.location % 3
                    mButton[i][j]!!.setBackgroundResource(R.drawable.studyroom_clicked)
                    mApper[i][j] = true
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onChildRemoved(snapshot: DataSnapshot) {

                val roomstate = snapshot.getValue(UserInRoom::class.java)

                if(roomstate != null){
                    val i = roomstate.location / 3
                    val j = roomstate.location % 3
                    mButton[i][j]!!.setBackgroundResource(R.drawable.microsoftteams_image__2_)
                    mApper[i][j] = false
                }

                Log.d("test", "${roomstate!!.location}")


            }
        })
    }

//    override fun onPause() {
//        super.onPause()
//
//        if(flag){
//            val ref = FirebaseDatabase.getInstance().getReference("room")
//            val uid = FirebaseAuth.getInstance().uid
//            ref.child("${uid}").removeValue()//
//            flag = false//flagを初期化
//        }
//    }

}

class UserInRoom(val uid: String, val location: Int){
    constructor(): this("", -1)
}