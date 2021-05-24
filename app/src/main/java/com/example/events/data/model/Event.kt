package com.example.events.data.model

import android.os.Parcel
import android.os.Parcelable

data class Event(
    val id: Long,
    val title: String,
    val description: String,
    val image: String,
    val price: Double,
    val date: Long,
    val people: ArrayList<Person>
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readDouble(),
        parcel.readLong(),
        parcel.createTypedArrayList(Person) ?: ArrayList<Person>()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(image)
        parcel.writeDouble(price)
        parcel.writeLong(date)
        parcel.writeTypedList(people)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Event> {
        override fun createFromParcel(parcel: Parcel): Event {
            return Event(parcel)
        }

        override fun newArray(size: Int): Array<Event?> {
            return arrayOfNulls(size)
        }
    }
}