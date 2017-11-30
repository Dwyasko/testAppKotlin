package com.yascode.testapp.data.local

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by caksono21 on 29/11/17.
 */
data class Content(
        val id: Int,
        val summary: String,
        val detail: String,
        val thumbnail_url: String,
        val original_url: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(summary)
        parcel.writeString(detail)
        parcel.writeString(thumbnail_url)
        parcel.writeString(original_url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Content> {
        override fun createFromParcel(parcel: Parcel): Content {
            return Content(parcel)
        }

        override fun newArray(size: Int): Array<Content?> {
            return arrayOfNulls(size)
        }
    }
}