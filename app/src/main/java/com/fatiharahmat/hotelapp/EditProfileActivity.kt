package com.fatiharahmat.hotelapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fatiharahmat.hotelapp.home.dashboard.DashboardFragment
import com.fatiharahmat.hotelapp.sign.signin.User
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.activity_sign_up.et_email
import kotlinx.android.synthetic.main.activity_sign_up.et_nama
import kotlinx.android.synthetic.main.activity_sign_up.et_password
import kotlinx.android.synthetic.main.activity_sign_up.et_username

class EditProfileActivity : AppCompatActivity() {

    //lateinit var preferences: Preferences
    lateinit var uUsername:String
    lateinit var uPassword:String
    lateinit var uEmail:String
    lateinit var uNama:String

    lateinit var mFirebaseInstance : FirebaseDatabase
    private lateinit var mDatabase : DatabaseReference
    lateinit var mDatabaseReference: DatabaseReference
    lateinit var preferences : com.fatiharahmat.hotelapp.utils.Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        mFirebaseInstance = FirebaseDatabase.getInstance()
        mDatabase = FirebaseDatabase.getInstance().getReference("User")
        mDatabaseReference = mFirebaseInstance.getReference("User")

        btnPerbarui.setOnClickListener {
            uUsername = et_username.text.toString()
            uPassword = et_password.text.toString()
            uNama = et_nama.text.toString()
            uEmail = et_email.text.toString()

            if (uUsername.equals("")){
                et_username.error = "Silahkan isi Username!"
                et_username.requestFocus()
            }
            else if (uPassword.equals("")){
                et_password.error = "Silahkan isi Username!"
                et_password.requestFocus()
            }
            else if (uNama.equals("")){
                et_nama.error = "Silahkan isi Username!"
                et_nama.requestFocus()
            }
            else if (uEmail.equals("")){
                et_email.error = "Silahkan isi Username!"
                et_email.requestFocus()
            }
            else {
                saveUsername (uUsername, uPassword, uNama, uEmail)
            }
        }

    }
    private fun saveUsername(uUsername: String, uPassword: String, uNama: String, uEmail: String) {
        val user = User()
        user.username = uUsername
        user.password = uPassword
        user.nama = uNama
        user.email = uEmail

        if(uUsername != null){
            checkingUsername(uUsername, user)
        }
    }

    private fun checkingUsername(uUsername: String, data: User) {
        mDatabaseReference.child(uUsername).addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val user  = dataSnapshot.getValue(User::class.java)
                //val key = data.username.toString()
                //val key  = preferences.getValues("username")

                if (user == null) {
                    mDatabaseReference.child(uUsername).setValue(data)
                    this@EditProfileActivity.uUsername = et_username.text.toString()
                    val intent = Intent(
                        this@EditProfileActivity,
                        DashboardFragment::class.java
                    )
                    startActivity(intent)
                }
                else{
                    Toast.makeText(this@EditProfileActivity, "Username sudah digunakan...", Toast.LENGTH_LONG).show()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@EditProfileActivity, ""+databaseError.message, Toast.LENGTH_LONG).show()
            }

        })
    }

}