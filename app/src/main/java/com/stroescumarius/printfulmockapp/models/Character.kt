package com.stroescumarius.printfulmockapp.models

import android.os.Parcel
import android.os.Parcelable
import androidx.versionedparcelable.VersionedParcelize
import com.google.gson.annotations.SerializedName
import java.util.ArrayList

@VersionedParcelize
data class Character(
    var name: String?,
    var height: Int,
    var mass: Int,
    @SerializedName("hair_color")
    var hairColor: String?,
    @SerializedName("skin_color")
    var skinColor: String?,
    @SerializedName("eye_color")
    var eyeColor: String?,
    @SerializedName("birth_year")
    var birthYear: String?,
    var gender: String?,
    var films: ArrayList<String>?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.createStringArrayList()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(height)
        parcel.writeInt(mass)
        parcel.writeString(hairColor)
        parcel.writeString(skinColor)
        parcel.writeString(eyeColor)
        parcel.writeString(birthYear)
        parcel.writeString(gender)
        parcel.writeStringList(films?.toList())
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Character> {
        override fun createFromParcel(parcel: Parcel): Character {
            return Character(parcel)
        }

        override fun newArray(size: Int): Array<Character?> {
            return arrayOfNulls(size)
        }
    }

}
