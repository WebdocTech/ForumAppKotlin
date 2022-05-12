package com.webdoc.Activities.LoginAndRegistration

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import com.webdoc.Essentials.Global
import com.webdoc.theforum.R
import com.webdoc.theforum.databinding.ActivityOtpverificationBinding
import java.util.concurrent.TimeUnit

class OTPVerificationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOtpverificationBinding
    private var otp_received: String? = null
    private var phoneNo: String? = null
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        clicklisteners()
        pinMovementListener()
    }

    private fun clicklisteners() {
        binding.btnVerifyOtp.setOnClickListener(View.OnClickListener {
            Global.utils!!.showCustomLoadingDialog(this@OTPVerificationActivity)
            val otp_entered = binding.etGetCodeOne.text.toString() +
                    binding.etGetCodeTwo.text.toString() +
                    binding.etGetCodeThree.text.toString() +
                    binding.etGetCodeFour.text.toString() +
                    binding.etGetCodeFive.text.toString() +
                    binding.etGetCodeSix.text.toString()
            if (otp_entered.isEmpty() || otp_entered.length < 6) {
                Global.utils!!.hideCustomLoadingDialog()
                Toast.makeText(
                    this@OTPVerificationActivity,
                    "Please enter code",
                    Toast.LENGTH_SHORT
                ).show()
                return@OnClickListener
            } else if (otp_received!!.isEmpty()) {
                Global.utils!!.hideCustomLoadingDialog()
                Toast.makeText(this@OTPVerificationActivity, "OTP not received", Toast.LENGTH_SHORT)
                    .show()
            } else {
                val credential = PhoneAuthProvider.getCredential(otp_received!!, otp_entered)
                signInWithCredential(credential)
            }
        })

        binding.btnResendCode.setOnClickListener {
            Global.utils!!.showCustomLoadingDialog(this@OTPVerificationActivity)
            sendVerificationCode(phoneNo.toString())
            Toast.makeText(
                this@OTPVerificationActivity,
                "Code will be send soon",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun initViews() {
        binding = ActivityOtpverificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        otp_received = intent.getStringExtra("optCode")
        phoneNo = intent.getStringExtra("phoneNo")

        firebaseAuth = FirebaseAuth.getInstance()

        binding.etGetCodeTwo.isFocusable = false
        binding.etGetCodeThree.isFocusable = false
        binding.etGetCodeFour.isFocusable = false
        binding.etGetCodeFive.isFocusable = false
        binding.etGetCodeSix.isFocusable = false

        deleteListener(binding.etGetCodeTwo)
        deleteListener(binding.etGetCodeThree)
        deleteListener(binding.etGetCodeFour)
        deleteListener(binding.etGetCodeFive)
        deleteListener(binding.etGetCodeSix)

        editTextTouchListener(binding.etGetCodeOne)
        editTextTouchListener(binding.etGetCodeTwo)
        editTextTouchListener(binding.etGetCodeThree)
        editTextTouchListener(binding.etGetCodeFour)
        editTextTouchListener(binding.etGetCodeFive)
        editTextTouchListener(binding.etGetCodeSix)

    }

    private fun editTextTouchListener(editText: EditText) {
        editText.setOnTouchListener { view, motionEvent ->
            when (editText.id) {
                R.id.et_getCodeOne -> if (!TextUtils.isEmpty(binding.etGetCodeOne.text.toString())) {
                    binding.etGetCodeOne.setText("")
                    binding.etGetCodeTwo.setText("")
                    binding.etGetCodeThree.setText("")
                    binding.etGetCodeFour.setText("")
                    binding.etGetCodeFive.setText("")
                    binding.etGetCodeSix.setText("")
                }
                R.id.et_getCodeTwo -> if (!TextUtils.isEmpty(binding.etGetCodeTwo.text.toString())) {
                    binding.etGetCodeTwo.setText("")
                    binding.etGetCodeThree.setText("")
                    binding.etGetCodeFour.setText("")
                    binding.etGetCodeFive.setText("")
                    binding.etGetCodeSix.setText("")
                }
                R.id.et_getCodeThree -> if (!TextUtils.isEmpty(binding.etGetCodeThree.text.toString())) {
                    binding.etGetCodeThree.setText("")
                    binding.etGetCodeFour.setText("")
                    binding.etGetCodeFive.setText("")
                    binding.etGetCodeSix.setText("")
                }
                R.id.et_getCodeFour -> if (!TextUtils.isEmpty(binding.etGetCodeFour.text.toString())) {
                    binding.etGetCodeFour.setText("")
                    binding.etGetCodeFive.setText("")
                    binding.etGetCodeSix.setText("")
                }
                R.id.et_getCodeFive -> if (!TextUtils.isEmpty(binding.etGetCodeFive.text.toString())) {
                    binding.etGetCodeFive.setText("")
                    binding.etGetCodeSix.setText("")
                }
                R.id.et_getCodeSix -> if (!TextUtils.isEmpty(binding.etGetCodeSix.text.toString())) {
                    binding.etGetCodeSix.setText("")
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
                    R.id.et_getCodeTwo -> if (binding.etGetCodeTwo.text.toString().isEmpty()) {
                        binding.etGetCodeOne.setText("")
                        binding.etGetCodeOne.requestFocus()
                    }
                    R.id.et_getCodeThree -> if (binding.etGetCodeThree.text.toString().isEmpty()) {
                        binding.etGetCodeTwo.isFocusableInTouchMode =
                            true /* enables edittext editing */
                        binding.etGetCodeTwo.setText("")
                        binding.etGetCodeTwo.requestFocus()
                    }
                    R.id.et_getCodeFour -> if (binding.etGetCodeFour.text.toString().isEmpty()) {
                        binding.etGetCodeThree.isFocusableInTouchMode =
                            true /* enables edittext editing */
                        binding.etGetCodeThree.setText("")
                        binding.etGetCodeThree.requestFocus()
                    }
                    R.id.et_getCodeFive -> if (binding.etGetCodeFive.text.toString().isEmpty()) {
                        binding.etGetCodeFour.isFocusableInTouchMode =
                            true /* enables edittext editing */
                        binding.etGetCodeFour.setText("")
                        binding.etGetCodeFour.requestFocus()
                    }
                    R.id.et_getCodeSix -> if (binding.etGetCodeSix.text.toString().isEmpty()) {
                        binding.etGetCodeFive.isFocusableInTouchMode =
                            true /* enables edittext editing */
                        binding.etGetCodeFive.setText("")
                        binding.etGetCodeFive.requestFocus()
                    }
                    else -> {
                    }
                }
            }
            false
        }
    } //delete


    fun pinMovementListener() {
        binding.etGetCodeOne.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (count == 0) {
                    /* disable all next edittexts */
                    binding.etGetCodeTwo.isFocusable = false
                } else {
                    if (s.length == 1) {
                        /* enables next edittext */
                        binding.etGetCodeTwo.isFocusableInTouchMode = true
                        /* move focus to next edittext */binding.etGetCodeTwo.requestFocus(
                            View.FOCUS_DOWN
                        )
                    }
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })
        binding.etGetCodeTwo.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (count == 0) {
                    /* disable all next edittexts */
                    binding.etGetCodeThree.isFocusable = false
                } else {
                    if (s.length == 1) {
                        /* enables next edittext */
                        binding.etGetCodeThree.isFocusableInTouchMode = true

                        /* move focus to next edittext */binding.etGetCodeThree.requestFocus(View.FOCUS_DOWN)
                    }
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })
        binding.etGetCodeThree.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (count == 0) {
                    /* disable all next edittexts */
                    binding.etGetCodeFour.isFocusable = false
                } else {
                    if (s.length == 1) {
                        /* enables next edittext */
                        binding.etGetCodeFour.isFocusableInTouchMode = true

                        /* move focus to next edittext */binding.etGetCodeFour.requestFocus(View.FOCUS_DOWN)
                    }
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })
        binding.etGetCodeFour.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (count == 0) {
                    /* disable all next edittexts */
                    binding.etGetCodeFive.isFocusable = false
                } else {
                    if (s.length == 1) {
                        /* enables next edittext */
                        binding.etGetCodeFive.isFocusableInTouchMode = true

                        /* move focus to next edittext */binding.etGetCodeFive.requestFocus(View.FOCUS_DOWN)
                    }
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })
        binding.etGetCodeFive.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (count == 0) {
                    /* disable all next edittexts */
                    binding.etGetCodeSix.isFocusable = false
                } else {
                    if (s.length == 1) {
                        /* enables next edittext */
                        binding.etGetCodeSix.isFocusableInTouchMode = true

                        /* move focus to next edittext */binding.etGetCodeSix.requestFocus(View.FOCUS_DOWN)
                    }
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })
    }

    private fun verifyCode(code: String) {
        val credential = PhoneAuthProvider.getCredential(otp_received!!, code)
        signInWithCredential(credential)
    }

    private fun sendVerificationCode(number: String) {
        val options = PhoneAuthOptions.newBuilder(firebaseAuth!!)
            .setPhoneNumber(number) // Phone number to verify
            .setTimeout(100L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this) // Activity (for callback binding)
            .setCallbacks(mCallBack) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    // callback method is called on Phone auth provider.
    private val mCallBack: OnVerificationStateChangedCallbacks =
        object : OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                val code = phoneAuthCredential.smsCode
                if (code != null) {
                    verifyCode(code)
                }
            }

            override fun onVerificationFailed(e: FirebaseException) {
                // displaying error message with firebase exception.
                Global.utils!!.hideCustomLoadingDialog()
                Toast.makeText(this@OTPVerificationActivity, e.message, Toast.LENGTH_LONG).show()
            }

            override fun onCodeSent(code: String, forceResendingToken: ForceResendingToken) {
                super.onCodeSent(code, forceResendingToken)
                otp_received = code
                Global.utils!!.hideCustomLoadingDialog()
                Toast.makeText(
                    this@OTPVerificationActivity,
                    "OTP sent successfully",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    private fun signInWithCredential(credential: PhoneAuthCredential) {
        firebaseAuth!!.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Global.utils!!.hideCustomLoadingDialog()
                    Toast.makeText(
                        this@OTPVerificationActivity,
                        "Verification Successful..",
                        Toast.LENGTH_SHORT
                    ).show()

                    val intent =
                        Intent(this@OTPVerificationActivity, RegistrationActivity::class.java)
                    intent.putExtra("phoneNo", phoneNo)
                    startActivity(intent)
                    finishAffinity()

                } else {
                    Global.utils!!.hideCustomLoadingDialog()
                    Toast.makeText(
                        this@OTPVerificationActivity,
                        task.exception!!.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }


}