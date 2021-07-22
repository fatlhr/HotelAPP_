package com.fatiharahmat.hotelapp.home.dashboard

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fatiharahmat.hotelapp.HotelDetailActivity
import com.fatiharahmat.hotelapp.R
import com.fatiharahmat.hotelapp.model.Hotel
import com.fatiharahmat.hotelapp.utils.Preferences
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_dashboard.*
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class DashboardFragment : Fragment(R.layout.fragment_dashboard){

    private lateinit var preferences : Preferences
    private lateinit var mDatabase : DatabaseReference

    lateinit var iChekIn:String
    lateinit var iCheckOut:String

    private var dataList = ArrayList<Hotel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    companion object {
        fun newInstance(param1: String, param2: String) =
            DashboardFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val cIn = Calendar.getInstance()
        val yearIn = cIn.get(Calendar.YEAR)
        val monthIn = cIn.get(Calendar.MONTH)
        val dayIn = cIn.get(Calendar.DAY_OF_MONTH)

        val cOut = Calendar.getInstance()
        val yearOut = cOut.get(Calendar.YEAR)
        val monthOut = cOut.get(Calendar.MONTH)
        val dayOut = cOut.get(Calendar.DAY_OF_MONTH)


        tv_checkIn.setOnClickListener {
            val dpdIn = DatePickerDialog(requireActivity(), { _: View, mYearIn, mMonthIn, mDayIn ->
                if(mDayIn < 10 && mMonthIn < 10){
                    tv_checkIn.setText("0$mDayIn/0${mMonthIn+1}/$mYearIn")
                }else if(mDayIn < 10){
                    tv_checkIn.setText("0$mDayIn/${mMonthIn+1}/$mYearIn")
                }else if(mMonthIn < 10){
                    tv_checkIn.setText("$mDayIn/0${mMonthIn+1}/$mYearIn")
                }
                else{
                    tv_checkIn.setText("$mDayIn/${mMonthIn+1}/$mYearIn")}
            },yearIn, monthIn, dayIn)
            dpdIn.datePicker.minDate = System.currentTimeMillis() + 1
            dpdIn.show()
        }

        tv_checkOut.setOnClickListener {
            val dpdOut = DatePickerDialog(requireActivity(), { _: View, mYearOut, mMonthOut, mDayOut ->
                if(mDayOut < 10 && mMonthOut < 10){
                    tv_checkOut.setText("0$mDayOut/0${mMonthOut+1}/$mYearOut")
                }else if(mDayOut < 10){
                tv_checkOut.setText("0$mDayOut/${mMonthOut+1}/$mYearOut")
                }else if(mMonthOut < 10){
                    tv_checkOut.setText("$mDayOut/0${mMonthOut+1}/$mYearOut")
                }
                else{
                tv_checkOut.setText("$mDayOut/${mMonthOut+1}/$mYearOut")}
            },yearOut, monthOut, dayOut)
            dpdOut.datePicker.minDate = System.currentTimeMillis() + 1
            dpdOut.show()
        }

        preferences = Preferences(requireActivity().applicationContext)
        mDatabase = FirebaseDatabase.getInstance().getReference("Hotel")

        tv_nama.setText(preferences.getValues("nama"))
        if(!preferences.getValues("saldo").equals("")){
            currency(preferences.getValues("saldo")!!.toDouble(), tv_saldo)
        }

        Glide.with(this)
                .load(preferences.getValues("url"))
                .apply(RequestOptions.circleCropTransform())
                .into(iv_profil)

        rv_hotel_rekomendasi.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rv_hotel_terdekat.layoutManager = LinearLayoutManager(context)

        getData()
    }

    private fun getData() {
        mDatabase.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataList.clear()
                for (getdataSnapshot in dataSnapshot.getChildren()){
                    var hotel = getdataSnapshot.getValue(Hotel::class.java!!)
                    dataList.add(hotel!!)
                }

                if (dataList.isNotEmpty()) {
                    rv_hotel_rekomendasi.adapter = HotelRekomendasiAdapter(dataList) {
                        val intent = Intent(
                            context,
                            HotelDetailActivity::class.java
                        ).putExtra("data", it)
                        startActivity(intent)
                    }

                    rv_hotel_terdekat.adapter = HotelTerdekatAdapter(dataList) {
                        val intent = Intent(
                            context,
                            HotelDetailActivity::class.java
                        ).putExtra("data", it)
                        startActivity(intent)
                    }
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(context, ""+databaseError.message, Toast.LENGTH_LONG)
            }

        })
    }

    private fun currency(biaya : Double, textView: TextView){
        val localeId = Locale("in", "ID")
        val formatRupiah = NumberFormat.getCurrencyInstance(localeId)
        textView.setText(formatRupiah.format(biaya))
    }



}