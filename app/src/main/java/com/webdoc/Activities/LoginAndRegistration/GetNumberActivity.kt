package com.webdoc.Activities.LoginAndRegistration

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.webdoc.Activities.LoginAndRegistration.ViewModels.RegistrationViewModel
import com.webdoc.Activities.MainActivity
import com.webdoc.Essentials.Global
import com.webdoc.Essentials.PreferencesNew
import com.webdoc.theforum.R
import com.webdoc.theforum.databinding.ActivityGetNumberBinding
import java.io.File

class GetNumberActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGetNumberBinding
    private var phoneNo: String? = null
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var firebaseAuth: FirebaseAuth
    private var uid: String = ""
    private var email: String = ""
    private var name: String = ""
    private var profileImage: Uri? = null
    var file: File? = null
    var imageLink: String? = null
    private var databaseReference: DatabaseReference? = null
    private lateinit var prefs: SharedPreferences
    private lateinit var edit: SharedPreferences.Editor
    private var registrationViewModel: RegistrationViewModel? = null
    private var firebaseStorage: FirebaseStorage? = null

    private companion object {
        private const val RC_SIGN_IN = 100
        private const val TAG = "GOOGLE_SIGN_IN_TAG"
    }

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
                    Toast.makeText(this@GetNumberActivity, "Customer Added", Toast.LENGTH_SHORT)
                        .show()

                    edit.putString(PreferencesNew.KEY_ApplicationUserId, uid)
                    edit.putString(PreferencesNew.KEY_USER_NAME, name)
                    edit.putString(PreferencesNew.KEY_USER_PHONE, phoneNo)
                    edit.putString(PreferencesNew.KEY_USER_EMAIL, email)
                    edit.putString(PreferencesNew.KEY_USER_IMAGE, profileImage.toString())
                    edit.commit()
                    edit.apply()

                    Global.utils!!.hideCustomLoadingDialog()
                    startActivity(Intent(this@GetNumberActivity, MainActivity::class.java))
                    finishAffinity()
                } else {
                    edit.putString(PreferencesNew.KEY_ApplicationUserId, uid)
                    edit.putString(PreferencesNew.KEY_USER_NAME, name)
                    edit.putString(PreferencesNew.KEY_USER_PHONE, phoneNo)
                    edit.putString(PreferencesNew.KEY_USER_EMAIL, email)
                    edit.putString(PreferencesNew.KEY_USER_IMAGE, profileImage.toString())
                    edit.putBoolean(PreferencesNew.KEY_IS_LOGIN, true)
                    edit.commit()
                    edit.apply()
                    Global.utils!!.hideCustomLoadingDialog()
                    Toast.makeText(this, "Welcome Back\t" + "\t" + name, Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@GetNumberActivity, MainActivity::class.java))
                    finishAffinity()
                }
            }
        }
    }

    private fun clickListerners() {
        binding.ivSignIn.setOnClickListener {
            val number = binding.edtMobileNo.text.toString()

            if (TextUtils.isEmpty(number)) {
                binding.edtMobileNo.error = "Enter your mobile number"
                binding.edtMobileNo.requestFocus()
                binding.ivSignIn.setEnabled(true)
                return@setOnClickListener
            } else if (number.length < 10) {
                binding.edtMobileNo.error = "Enter a valid mobile number"
                binding.edtMobileNo.requestFocus()
                binding.ivSignIn.setEnabled(true)
                return@setOnClickListener
            } else {

                phoneNo = binding.ccp.fullNumberWithPlus.toString() + number

                signIn()
                Global.utils!!.showCustomLoadingDialog(this@GetNumberActivity)

            }
        }

    }

    private fun initViews() {
        binding = ActivityGetNumberBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val gso: GoogleSignInOptions =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .requestProfile()
                .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        firebaseAuth = FirebaseAuth.getInstance()
        registrationViewModel = ViewModelProvider(this).get(RegistrationViewModel::class.java)
        firebaseStorage = FirebaseStorage.getInstance()
        databaseReference = FirebaseDatabase.getInstance().getReference("user")
        prefs = getSharedPreferences("abc", Context.MODE_PRIVATE)
        edit = prefs?.edit()
    }

    private fun updateUI(account: GoogleSignInAccount?) {

        val credential = GoogleAuthProvider.getCredential(account!!.idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnSuccessListener { authResult ->

                val fireBaseUser = firebaseAuth.currentUser
                uid = fireBaseUser!!.uid
                email = fireBaseUser.email!!
                name = fireBaseUser.displayName!!
                profileImage = fireBaseUser.photoUrl!!
                file = File(profileImage!!.getPath())
                imageLink = file.toString()

                if (authResult.additionalUserInfo!!.isNewUser) {

                    Toast.makeText(
                        this@GetNumberActivity, "Account created... \n$name",
                        Toast.LENGTH_SHORT
                    ).show();

                    val type = "Email"
                    val pin = "forum@1234"
                    registrationViewModel!!.calRegisterUserGoogle(
                        name, email, phoneNo, type, pin,
                        profileImage!!.toString()
                    )

                } else {

                    Toast.makeText(
                        this@GetNumberActivity, "Logged in... \n$name",
                        Toast.LENGTH_SHORT
                    ).show();
                    val type = "Email"
                    val pin = "forum@1234"
                    registrationViewModel!!.calRegisterUserGoogle(
                        name, email, phoneNo, type, pin,
                        profileImage!!.toString()
                    )
                }


            }
            .addOnFailureListener { e ->

                Toast.makeText(
                    this@GetNumberActivity, "Logged Failed due to ${e.message}",
                    Toast.LENGTH_SHORT
                ).show();
            }
    }

    //    private fun uploadData() {
//        val userModel = UserModel(email, uid, name)
//
//        databaseReference = FirebaseDatabase.getInstance().getReference("user").child(uid)
//
//        databaseReference!!.setValue(userModel).addOnSuccessListener {
//            Toast.makeText(
//                this@LoginActivity,
//                "dataAddedSuccesfully",
//                Toast.LENGTH_SHORT
//            ).show()
//
//        }
//            .addOnFailureListener { e ->
//                Log.i(
//                    "sdfdf",
//                    e.message!!
//                )
//            }
//    }
    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {

            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                updateUI(account)
            } catch (e: Exception) {
                Log.i("dsd", e.toString())
            }
        }
    }
}