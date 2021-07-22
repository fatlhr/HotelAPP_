package com.fatiharahmat.hotelapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Hotel (
    var biaya: String ?="",
    var deskripsi: String ?="",
    var lokasi: String ?="",
    var namaHotel: String ?="",
    var poster: String ?="",
    var rating: String ?="",
): Parcelable