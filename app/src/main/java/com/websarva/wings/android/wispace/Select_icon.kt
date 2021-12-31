package com.websarva.wings.android.wispace

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Icon
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import android.widget.SimpleAdapter
import androidx.core.content.FileProvider
import androidx.core.view.drawToBitmap
import com.fasterxml.jackson.databind.util.ClassUtil.getPackageName
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_select_icon.*
import kotlinx.android.synthetic.main.select_icon_images.view.*
import java.io.File
import java.io.FileOutputStream

class Select_icon : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_icon)

        Log.d("sample", "select icon oncreate")

        val adapter = GroupAdapter<ViewHolder>()
        adapter.add(IconItem(1, 2))
        adapter.add(IconItem(3, 4))
        adapter.add(IconItem(5, 6))
        adapter.add(IconItem(7, 8))
        adapter.add(IconItem(9, 10))
        adapter.add(IconItem(11, 12))

        val dir = FirebaseStorage.getInstance().getReference("/icon/icon_1.png")
//        Log.d("Select_icon", "${dir.downloadUrl}")

        dir.downloadUrl.addOnSuccessListener {
            Log.d("Select_icon", "$it")
        }

        recyclerView_icon_select.adapter = adapter

    }

    inner class IconItem(val icon_left: Int, val icon_right: Int): Item<ViewHolder>() {
        override fun bind(viewHolder: ViewHolder, position: Int) {
            val left_viewId = resources.getIdentifier("icon_${icon_left}", "drawable", packageName)
            val right_viewId = resources.getIdentifier("icon_${icon_right}", "drawable", packageName)
            viewHolder.itemView.imageView_left.setImageResource(left_viewId)
            viewHolder.itemView.imageView_right.setImageResource(right_viewId)
        }

        override fun getLayout(): Int {
            return R.layout.select_icon_images
        }
    }
}