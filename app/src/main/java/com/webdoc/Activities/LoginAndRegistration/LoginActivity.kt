package com.webdoc.Activities.LoginAndRegistration

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.webdoc.Activities.LoginAndRegistration.ViewModels.LoginViewModel
import com.webdoc.Activities.MainActivity
import com.webdoc.Essentials.Global
import com.webdoc.Essentials.PreferencesNew
import com.webdoc.theforum.R
import com.webdoc.theforum.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private var phoneNO: String? = null
    private lateinit var prefs: SharedPreferences
    private lateinit var edit: SharedPreferences.Editor
    var loginViewModel: LoginViewModel? = null
    var deviceToken: String? = null


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


                val userName: String = response.result!!.customerDetails!!.name!!
                val userBal: String = response.result!!.customerDetails!!.balance!!
                val userMail: String = response.result!!.customerDetails!!.email!!
                val userNumber: String = response.result!!.customerDetails!!.mobileNumber!!
                val userPhoto: String = response.result!!.customerDetails!!.profilePictureUrl!!
                val userID: String = response.result!!.customerDetails!!.userId!!

                edit.putBoolean(PreferencesNew.KEY_IS_Register, true)
                edit.putString(PreferencesNew.KEY_ApplicationUserId, userID)
                edit.putString(PreferencesNew.KEY_USER_NAME, userName)
                edit.putString(PreferencesNew.KEY_USER_PHONE, userNumber)
                edit.putString(PreferencesNew.KEY_USER_EMAIL, userMail)
                edit.putString(PreferencesNew.KEY_USER_IMAGE, userPhoto)
                edit.putString(PreferencesNew.KEY_USER_Balance, userBal)
                edit.commit()
                edit.apply()
                if (response.result!!.responseCode.equals("0000")) {
                    edit.putBoolean(PreferencesNew.KEY_IS_LOGIN, true)
                    edit.commit()
                    edit.apply()

                    Global.utils!!.hideCustomLoadingDialog()
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
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
            deviceToken = "123456"
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
                Global.utils!!.showCustomLoadingDialog(this@LoginActivity)
                loginViewModel!!.calLogin(phoneNO, type, otp_entered, os, deviceToken)
            }
        }
        binding.tvDntAcc.setOnClickListener {
            val intent = Intent(this, AuthenticationActivity::class.java)
            startActivity(intent)
        }
        binding.tvGoogleSign.setOnClickListener {
            val intent = Intent(this, GetNumberActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initViews() {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

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
}