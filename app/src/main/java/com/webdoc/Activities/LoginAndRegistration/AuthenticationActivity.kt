package com.webdoc.Activities.LoginAndRegistration

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import com.webdoc.Essentials.Global
import com.webdoc.theforum.databinding.ActivityAuthenticationBinding
import java.util.concurrent.TimeUnit

class AuthenticationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthenticationBinding
    private lateinit var mAuth: FirebaseAuth
    var phoneNo: String? = null
    var otp_received: String? = null
    private  var firebaseAuth: FirebaseAuth?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        clicklisteners()
    }

    private fun clicklisteners() {
        binding.ivSignIn.setOnClickListener {


//                binding.btnNext.setEnabled(false);
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

                Global.utils!!.showCustomLoadingDialog(this@AuthenticationActivity)
                phoneNo = binding.ccp.fullNumberWithPlus.toString() + number
                sendVerificationCode(phoneNo!!);
            }
        }
    }

    private fun initViews() {
        binding = ActivityAuthenticationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mAuth = FirebaseAuth.getInstance()
    }

    private fun sendVerificationCode(number: String) {
        val options = PhoneAuthOptions.newBuilder(mAuth!!)
            .setPhoneNumber(number) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this) // Activity (for callback binding)
            .setCallbacks(mCallBack) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun signInWithCredential(credential: PhoneAuthCredential) {
        firebaseAuth!!.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Global.utils!!.hideCustomLoadingDialog()
                    Toast.makeText(
                        this@AuthenticationActivity,
                        "Verification Successful..",
                        Toast.LENGTH_SHORT)
//                                startActivity(
//                            Intent(
//                                this@AuthenticationActivity,
//                               LoginActivity::class.java
//                           )
//                       )
//                                                    Intent intent = new Intent(OTPVerificationActivity.this, GetUserDataActivity.class);
//                                intent.putExtra("phoneNo", phoneNo);
//                               startActivity(intent);
//                       finishAffinity()
//                    ).show()
//                    if (Preferences.getInstance(applicationContext).getKeyIsRegister()) {
//                        Preferences.getInstance(applicationContext).setKeyIsLogin(true)
//                        startActivity(
//                            Intent(
//                                this@AuthenticationActivity,
//                                LoginActivity::class.java
//                            )
//                        )
//                        //                                Intent intent = new Intent(OTPVerificationActivity.this, GetUserDataActivity.class);
////                                intent.putExtra("phoneNo", phoneNo);
////                                startActivity(intent);
//                        finishAffinity()
//                    } else {
//                        val intent =
//                            Intent(this@AuthenticationActivity, LoginOptionsActivity::class.java)
//                        intent.putExtra("phoneNo", phoneNo)
//                        startActivity(intent)
//                        finish()
//                    }
                } else {
                    Global.utils!!.hideCustomLoadingDialog()
                    Toast.makeText(
                        this@AuthenticationActivity,
                        task.exception!!.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }

    private fun verifyCode(code: String) {
        val credential = PhoneAuthProvider.getCredential(otp_received!!, code)
        signInWithCredential(credential)
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
                Toast.makeText(this@AuthenticationActivity, e.message, Toast.LENGTH_LONG).show()
            }

            override fun onCodeSent(code: String, forceResendingToken: ForceResendingToken) {
                Global.utils!!.hideCustomLoadingDialog()
                otp_received = code
                val intent =
                    Intent(this@AuthenticationActivity, OTPVerificationActivity::class.java)
                intent.putExtra("optCode", code)
                intent.putExtra("phoneNo", phoneNo)
                startActivity(intent)
            }
        }
}