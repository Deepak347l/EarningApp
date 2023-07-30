package com.deepakdev.earnpocketmoney

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi

class UPIModel : Parcelable {
    var Amount: String? = null
    var status: Boolean? = null

    constructor(){}
    @RequiresApi(Build.VERSION_CODES.Q)
    protected constructor(`in`: Parcel) {
        Amount = `in`.readString()
        status = `in`.readBoolean()
    }
    constructor(
        Amount: String?,
        status: Boolean?,
    ) {
        this.Amount = Amount
        this.status = status
    }
    override fun describeContents(): Int {
        return 0
    }
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(Amount)
        dest.writeBoolean(status!!)
    }
    companion object {
        val CREATOR: Parcelable.Creator<UPIModel?> = object : Parcelable.Creator<UPIModel?> {
            @RequiresApi(Build.VERSION_CODES.Q)
            override fun createFromParcel(`in`: Parcel): UPIModel {
                return UPIModel(`in`)
            }

            override fun newArray(size: Int): Array<UPIModel?> {
                return arrayOfNulls(size)
            }
        }
    }
}