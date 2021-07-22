package com.fatiharahmat.hotelapp.sign.signin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fatiharahmat.hotelapp.R
import com.fatiharahmat.hotelapp.home.HomeActivity
import com.fatiharahmat.hotelapp.sign.signup.SignUpActivity
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_sign_in.*


class SignInActivity : AppCompatActivity() {

    lateinit var iUsername:String
    lateinit var iPassword:String
    lateinit var mDatabase : DatabaseReference
    lateinit var preferences : com.fatiharahmat.hotelapp.utils.Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        mDatabase = FirebaseDatabase.getInstance().getReference("User")
        preferences = com.fatiharahmat.hotelapp.utils.Preferences(this)

        preferences.setValues("onboarding","1")
        if(preferences.getValues("status").equals("1")){
            finishAffinity()

            val goHome =Intent(this@SignInActivity, HomeActivity::class.java)
            startActivity(goHome)
        }

        btn_masuk.setOnClickListener {
            iUsername = et_username.text.toString()
            iPassword = et_password.text.toString()

            if (iUsername.equals("")){
                et_username.error = "Silahkan isi Username!"
                et_username.requestFocus()
            } else if (iPassword.equals("")){
                et_password.error = "Silahkan isi Password!"
                et_password.requestFocus()
            } else{
                pushLogin(iUsername, iPassword)
            }
        }

        btn_daftar.setOnClickListener {
            finishAffinity()
            val intent = Intent(this@SignInActivity, SignUpActivity::class.java)
            startActivity(intent)
        }

    }

    private fun pushLogin(iUsername: String, iPassword: String) {
        mDatabase.child(iUsername).addValueEventListener(object : ValueEventListener{

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var user = dataSnapshot.getValue(User::class.java)
                if(user == null){
                    Toast.makeText(this@SignInActivity,"Username tidak ada!",
                    Toast.LENGTH_LONG).show()
                }
                else {
                    if(user.password.equals(iPassword)){

                        preferences.setValues("nama", user.nama.toString())
                        preferences.setValues("email", user.email.toString())
                        preferences.setValues("username", user.username.toString())
                        preferences.setValues("saldo", user.saldo.toString())
                        preferences.setValues("url", user.url.toString())
                        preferences.setValues("status", "1")

                        val intent = Intent(this@SignInActivity, HomeActivity::class.java)
                        startActivity(intent)
                    }else{
                        Toast.makeText(this@SignInActivity,"Password salah!",
                            Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@SignInActivity,error.message,
                    Toast.LENGTH_LONG).show()
            }
        })
    }
}