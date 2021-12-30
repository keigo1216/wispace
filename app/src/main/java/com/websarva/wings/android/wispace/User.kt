package com.websarva.wings.android.wispace

import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class User(val uid: String, val username: String, val profileImageUri: String): Parcelable {
    constructor(): this("", "", "")
}