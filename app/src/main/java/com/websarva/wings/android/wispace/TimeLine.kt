package com.websarva.wings.android.wispace

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_time_line.*
import kotlinx.android.synthetic.main.timeline_row.view.*

class TimeLine : AppCompatActivity() {

    val adapter = GroupAdapter<ViewHolder>()

    companion object{
        val ROOM_KEY = "ROOM_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_line)

        setIcon()

        listenRoomItem()

//        adapter.add(RoomItem())
//        adapter.add(RoomItem())

        recyclerView_timeline.adapter = adapter

        tweet.setOnClickListener {
            val intent = Intent(this, Tweet::class.java)
            startActivity(intent)
        }

        logout_button.setOnClickListener {
            Firebase.auth.signOut()
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }

    private fun setIcon(){

        val uid = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("/user/$uid")
        ref.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java)
                if(user != null){
                    Picasso.get().load(user.profileImageUri).into(icon_time_line)
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun listenRoomItem(){

        val ref = FirebaseDatabase.getInstance().getReference("/studyroom")

        ref.addChildEventListener(object: ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val room = snapshot.getValue(Room::class.java)
                if(room != null){
                    adapter.add(RoomItem(room))

                    adapter.setOnItemClickListener { item, view ->

                        val roomItem = item as RoomItem

                        val intent = Intent(view.context, StudyRoom::class.java)
                        intent.putExtra(ROOM_KEY, roomItem.room.roomId)
                        startActivity(intent)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onChildRemoved(snapshot: DataSnapshot) {

            }
        })

    }
}


class RoomItem(val room: Room): Item<ViewHolder>(){
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.title_timeline.text = room.roomName
        viewHolder.itemView.text_timeline.text = room.discription
    }

    override fun getLayout(): Int {
        return R.layout.timeline_row
    }
}