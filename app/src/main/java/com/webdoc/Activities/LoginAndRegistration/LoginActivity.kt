package com.webdoc.Activities.LoginAndRegistration

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
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
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.webdoc.Activities.LoginAndRegistration.ViewModels.LoginViewModel
import com.webdoc.Activities.LoginAndRegistration.ViewModels.RegistrationViewModel
import com.webdoc.Activities.MainActivity
import com.webdoc.Essentials.Global
import com.webdoc.Essentials.PreferencesNew
import com.webdoc.ModelClasses.UserModel
import com.webdoc.theforum.R
import com.webdoc.theforum.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var firebaseAuth: FirebaseAuth
    private var uid: String = ""
    private var email: String = ""
    private var name: String = ""
    private var profileImage: Uri? = null
    private var storageReference: StorageReference? = null
    private var firebaseStorage: FirebaseStorage? = null
    private var databaseReference: DatabaseReference? = null
    private lateinit var prefs: SharedPreferences
    private lateinit var edit: SharedPreferences.Editor
    var loginViewModel: LoginViewModel? = null
    private var registrationViewModel: RegistrationViewModel? = null

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
        loginViewModel!!.LDLogin().observe(this) { response ->
            if (response != null) {
//                val name: String = response.getResult().getCustomerDetails().getName()
//                val adress: String = response.getResult().getCustomerDetails().getAddress()
//                val city: String = response.getResult().getCustomerDetails().getCity()
//                val userId: String = response.getResult().getCustomerDetails().getUserId()
//                Preferences.getInstance(applicationContext).setKeyUserName(name)
//                Preferences.getInstance(applicationContext).setKEY_USER_Address(adress)
//                Preferences.getInstance(applicationContext).setKEY_USER_City(city)
//                Preferences.getInstance(applicationContext).setKEY_ApplicationUserId(userId)
                if (response.result!!.responseCode.equals("0000")) {

//                    if (Preferences.getInstance(getApplicationContext()).getKeyIsRegister()) {
                    //   Preferences.getInstance(applicationContext).setKeyIsLogin(true)
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                    finishAffinity()

                    //                  }
//                    Intent intent = new Intent(RegistrationActivity.this, DashboardActivity.class);
////                    intent.putExtra("optCode", code);
////                    intent.putExtra("phoneNo", phoneNo);
//
//                    startActivity(intent);
//                    finishAffinity();
                } else {
                    Toast.makeText(this, "Pin Incorrect or number", Toast.LENGTH_SHORT).show()
                    Global.utils!!.hideCustomLoadingDialog()
                    //  Preferences.getInstance(getApplicationContext()).setKeyIsRegister(true);
                }
            }
        }

        registrationViewModel!!.LDRegistration().observe(this) { response ->
            if (response != null) {
                if (response.result!!.responseCode.equals("0000")) {
                    Toast.makeText(this@LoginActivity, "Customer Added", Toast.LENGTH_SHORT)
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
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    finishAffinity()
                } else {
//                    Preferences.getInstance(applicationContext).setKeyIsLogin(true)
//                    //  Preferences.getInstance(getApplicationContext()).setKeyIsRegister(true);
//                    Preferences.getInstance(applicationContext).setKeyUserName(name)
                    Global.utils!!.hideCustomLoadingDialog()
                    Toast.makeText(this, "Customer Already added", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    finishAffinity()
                }
            }
        }
    }

    private fun clickListerners() {
        binding.ivSignIn.setOnClickListener {
            val phoneNO: String = binding.edUserNumber.getText().toString()
            val os = "Android"
            val type = "Mobile"
            val deviceToken: String = "123456"
            val otp_entered = binding.etGetCode1.text.toString() +
                    binding.etGetCode2.text.toString() +
                    binding.etGetCode3.text.toString() +
                    binding.etGetCode4.text.toString()

            if (phoneNO!!.isEmpty()) {
                binding.edUserNumber.setError("number required")
            } else if (otp_entered.isEmpty() || otp_entered.length < 4) {
                Toast.makeText(
                    this@LoginActivity,
                    "Please enter code",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            } else {
                Global.utils!!.showCustomLoadingDialog(this@LoginActivity)
                loginViewModel!!.calLogin(phoneNO, type, otp_entered, os, deviceToken)
                //    Preferences.getInstance(applicationContext).setKeyUserPhone(phoneNo)
            }
//            if (TextUtils.isEmpty(pin)) {
//                binding.edtPassword.setError("Pin Required")
//                binding.edtPassword.requestFocus()
//                binding.btnLogin.setEnabled(true)
//                return@setOnClickListener
//            } else {
//
//            }
        }
        binding.tvDntAcc.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }
        binding.tvGoogleSign.setOnClickListener {
            signIn();
            val type = "Mobile"
            val pin = "forum@1234"
//            registrationViewModel!!.calRegisterUser(
//                name, email, phoneNo, type, pin,
//                Uri.parse(profileImage.toString())
//            )
            Global.utils!!.showCustomLoadingDialog(this@LoginActivity)
        }
    }

    private fun initViews() {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        val gso: GoogleSignInOptions =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .requestProfile()
                .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseStorage = FirebaseStorage.getInstance()
        databaseReference = FirebaseDatabase.getInstance().getReference("user")
        //firebaseAuth = Firebase.auth
        prefs = getSharedPreferences("abc", Context.MODE_PRIVATE)
        edit = prefs?.edit()
        //  checkUser()

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

        registrationViewModel = ViewModelProvider(this).get(RegistrationViewModel::class.java)
    }

    private fun updateUI(account: GoogleSignInAccount?) {
        //   Log.d(LoginOptionsActivity.TAG, "firebaseAuthwithGoogleAccount: begin firebase auth with google account")

        val credential = GoogleAuthProvider.getCredential(account!!.idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnSuccessListener { authResult ->
                //   Log.d(LoginActivity.TAG, "updateUI: LoggedIn")

                val fireBaseUser = firebaseAuth.currentUser
                uid = fireBaseUser!!.uid
                email = fireBaseUser.email!!
                name = fireBaseUser.displayName!!
                profileImage = fireBaseUser.photoUrl!!
//                Log.d(TAG, "updateUI: Uid: $uid")
//                Log.d(TAG, "updateUI: Email: $email")
//                Log.d(TAG, "updateUI: Name: $name")

                if (authResult.additionalUserInfo!!.isNewUser) {
                    //   Log.d(TAG, "updateUI: Account created... \n$name")
                    Toast.makeText(
                        this@LoginActivity, "Account created... \n$name",
                        Toast.LENGTH_SHORT
                    ).show();

                    uploadData()

                    edit.putString(PreferencesNew.KEY_ApplicationUserId, uid)
                    edit.commit()
                    edit.apply()

                    val intent = Intent(this, GetNumberActivity::class.java)
                    startActivity(intent)
                    Global.utils!!.hideCustomLoadingDialog()
                } else {
                    // Log.d(LoginOptionsActivity.TAG, "updateUI: Existing User... \n$name")


                    Toast.makeText(
                        this@LoginActivity, "Logged in... \n$name",
                        Toast.LENGTH_SHORT
                    ).show();
                    //    Preferences.getInstance(applicationContext).setKEY_ApplicationUserId(uid)

                    //  uploadDatawithImage()

                    edit.putString(PreferencesNew.KEY_ApplicationUserId, uid)
                    edit.commit()
                    edit.apply()

                    val intent = Intent(this, GetNumberActivity::class.java)
                    startActivity(intent)
                    Global.utils!!.hideCustomLoadingDialog()
                }


            }
            .addOnFailureListener { e ->
                //  Log.d(LoginOptionsActivity.TAG, "updateUI: Logged Failed due to ${e.message}")
                Toast.makeText(
                    this@LoginActivity, "Logged Failed due to ${e.message}",
                    Toast.LENGTH_SHORT
                ).show();
            }
    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                updateUI(account)
            } catch (e: Exception) {
                //  Log.d(LoginOptionsActivity.TAG, "OnActivityResult: ${e.message}")
            }
            //  handleSignInResult(task)
        }
    }

    private fun checkUser() {
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun uploadData() {
        val userModel = UserModel(email, uid, name)

        databaseReference = FirebaseDatabase.getInstance().getReference("user").child(uid)

        databaseReference!!.setValue(userModel).addOnSuccessListener {
            Toast.makeText(
                this@LoginActivity,
                "dataAddedSuccesfully",
                Toast.LENGTH_SHORT
            ).show()

        }
            .addOnFailureListener { e ->
                Log.i(
                    "sdfdf",
                    e.message!!
                )
            }
    }

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
}