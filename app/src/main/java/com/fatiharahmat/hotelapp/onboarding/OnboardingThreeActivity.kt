package com.fatiharahmat.hotelapp.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fatiharahmat.hotelapp.R
import com.fatiharahmat.hotelapp.sign.signin.SignInActivity
import kotlinx.android.synthetic.main.activity_onboarding_one.*
import kotlinx.android.synthetic.main.activity_onboarding_three.*

class OnboardingThreeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_three)

        btn_memulai.setOnClickListener {
            finishAffinity()
            val intent = Intent(this@OnboardingThreeActivity, SignInActivity::class.java)
            startActivity(intent)
        }
    }
}