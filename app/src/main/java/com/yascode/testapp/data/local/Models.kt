package com.yascode.testapp.data.local

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by caksono21 on 29/11/17.
 */
data class Content(
        val imgPath: String,
        val imgDesc: String,
        val imgSum: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(imgPath)
        parcel.writeString(imgDesc)
        parcel.writeString(imgSum)
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