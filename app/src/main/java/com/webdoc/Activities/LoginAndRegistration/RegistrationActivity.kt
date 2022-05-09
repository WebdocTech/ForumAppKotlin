package com.webdoc.Activities.LoginAndRegistration

import android.Manifest.permission
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import com.tangxiaolv.telegramgallery.GalleryActivity
import com.tangxiaolv.telegramgallery.GalleryConfig
import com.webdoc.Activities.LoginAndRegistration.ViewModels.RegistrationViewModel
import com.webdoc.Activities.MainActivity
import com.webdoc.Essentials.Global
import com.webdoc.theforum.databinding.ActivityRegistrationBinding
import java.io.File

class RegistrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrationBinding
    private val WRITE_PERMISSION = 2
    val GALLERY_REQUEST_CODE = 200
    var fileType: String? = null
    private var phoneNo: String? = null
    lateinit var uri: Uri
    var file: File? = null
    var imageLink: String? = null
    var userName: String? = null
    var userEmail: String? = null
    var userType: String? = null
    var userPin: String? = null
    var userConfirmPin: String? = null
    var registrationViewModel: RegistrationViewModel? = null
    lateinit var prefs: SharedPreferences
    lateinit var edit: SharedPreferences.Editor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViews()
        clickListerners()
        observers()
    }

    private fun observers() {
        registrationViewModel!!.LDRegistration().observe(this) { response ->
            if (response != null) {
                if (response.result!!.responseCode.equals("0000")) {
                    Toast.makeText(this@RegistrationActivity, "Customer Added", Toast.LENGTH_SHORT)
                        .show()
//                    edit.putString("id", uid)
//                    edit.commit()
//                    edit.apply()
//                    Preferences.getInstance(applicationContext).setKeyIsRegister(true)
//                    Preferences.getInstance(applicationContext).setKeyIsLogin(true)
//                    Preferences.getInstance(applicationContext).setKeyUserName(name)
//                    Preferences.getInstance(applicationContext).setKeyUserPhone(phoneNo)
//                    Preferences.getInstance(applicationContext).setKEY_USER_Address(homeAddress)
//                    Preferences.getInstance(applicationContext).setKEY_USER_City(city)
                    Global.utils!!.hideCustomLoadingDialog()
                    startActivity(Intent(this@RegistrationActivity, MainActivity::class.java))
                    finishAffinity()
                } else {
//                    Preferences.getInstance(applicationContext).setKeyIsLogin(true)
//                    //  Preferences.getInstance(getApplicationContext()).setKeyIsRegister(true);
//                    Preferences.getInstance(applicationContext).setKeyUserName(name)
                    Global.utils!!.hideCustomLoadingDialog()
                    Toast.makeText(this, "Customer Already added", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@RegistrationActivity, MainActivity::class.java))
                    finishAffinity()
                }
            }
        }
    }

    private fun clickListerners() {
        binding.ivCreate.setOnClickListener {
            userName = binding.edName.getText().toString()
            userEmail = binding.edNoEmail.getText().toString()
            userType = "Mobile"
            userPin = binding.edPassword.getText().toString()
            userConfirmPin = binding.edConfirmPassword.getText().toString()
            phoneNo = "03055178347"
            //  deviceToken = FirebaseInstanceId.getInstance().getToken()
            val strPass1: String = binding.edPassword.getText().toString()
            val strPass2: String = binding.edConfirmPassword.getText().toString()
            if (userName!!.isEmpty()) {
                binding.edName.setError("Name required")
            } else if (userEmail!!.isEmpty()) {
                binding.edNoEmail.setError("Name required")
            } else if (userPin!!.isEmpty()) {
                binding.edPassword.setError("Pin Required")
            } else if (userConfirmPin!!.isEmpty()) {
                binding.edConfirmPassword.setError("Confirm Pin Required")
            } else if (strPass1 != strPass2) {
                Toast.makeText(this@RegistrationActivity, "Pin not same!", Toast.LENGTH_SHORT)
                    .show()
            } else {
                registrationViewModel!!.calRegisterUser(
                    userName, userEmail, phoneNo, userType, userPin,
                    Uri.parse(imageLink)
                )
                Global.utils!!.showCustomLoadingDialog(this@RegistrationActivity)

            }
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
        }
        binding.btnAddPhoto.setOnClickListener {
            try {
                if (ContextCompat.checkSelfPermission(
                        applicationContext,
                        permission.READ_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(
                            arrayOf(
                                permission.WRITE_EXTERNAL_STORAGE,
                                permission.READ_EXTERNAL_STORAGE
                            ), WRITE_PERMISSION
                        )
                    }
                } else {
                    openGallery()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            //  openGallery()
        }
    }

    private fun initViews() {
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
//        prefs = getSharedPreferences("abc", Context.MODE_PRIVATE)
//        edit = prefs.edit()
        phoneNo = intent.getStringExtra("phoneNo")

        registrationViewModel = ViewModelProvider(this).get(RegistrationViewModel::class.java)
    }

    private fun openGallery() {
//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
//        intent.setType("image/*");
//
//        startActivityForResult(Intent.createChooser(intent, "Add profile picture"), GALLERY_REQUEST_CODE);
        fileType = "image"

        //open album

        //open album
        val config = GalleryConfig.Build()
            .singlePhoto(true)
            .build()
        GalleryActivity.openActivity(
            this@RegistrationActivity,
            GALLERY_REQUEST_CODE,
            config
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            val photos = data.getSerializableExtra(GalleryActivity.PHOTOS) as List<String>?
            if (photos != null) {
                for (i in photos.indices) {
                    Global.uriArrayList.add(Uri.fromFile(File(photos[i])))
                    uri = Uri.fromFile(File(photos[i]))
                    file = File(uri.getPath())
                    imageLink = file.toString()
                    Picasso.get().load(uri)
                        .into(binding.ivUserProfile)
                }
            }
            return
        }
//        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK) {
//
//            if (data.getData() != null) {
//                uri = data.getData();
//
//                Picasso.get().load(uri)
//                        .into(binding.ivUser);
////                uri = Uri.fromFile(new File(String.valueOf(data.getData())));
////                file = new File(uri.getPath());
////                uriFile = String.valueOf(file);
//            } else {
//                Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
//            }
//
//        }
    }
}