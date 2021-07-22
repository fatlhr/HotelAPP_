 package com.fatiharahmat.hotelapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.fatiharahmat.hotelapp.home.dashboard.FasilitasAdapter
import com.fatiharahmat.hotelapp.model.FasilitasModel
import com.fatiharahmat.hotelapp.model.Hotel
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_hotel_detail.*


class HotelDetailActivity : AppCompatActivity() {

    private lateinit var mDatabase : DatabaseReference
    private var dataList = ArrayList<FasilitasModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hotel_detail)

        val data = intent.getParcelableExtra<Hotel>("data")

        mDatabase = FirebaseDatabase.getInstance().getReference("Hotel")
            .child(data!!.namaHotel.toString())
            .child("fasilitas")

        tv_namaHotel.text = data.namaHotel
        tv_biaya.text = data.biaya
        tv_deskripsi.text = data.deskripsi
        tv_lokasi.text = data.lokasi
        tv_rate2.text = data.rating

        Glide.with(this)
            .load(data.poster)
            .into(iv_poster)

        rv_fasilitas.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        //getData()

        mDatabase.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                dataList.clear()
                for(getdataSnapshot in snapshot.children){
                    val fasilitas = getdataSnapshot.getValue(FasilitasModel::class.java)
                    dataList.add(fasilitas!!)
                }

                rv_fasilitas.adapter = FasilitasAdapter(dataList){
                }

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@HotelDetailActivity, ""+error.message, Toast.LENGTH_LONG).show()
            }

        })
    }

}