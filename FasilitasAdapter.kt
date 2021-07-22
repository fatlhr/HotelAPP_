package com.fatiharahmat.hotelapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fatiharahmat.hotelapp.model.Fasilitas

class FasilitasAdapter(
    private var data: List<Fasilitas>,
    private val listener: (Fasilitas) -> Unit)
    : RecyclerView.Adapter<FasilitasAdapter.ViewHolder>() {

    lateinit var contextAdapter: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FasilitasAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        contextAdapter = parent.context
        val inflatedView: View =
            layoutInflater.inflate(R.layout.row_item_fasilitas, parent, false)

        return ViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(data[position], listener, contextAdapter, position)
    }

    override fun getItemCount(): Int = data.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvFasilitas: TextView = view.findViewById(R.id.tv_fasilitas)
        private val ivFasilitas: ImageView = view.findViewById(R.id.iv_ic_fasilitas)

        fun bindItem(
            data: Fasilitas,
            listener: (Fasilitas) -> Unit,
            context: Context,
            position: Int
        ) {

            tvFasilitas.text = data.nama

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