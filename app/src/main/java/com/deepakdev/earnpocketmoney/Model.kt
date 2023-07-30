package com.deepakdev.earnpocketmoney

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi

class Model: Parcelable {
     var campid: String? = null
     var amount: String? = null
     var conversion: Boolean? = null

       constructor(){}
     @RequiresApi(Build.VERSION_CODES.Q)
     protected constructor(`in`: Parcel) {
         campid = `in`.readString()
         amount= `in`.readString()
         conversion = `in`.readBoolean()
     }
    constructor(
        campid: String?,
        amount: String?,
        conversion: Boolean?,
    ) {
        this.campid = campid
        this.amount = amount
        this.conversion = conversion
    }
    override fun describeContents(): Int {
        return 0
    }
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(campid)
        dest.writeString(amount)
        dest.writeBoolean(conversion!!)
    }
    companion object {
        val CREATOR: Parcelable.Creator<Model?> = object : Parcelable.Creator<Model?> {
            @RequiresApi(Build.VERSION_CODES.Q)
            override fun createFromParcel(`in`: Parcel): Model {
                return Model(`in`)
            }

            override fun newArray(size: Int): Array<Model?> {
                return arrayOfNulls(size)
            }
        }
    }
}