package com.fatiharahmat.hotelapp.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fatiharahmat.hotelapp.R
import kotlinx.android.synthetic.main.activity_onboarding_one.*
import kotlinx.android.synthetic.main.activity_onboarding_one.btn_masuk
import kotlinx.android.synthetic.main.activity_onboarding_two.*

class OnboardingTwoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_two)

        btn_masuk.setOnClickListener {
            finishAffinity()
            val intent = Intent(this@OnboardingTwoActivity, OnboardingThreeActivity::class.java)
            startActivity(intent)
        }
    }
}