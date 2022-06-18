package com.webdoc.Activities.LoginAndRegistration

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
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
    var mVerificationId: String? = null

    // private var firebaseAuth: FirebaseAuth? = null
    // private var mCallbacks: OnVerificationStateChangedCallbacks? = null
    private var forceResendingToken: ForceResendingToken? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        initViews()

        clicklisteners()
    }

    private fun verifyPhoneNumberWithCode(mVerificationId: String, code: String) {
        Global.utils!!.showCustomLoadingDialog(this@AuthenticationActivity)
        val credential = PhoneAuthProvider.getCredential(mVerificationId, code)
        signInWithPhoneAuthCredential(credential)
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
                if (isOnline(this@AuthenticationActivity)) {
                    Global.utils!!.showCustomLoadingDialog(this@AuthenticationActivity)
                    phoneNo = binding.ccp.fullNumberWithPlus.toString() + number
                    startPhoneNumberVerification(phoneNo!!);
                }
            }
        }
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

    private fun initViews() {
        binding = ActivityAuthenticationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mAuth = FirebaseAuth.getInstance()
    }

    private fun startPhoneNumberVerification(phone: String) {
        //Global.utils!!.showCustomLoadingDialog(this@AuthenticationActivity)
        val options = PhoneAuthOptions.newBuilder(mAuth)
            .setPhoneNumber(phone)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this@AuthenticationActivity)
            .setCallbacks(mCallbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }
//    private fun sendVerificationCode(number: String) {
//        val options = PhoneAuthOptions.newBuilder(mAuth!!)
//            .setPhoneNumber(number) // Phone number to verify
//            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
//            .setActivity(this) // Activity (for callback binding)
//            .setCallbacks(mCallBack) // OnVerificationStateChangedCallbacks
//            .build()
//        PhoneAuthProvider.verifyPhoneNumber(options)
//    }

//    private fun signInWithCredential(credential: PhoneAuthCredential) {
//        firebaseAuth!!.signInWithCredential(credential)
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    Global.utils!!.hideCustomLoadingDialog()
//                    Toast.makeText(
//                        this@AuthenticationActivity,
//                        "Verification Successful..",
//                        Toast.LENGTH_SHORT
//                    )
//                } else {
//                    Global.utils!!.hideCustomLoadingDialog()
//                    Toast.makeText(
//                        this@AuthenticationActivity,
//                        task.exception!!.message,
//                        Toast.LENGTH_LONG
//                    ).show()
//                }
//            }
//    }


    //    private val mCallBack: OnVerificationStateChangedCallbacks =
//        object : OnVerificationStateChangedCallbacks() {
//            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
//                signInWithCredential(phoneAuthCredential)
//            }
//
//            override fun onVerificationFailed(e: FirebaseException) {
//                Global.utils!!.hideCustomLoadingDialog()
//                Toast.makeText(this@AuthenticationActivity, e.message, Toast.LENGTH_LONG).show()
//            }
//
//            override fun onCodeSent(code: String, forceResendingToken: ForceResendingToken) {
//                Global.utils!!.hideCustomLoadingDialog()
//                otp_received = code
//                val intent =
//                    Intent(this@AuthenticationActivity, OTPVerificationActivity::class.java)
//                intent.putExtra("optCode", code)
//                intent.putExtra("phoneNo", phoneNo)
//                startActivity(intent)
//            }
//        }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        //   pd.setMessage("Logging In")
        mAuth.signInWithCredential(credential).addOnSuccessListener {
            Global.utils!!.hideCustomLoadingDialog()
            val phone = mAuth.currentUser!!.phoneNumber
            Toast.makeText(this@AuthenticationActivity, "Logging In As$phone", Toast.LENGTH_SHORT)
                .show()

            // Navigation.findNavController(binding.root).navigate(R.id.fragmentB)
        }.addOnFailureListener { e ->
            Global.utils!!.hideCustomLoadingDialog()
            Toast.makeText(this@AuthenticationActivity, "" + e.message, Toast.LENGTH_SHORT).show()
        }
    }

    private val mCallbacks = object : OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
            signInWithPhoneAuthCredential(phoneAuthCredential)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            Global.utils!!.hideCustomLoadingDialog()
            Toast.makeText(this@AuthenticationActivity, "" + e.message, Toast.LENGTH_SHORT)
                .show()
        }


        override fun onCodeSent(verificationId: String, token: ForceResendingToken) {


            mVerificationId = verificationId
            forceResendingToken = token
            Global.utils!!.hideCustomLoadingDialog()
            val intent =
                Intent(this@AuthenticationActivity, OTPVerificationActivity::class.java)
            intent.putExtra("optCode", mVerificationId)
            intent.putExtra("phoneNo", phoneNo)
            startActivity(intent)
//                binding.constraintPhone.setVisibility(View.GONE)
//                binding.constraintOtp.setVisibility(View.VISIBLE)
            Toast.makeText(
                this@AuthenticationActivity,
                "Verification Code Sent...",
                Toast.LENGTH_SHORT
            ).show()


//                binding.tvOtpDescription.setText(
//                    """
//                Please type the verification code we sent
//                ${binding.edNumber.getText().toString().trim()}
//                """.trimIndent()
//                )
        }
    }


}