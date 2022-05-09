package com.webdoc.Activities.LoginAndRegistration

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.webdoc.Activities.MainActivity
import com.webdoc.Essentials.Global
import com.webdoc.theforum.databinding.ActivityGetNumberBinding
import com.webdoc.theforum.databinding.ActivityLoginOptionsBinding

class GetNumberActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGetNumberBinding
    private var phoneNo: String? = null
    private lateinit var prefs: SharedPreferences
    private lateinit var edit: SharedPreferences.Editor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        clickListerners()
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
                //     Global.utils!!.showCustomLoadingDialog(this@GetNumberActivity)
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                phoneNo = binding.ccp.fullNumberWithPlus.toString() + number
                edit.putString("phoneNo", phoneNo)
                edit.commit()
                edit.apply()
                Toast.makeText(
                    this@GetNumberActivity, "Logged in Successful",
                    Toast.LENGTH_SHORT
                ).show();
            }
        }

    }

    private fun initViews() {
        binding = ActivityGetNumberBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prefs = getSharedPreferences("abc", Context.MODE_PRIVATE)
        edit = prefs?.edit()
    }
}