package com.fatiharahmat.hotelapp.home.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fatiharahmat.hotelapp.EditProfileActivity
import com.fatiharahmat.hotelapp.MyWalletActivity
import com.fatiharahmat.hotelapp.R
import com.fatiharahmat.hotelapp.sign.signin.SignInActivity
import com.fatiharahmat.hotelapp.utils.Preferences
import kotlinx.android.synthetic.main.fragment_profile.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    companion object {
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

/*    private fun clearData(){
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
*/
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        preferences = Preferences(requireContext())

        tvNama.text = preferences.getValues("nama")
        tvEmail.text = preferences.getValues("email")



        Glide.with(this)
            .load(preferences.getValues("url"))
            .apply(RequestOptions.circleCropTransform())
            .into(ivProfil)

        btnEditProfil.setOnClickListener {
            startActivity(Intent(activity, EditProfileActivity::class.java))
        }

        btnWallet.setOnClickListener {
            startActivity(Intent(activity, MyWalletActivity::class.java))
        }

        btnHelp.setOnClickListener {
        }

        btnLogout.setOnClickListener {
            //clearData()
            preferences.setValues("status","0")
            finishAffinity(requireActivity())
            startActivity(Intent(activity, SignInActivity::class.java))
        }

    }

}