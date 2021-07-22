package com.fatiharahmat.hotelapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FasilitasModel (
    var nama: String ?="",
    var url: String ?=""
): Parcelable