package com.websarva.wings.android.wispace

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_chat_log.*
import kotlinx.android.synthetic.main.chat_from_row.view.*
import kotlinx.android.synthetic.main.chat_to_row.view.*

class ChatLog : AppCompatActivity() {

    var adapter = GroupAdapter<ViewHolder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_log)

        recyclerview_chat_log.adapter = adapter

        listenForMessage()

        send_button_chat_log.setOnClickListener {
            performSendMessage()
            editText_chat_log.text.clear()
        }

        back_button.setOnClickListener {
            finish()
        }
    }

    private fun listenForMessage(){

        val ref = FirebaseDatabase.getInstance().getReference("/chat/room")

        ref.addChildEventListener(object: ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {

                val chatMessage = snapshot.getValue(ChatMessage::class.java)

                Log.d("ChatLog", "${chatMessage?.text}")

                if(chatMessage != null){
                    val uid = FirebaseAuth.getInstance().uid
                    if(chatMessage.fromId == uid){
                        set_ChatToItem(chatMessage, uid)
                    }else{
                        set_ChatFromItem(chatMessage, chatMessage.fromId)
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

    private fun set_ChatFromItem(chatMessage: ChatMessage, uid: String){
        val ref = FirebaseDatabase.getInstance().getReference("/user/$uid")
        ref.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java)
                if(user == null) return
                adapter.add(ChatFromItem(chatMessage.text, user))
                recyclerview_chat_log.scrollToPosition(adapter.itemCount - 1)
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun set_ChatToItem(chatMessage: ChatMessage, uid: String){
        val ref = FirebaseDatabase.getInstance().getReference("/user/$uid")
        ref.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java)
                if(user == null) return
                adapter.add(ChatToItem(chatMessage.text, user))
                recyclerview_chat_log.scrollToPosition(adapter.itemCount - 1)
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun performSendMessage(){

        val text = editText_chat_log.text.toString()

        val fromId = FirebaseAuth.getInstance().uid
        val roomName = "room"

        if(fromId == null) return
        if(roomName == null) return

        val reference = FirebaseDatabase.getInstance().getReference("/chat/$roomName").push()

        val chatMessage = ChatMessage(reference.key!!, text, fromId, roomName, System.currentTimeMillis()/1000)
        reference.setValue(chatMessage)
            .addOnSuccessListener {
                Log.d("ChatLog", "Saved our chat message: ${reference.key}")
            }

    }
}

class ChatFromItem(val text: String, val user: User): Item<ViewHolder>(){
    override fun bind(viewHolder: ViewHolder, position: Int) {

        viewHolder.itemView.textView_chat_from_row.text = text

        val uri = user.profileImageUri
        val targetView = viewHolder.itemView.imageView_chat_from_row
        Picasso.get().load(uri).into(targetView)

    }

    override fun getLayout(): Int {
        return R.layout.chat_from_row
    }
}

class ChatToItem(val text: String, val user: User): Item<ViewHolder>(){
    override fun bind(viewHolder: ViewHolder, position: Int) {

        viewHolder.itemView.textView_chat_to_row.text = text

        val uri = user.profileImageUri
        val targetView = viewHolder.itemView.imageView_chat_to_row
        Picasso.get().load(uri).into(targetView)
    }

    override fun getLayout(): Int {
        return R.layout.chat_to_row
    }
}