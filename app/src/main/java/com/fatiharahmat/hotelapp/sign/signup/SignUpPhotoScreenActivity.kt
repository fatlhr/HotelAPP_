package com.fatiharahmat.hotelapp.sign.signup

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fatiharahmat.hotelapp.R
import com.fatiharahmat.hotelapp.home.HomeActivity
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.activity_sign_up_photoscreen.*
import java.util.*

class SignUpPhotoScreenActivity : AppCompatActivity(), PermissionListener {

    // request pencarian foto
    val REQUEST_IMAGE_CAPTURE = 1
    // status pencarian foto
    var statusAdd:Boolean = false
    // path foto firebase
    lateinit var filePath: Uri
    lateinit var storage : FirebaseStorage
    lateinit var storageReference : StorageReference
    lateinit var preferences: com.fatiharahmat.hotelapp.utils.Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_photoscreen)

        preferences = com.fatiharahmat.hotelapp.utils.Preferences(this)
        storage = FirebaseStorage.getInstance()
        storageReference = storage.getReference()

        tv_selamat_datang.text = "Selamat Datang\n"+intent.getStringExtra("nama")
        iv_foto.setOnClickListener {
            if(statusAdd){
                statusAdd = false
                btn_simpan.visibility = View.VISIBLE
                iv_add.setImageResource(R.drawable.ic_btn_add)
                iv_foto.setImageResource(R.drawable.ic_account_pic)
            }
            else {
                ImagePicker.with(this)
                    .cameraOnly()	//User can only capture image using Camera
                    .cropSquare()	//Crop square image, its same as crop(1f, 1f)
                    .start()
            }
        }

        btn_upload_nanti.setOnClickListener {
            finishAffinity()
            var goHome = Intent(this@SignUpPhotoScreenActivity, HomeActivity::class.java)
            startActivity(goHome)
        }

        btn_simpan.setOnClickListener{
            if (filePath != null){
                val progressDialog = ProgressDialog(this)
                progressDialog.setTitle("Uploading...")
                progressDialog.show()

                val ref = storageReference.child("images/" + UUID.randomUUID().toString())
                ref.putFile(filePath)
                    .addOnSuccessListener {
                        progressDialog.dismiss()
                        Toast.makeText(this@SignUpPhotoScreenActivity, "Upload berhasil", Toast.LENGTH_SHORT).show()

                        ref.downloadUrl.addOnSuccessListener {
                            preferences.setValues("url", it.toString())
                        }
                        finishAffinity()
                        var goHome = Intent(this@SignUpPhotoScreenActivity, HomeActivity::class.java)
                        startActivity(goHome)
                    }
                    .addOnFailureListener{
                        progressDialog.dismiss()
                        Toast.makeText(this@SignUpPhotoScreenActivity, "File Gagal diupload!", Toast.LENGTH_LONG).show()
                    }
/*ini */            .addOnProgressListener { taskSnapshot ->
                        val progress = 100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount
                        progressDialog.setMessage("Uploaded " + progress.toInt() + "%")
                    }
            }

        }

    }

    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
        Toast.makeText(this, "Tambah profile tidak diizinkan!", Toast.LENGTH_LONG ).show()
    }

    override fun onPermissionRationaleShouldBeShown(
        permission: PermissionRequest?,
        token: PermissionToken?
    ) {
        TODO("Not yet implemented")
    }

    override fun onBackPressed() {
        Toast.makeText(this, "Silahlan klik tombol Upload Nanti", Toast.LENGTH_LONG ).show()
    }

    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            statusAdd = true
            filePath = data?.data!!

            Glide.with(this)
                .load(filePath)
                .apply(RequestOptions.circleCropTransform())
                .into(iv_foto)

            btn_simpan.visibility = View.VISIBLE
            iv_add.setImageResource(R.drawable.ic_delete)
        }
        else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }

}