package com.fatiharahmat.hotelapp.home

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.fatiharahmat.hotelapp.R
import com.fatiharahmat.hotelapp.home.dashboard.DashboardFragment
import com.fatiharahmat.hotelapp.home.profile.ProfileFragment
import com.fatiharahmat.hotelapp.utils.Preferences
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    //lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //preferences.setValues("status","1")

        val fragmentHome = DashboardFragment()
        val fragmentFavorites = FavoritesFragment()
        val fragmentOrder = OrderFragment()
        val fragmentProfile = ProfileFragment()

        setFragment(fragmentHome)

        if (fragmentHome != null){
            changeIcon(iv_menu_home, R.drawable.ic_home)
            changeIcon(iv_menu_favorites, R.drawable.ic_favorites_inactive)
            changeIcon(iv_menu_order, R.drawable.ic_orders_inactive)
            changeIcon(iv_menu_profile, R.drawable.ic_profile_inactive)
        }

        iv_menu_home.setOnClickListener{
            setFragment(fragmentHome)

            changeIcon(iv_menu_home, R.drawable.ic_home)
            changeIcon(iv_menu_favorites, R.drawable.ic_favorites_inactive)
            changeIcon(iv_menu_order, R.drawable.ic_orders_inactive)
            changeIcon(iv_menu_profile, R.drawable.ic_profile_inactive)
        }

        iv_menu_profile.setOnClickListener{
            setFragment(fragmentProfile)

            changeIcon(iv_menu_home, R.drawable.ic_home_inactive)
            changeIcon(iv_menu_favorites, R.drawable.ic_favorites_inactive)
            changeIcon(iv_menu_order, R.drawable.ic_orders_inactive)
            changeIcon(iv_menu_profile, R.drawable.ic_profile)
        }

        iv_menu_favorites.setOnClickListener{
            setFragment(fragmentFavorites)

            changeIcon(iv_menu_home, R.drawable.ic_home_inactive)
            changeIcon(iv_menu_favorites, R.drawable.ic_favorites)
            changeIcon(iv_menu_order, R.drawable.ic_orders_inactive)
            changeIcon(iv_menu_profile, R.drawable.ic_profile_inactive)
        }

        iv_menu_order.setOnClickListener{
            setFragment(fragmentOrder)

            changeIcon(iv_menu_home, R.drawable.ic_home_inactive)
            changeIcon(iv_menu_favorites, R.drawable.ic_favorites_inactive)
            changeIcon(iv_menu_order, R.drawable.ic_orders)
            changeIcon(iv_menu_profile, R.drawable.ic_profile_inactive)
        }
    }

    private fun setFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTrasaction = fragmentManager.beginTransaction()
        fragmentTrasaction.replace(R.id.layout_frame, fragment)
        fragmentTrasaction.commit()
    }

    private fun changeIcon(imageView: ImageView, int: Int){
        imageView.setImageResource(int)
    }
}