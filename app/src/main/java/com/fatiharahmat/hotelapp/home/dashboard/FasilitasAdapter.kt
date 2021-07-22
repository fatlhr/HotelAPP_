package com.fatiharahmat.hotelapp.home.dashboard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fatiharahmat.hotelapp.R
import com.fatiharahmat.hotelapp.model.FasilitasModel

class FasilitasAdapter(private var data: List<FasilitasModel>,
                       private val listener: (FasilitasModel) -> Unit)
    : RecyclerView.Adapter<FasilitasAdapter.LeagueViewHolder>() {

    lateinit var contextAdapter: Context

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LeagueViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        contextAdapter = parent.context
        val inflatedView: View = layoutInflater.inflate(R.layout.row_item_fasilitas, parent, false)

        return LeagueViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: LeagueViewHolder, position: Int) {
        holder.bindItem(data[position], listener, contextAdapter, position)
    }

    override fun getItemCount(): Int = data.size

    class LeagueViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val tvNamaFasilitas: TextView = view.findViewById(R.id.tv_fasilitas)
        private val ivFasilitas: ImageView = view.findViewById(R.id.iv_ic_fasilitas)

        fun bindItem(data: FasilitasModel, listener: (FasilitasModel) -> Unit, context: Context, position: Int) {
            tvNamaFasilitas.text = data.nama

            Glide.with(context)
                .load(data.url)
                .apply(RequestOptions.circleCropTransform())
                .into(ivFasilitas)

            itemView.setOnClickListener {
                listener(data)
            }
        }

    }

}