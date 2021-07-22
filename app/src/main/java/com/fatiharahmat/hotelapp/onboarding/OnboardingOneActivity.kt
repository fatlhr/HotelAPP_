package com.fatiharahmat.hotelapp.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fatiharahmat.hotelapp.R
import com.fatiharahmat.hotelapp.sign.signin.SignInActivity
import kotlinx.android.synthetic.main.activity_onboarding_one.*

class OnboardingOneActivity : AppCompatActivity() {

    lateinit var preference : com.fatiharahmat.hotelapp.utils.Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_one)

        preference = com.fatiharahmat.hotelapp.utils.Preferences(this)
        if(preference.getValues("onboarding").equals("1")){
            finishAffinity()
            var goHome =Intent(this@OnboardingOneActivity, SignInActivity::class.java)
            startActivity(goHome)
        }

        btn_daftar.setOnClickListener {
            preference.setValues("onboarding","1")
            finishAffinity()
            val intent = Intent(this@OnboardingOneActivity, SignInActivity::class.java)
            startActivity(intent)
        }
        btn_masuk.setOnClickListener {
            finishAffinity()
            val intent = Intent(this@OnboardingOneActivity, OnboardingTwoActivity::class.java)
            startActivity(intent)
        }

    }
}