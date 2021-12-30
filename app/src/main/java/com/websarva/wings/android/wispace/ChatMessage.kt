package com.websarva.wings.android.wispace

//チャットをfirebaseに記録するためのクラス
class ChatMessage(val id: String, val text: String, val fromId: String, val roomName: String, val timestamp: Long) {
    constructor(): this("", "", "", "", -1)
}