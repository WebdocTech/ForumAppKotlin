package com.webdoc.Activities.LoginAndRegistration

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.GoogleAuthUtil.getToken
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.zzl.getToken
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.installations.FirebaseInstallations
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.storage.FirebaseStorage
import com.webdoc.Activities.LoginAndRegistration.ViewModels.LoginViewModel
import com.webdoc.Activities.LoginAndRegistration.ViewModels.RegistrationViewModel
import com.webdoc.Activities.MainActivity
import com.webdoc.Essentials.Global
import com.webdoc.Essentials.PreferencesNew
import com.webdoc.theforum.R
import com.webdoc.theforum.databinding.ActivityLoginBinding
import java.io.File

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private var phoneNO: String? = null
    private lateinit var prefs: SharedPreferences
    private lateinit var edit: SharedPreferences.Editor
    var loginViewModel: LoginViewModel? = null
    var deviceToken: String = ""

    private var phoneNo: String? = null
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var firebaseAuth: FirebaseAuth
    private var uid: String = ""
    private var email: String = ""
    private var name: String = ""
    private var profileImage: Uri? = null
    var file: File? = null
    private lateinit var userPhoto: String
    var imageLink: String? = null
    private var databaseReference: DatabaseReference? = null
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
        pinMovementListener()
    }

    private fun observers() {
        registrationViewModel!!.LDRegistration().observe(this) { response ->
            if (response != null) {
                if (response.result!!.responseCode.equals("0000")) {
                    Toast.makeText(this@LoginActivity, "Customer Added", Toast.LENGTH_SHORT)
                        .show()

                    edit.putString(PreferencesNew.KEY_ApplicationUserId, uid)
                    edit.putString(PreferencesNew.KEY_USER_NAME, name)
                    edit.putString(PreferencesNew.KEY_USER_PHONE, phoneNo)
                    edit.putString(PreferencesNew.KEY_USER_EMAIL, email)
                    edit.putString(PreferencesNew.KEY_USER_IMAGE, profileImage.toString())
                    edit.commit()
                    edit.apply()

                    Global.utils!!.hideCustomLoadingDialog()
                    startActivity(Intent(this@LoginActivity, GetNumberActivity::class.java))
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
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    finishAffinity()
                }
            }
        }
        loginViewModel!!.LDLogin().observe(this) { response ->
            if (response != null) {


                val userName: String = response.result!!.customerDetails!!.name!!
                val userBal: String = response.result!!.customerDetails!!.balance!!
                val userMail: String = response.result!!.customerDetails!!.email!!
                val userNumber: String = response.result!!.customerDetails!!.mobileNumber!!
                if (response.result!!.customerDetails!!.profilePictureUrl != null) {
                  userPhoto  = response.result!!.customerDetails!!.profilePictureUrl!!
                    edit.putString(PreferencesNew.KEY_USER_IMAGE, userPhoto)
                }

                val userID: String = response.result!!.customerDetails!!.userId!!
                edit.putBoolean(PreferencesNew.KEY_IS_Register, true)
                edit.putString(PreferencesNew.KEY_ApplicationUserId, userID)
                edit.putString(PreferencesNew.KEY_USER_NAME, userName)
                edit.putString(PreferencesNew.KEY_USER_PHONE, userNumber)
                edit.putString(PreferencesNew.KEY_USER_EMAIL, userMail)




                edit.putString(PreferencesNew.KEY_USER_Balance, userBal)
                edit.commit()
                edit.apply()
                if (response.result!!.responseCode.equals("0000")) {
                    edit.putBoolean(PreferencesNew.KEY_IS_LOGIN, true)
                    edit.commit()
                    edit.apply()

                    Global.utils!!.hideCustomLoadingDialog()
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    Toast.makeText(this, "Welcome\t" + userName, Toast.LENGTH_SHORT).show()
                    finishAffinity()

                } else {
                    onSNACK(binding.root)
                    Global.utils!!.hideCustomLoadingDialog()
                }
            }
        }

    }

    fun onSNACK(view: View) {
        //Snackbar(view)
        val snackbar = Snackbar.make(
            view, "Pin Or Number Incorrect!!",
            Snackbar.LENGTH_LONG
        ).setAction("Action", null)
        snackbar.setActionTextColor(Color.BLACK)
        val snackbarView = snackbar.view
        snackbarView.setBackgroundColor(Color.WHITE)
        val textView =
            snackbarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        textView.setTextColor(Color.RED)
        textView.textSize = 20f
        textView.setGravity(Gravity.CENTER)
        snackbar.show()
    }

    private fun clickListerners() {
        binding.ivSignIn.setOnClickListener {
            phoneNO = binding.edUserNumber.getText().toString()
            val os = "Android"
            val type = "Mobile"


            // deviceToken = "123456"
            val otp_entered = binding.etGetCode1.text.toString() +
                    binding.etGetCode2.text.toString() +
                    binding.etGetCode3.text.toString() +
                    binding.etGetCode4.text.toString()
            val number = binding.edUserNumber.text.toString()

            if (TextUtils.isEmpty(number)) {
                binding.edUserNumber.error = "Enter your mobile number"
                binding.edUserNumber.requestFocus()
                binding.ivSignIn.setEnabled(true)
                return@setOnClickListener
            } else if (number.length < 10) {
                binding.edUserNumber.error = "Enter a valid mobile number"
                binding.edUserNumber.requestFocus()
                binding.ivSignIn.setEnabled(true)
                return@setOnClickListener
            } else if (otp_entered.isEmpty() || otp_entered.length < 4) {
                Toast.makeText(
                    this@LoginActivity,
                    "Please enter code",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            } else {
                phoneNO = binding.codeCcp.fullNumberWithPlus.toString() + number
                if (isOnline(this@LoginActivity)) {
                    Global.utils!!.showCustomLoadingDialog(this@LoginActivity)
                    loginViewModel!!.calLogin(phoneNO, type, otp_entered, os, deviceToken)
                } else {
                    Toast.makeText(
                        this@LoginActivity,
                        "Please Check Internet",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }
        }
        binding.tvDntAcc.setOnClickListener {
            val intent = Intent(this, AuthenticationActivity::class.java)
            startActivity(intent)
        }
        binding.tvGoogleSign.setOnClickListener {
//            val intent = Intent(this, GetNumberActivity::class.java)
//            startActivity(intent)
            if (isOnline(this@LoginActivity)) {
                signIn()
                Global.utils!!.showCustomLoadingDialog(this@LoginActivity)
            } else {
                Toast.makeText(
                    this@LoginActivity, "Check Internet",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)

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
                        this@LoginActivity, "Account created... \n$name",
                        Toast.LENGTH_SHORT
                    ).show();

                    val type = "Email"
                    val pin = "forum@1234"
//                    registrationViewModel!!.calRegisterUserGoogle(
//                        name, email, phoneNo, type, pin,
//                        profileImage!!.toString()
//                    )
                    edit.putString(PreferencesNew.KEY_ApplicationUserId, uid)
                    edit.putString(PreferencesNew.KEY_USER_NAME, name)
                    //  edit.putString(PreferencesNew.KEY_USER_PHONE, phoneNo)
                    edit.putString(PreferencesNew.KEY_USER_EMAIL, email)
                    edit.putString(PreferencesNew.KEY_USER_IMAGE, profileImage.toString())
                    edit.commit()
                    edit.apply()
                    Global.utils!!.hideCustomLoadingDialog()
                    startActivity(Intent(this@LoginActivity, GetNumberActivity::class.java))
                    finishAffinity()
                } else {

                    Toast.makeText(
                        this@LoginActivity, "Logged in... \n$name",
                        Toast.LENGTH_SHORT
                    ).show();
                    val os = "Android"
                    val type = "Email"
                    val pin = "forum@1234"
                    loginViewModel!!.calLogin(email, type, pin, os, deviceToken)
                }


            }
            .addOnFailureListener { e ->

                Toast.makeText(
                    this@LoginActivity, "Logged Failed due to ${e.message}",
                    Toast.LENGTH_SHORT
                ).show();
            }
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

    private fun initViews() {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val gso: GoogleSignInOptions =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .requestProfile()
                .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        firebaseAuth = FirebaseAuth.getInstance()
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        registrationViewModel = ViewModelProvider(this).get(RegistrationViewModel::class.java)
        firebaseStorage = FirebaseStorage.getInstance()
        databaseReference = FirebaseDatabase.getInstance().getReference("user")
        prefs = getSharedPreferences("abc", Context.MODE_PRIVATE)
        edit = prefs?.edit()

        binding.etGetCode2.setFocusable(false)
        binding.etGetCode3.setFocusable(false)
        binding.etGetCode4.setFocusable(false)

        deleteListener(binding.etGetCode2)
        deleteListener(binding.etGetCode3)
        deleteListener(binding.etGetCode4)

        editTextTouchListener(binding.etGetCode1)
        editTextTouchListener(binding.etGetCode2)
        editTextTouchListener(binding.etGetCode3)
        editTextTouchListener(binding.etGetCode4)
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                deviceToken = task.result
            } else {
                Toast.makeText(this, "Token Failed", Toast.LENGTH_SHORT).show()
            }
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

    fun editTextTouchListener(editText: EditText) {
        editText.setOnTouchListener { view, motionEvent ->
            when (editText.id) {
                R.id.et_getCode1 -> if (!TextUtils.isEmpty(binding.etGetCode1.text.toString())) {
                    binding.etGetCode1.setText("")
                    binding.etGetCode2.setText("")
                    binding.etGetCode3.setText("")
                    binding.etGetCode4.setText("")
                }
                R.id.et_getCode2 -> if (!TextUtils.isEmpty(binding.etGetCode2.text.toString())) {
                    binding.etGetCode2.setText("")
                    binding.etGetCode3.setText("")
                    binding.etGetCode4.setText("")
                }
                R.id.et_getCode3 -> if (!TextUtils.isEmpty(binding.etGetCode3.text.toString())) {
                    binding.etGetCode3.setText("")
                    binding.etGetCode4.setText("")
                }
                R.id.et_getCode4 -> if (!TextUtils.isEmpty(binding.etGetCode4.text.toString())) {
                    binding.etGetCode4.setText("")
                }

                else -> {
                }
            }
            false /* keyboard pops up on false */
        }
    }

    fun deleteListener(editText: EditText) {
        editText.setOnKeyListener { v, keyCode, event -> //You can identify which key pressed by checking keyCode value with KeyEvent.KEYCODE_
            if (event.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_DEL) {
                //this is for backspace
                when (editText.id) {
                    R.id.et_getCode2 -> if (binding.etGetCode2.text.toString().isEmpty()) {
                        binding.etGetCode1.setText("")
                        binding.etGetCode1.requestFocus()
                    }
                    R.id.et_getCode3 -> if (binding.etGetCode3.text.toString().isEmpty()) {
                        binding.etGetCode2.isFocusableInTouchMode =
                            true /* enables edittext editing */
                        binding.etGetCode2.setText("")
                        binding.etGetCode2.requestFocus()
                    }
                    R.id.et_getCode4 -> if (binding.etGetCode4.text.toString().isEmpty()) {
                        binding.etGetCode3.isFocusableInTouchMode =
                            true /* enables edittext editing */
                        binding.etGetCode3.setText("")
                        binding.etGetCode3.requestFocus()
                    }
                    else -> {
                    }
                }
            }
            false
        }
    } //delete


    fun pinMovementListener() {
        binding.etGetCode1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (count == 0) {
                    /* disable all next edittexts */
                    binding.etGetCode2.isFocusable = false
                } else {
                    if (s.length == 1) {
                        /* enables next edittext */
                        binding.etGetCode2.isFocusableInTouchMode = true
                        /* move focus to next edittext */binding.etGetCode2.requestFocus(
                            View.FOCUS_DOWN
                        )
                    }
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })
        binding.etGetCode2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (count == 0) {
                    /* disable all next edittexts */
                    binding.etGetCode3.isFocusable = false
                } else {
                    if (s.length == 1) {
                        /* enables next edittext */
                        binding.etGetCode3.isFocusableInTouchMode = true

                        /* move focus to next edittext */binding.etGetCode3.requestFocus(View.FOCUS_DOWN)
                    }
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })
        binding.etGetCode3.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (count == 0) {
                    /* disable all next edittexts */
                    binding.etGetCode4.isFocusable = false
                } else {
                    if (s.length == 1) {
                        /* enables next edittext */
                        binding.etGetCode4.isFocusableInTouchMode = true

                        /* move focus to next edittext */binding.etGetCode4.requestFocus(View.FOCUS_DOWN)
                    }
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })

    }

    private fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                } else {
                    TODO("VERSION.SDK_INT < M")
                }
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }
}