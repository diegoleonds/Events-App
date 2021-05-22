package com.example.events.data.model

import android.os.Parcel
import android.os.Parcelable

data class EventPerson(
    val eventId : Long,
    val name : String,
    val email : String
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(eventId)
        parcel.writeString(name)
        parcel.writeString(email)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<EventPerson> {
        override fun createFromParcel(parcel: Parcel): EventPerson {
            return EventPerson(parcel)
        }

        override fun newArray(size: Int): Array<EventPerson?> {
            return arrayOfNulls(size)
        }
    }
}