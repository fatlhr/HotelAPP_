package com.fatiharahmat.hotelapp.home.dashboard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fatiharahmat.hotelapp.R
import com.fatiharahmat.hotelapp.model.Hotel

class HotelTerdekatAdapter(
    private var data: List<Hotel>,
    private val listener: (Hotel) -> Unit)
    : RecyclerView.Adapter<HotelTerdekatAdapter.ViewHolder>() {

    private lateinit var contextAdapter: Context

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvNamaHotel: TextView = view.findViewById(R.id.tv_namaHotel)
        private val tvBiaya: TextView = view.findViewById(R.id.tv_biaya)
        private val tvRate: TextView = view.findViewById(R.id.tv_rate)
        private val tvPoster: ImageView = view.findViewById(R.id.iv_ic_fasilitas)

        fun bindItem(data: Hotel, listener: (Hotel) -> Unit, context: Context, position: Int) {

            tvNamaHotel.text = data.namaHotel
            tvBiaya.text = data.biaya
            tvRate.text = data.rating

            Glide.with(context)
                .load(data.poster)
                .into(tvPoster)

            itemView.setOnClickListener {
                listener(data)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HotelTerdekatAdapter.ViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        contextAdapter = parent.context
        val inflatedView: View =
            layoutInflater.inflate(R.layout.row_item_hotel_terdekat, parent, false)

        return ViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: HotelTerdekatAdapter.ViewHolder, position: Int) {
        holder.bindItem(data[position], listener, contextAdapter, position)
    }

    override fun getItemCount(): Int = data.size

    }