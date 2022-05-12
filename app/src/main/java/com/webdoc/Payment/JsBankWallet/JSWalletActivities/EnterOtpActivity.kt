package com.webdoc.webviewlibaray.JsBankWallet.JSWalletActivities

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.webdoc.Essentials.Global
import com.webdoc.theforum.R
import com.webdoc.theforum.databinding.ActivityEnterOtpBinding
import com.webdoc.webviewlibaray.JsBankWallet.ResponseModels.JsDebitPaymentResponse.JsDebitPaymentResponse
import com.webdoc.webviewlibaray.JsBankWallet.ViewModel.EnterOtpViewModel


class EnterOtpActivity : AppCompatActivity() {

    var binding: ActivityEnterOtpBinding? = null
    var viewModel: EnterOtpViewModel? = null
    var pinCode = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        InitViews()
        ClickListeners()
        Observers()
    }

    private fun Observers() {

        viewModel!!.LD_JsPaymentFinal().observe(this, { s ->
            if (s is JsDebitPaymentResponse) {
                if (s.responseCode.equals("00")) {
                    Toast.makeText(this, "Payment Succesfull", Toast.LENGTH_SHORT)
                        .show()

                } else {
                    //     Global.utils.hideCustomLoadingDialog()
                    Toast.makeText(this, "Merchant type missing or invalid", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })
    }

    private fun InitViews() {
        //todo:binding xml fine with java file
        binding = ActivityEnterOtpBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        //todo:connecting View Model with activity
        viewModel = ViewModelProviders.of(this)[EnterOtpViewModel::class.java]
    }

    private fun ClickListeners() {
        binding!!.btnPayNow.setOnClickListener {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                viewModel!!.OnPayNowClick(pinCode)
            }
        }
        binding!!.etPin1GetWalletPinFragPaymentMethodPaymentMethod.setOnTouchListener { v, event ->
            binding!!.etPin1GetWalletPinFragPaymentMethodPaymentMethod.text.clear()
            binding!!.etPin2GetWalletPinFragPaymentMethodPaymentMethod.text.clear()
            binding!!.etPin3GetWalletPinFragPaymentMethodPaymentMethod.text.clear()
            binding!!.etPin4GetWalletPinFragPaymentMethodPaymentMethod.text.clear()
            binding!!.etPin5GetWalletPinFragPaymentMethodPaymentMethod5.text.toString()
            binding!!.etPin1GetWalletPinFragPaymentMethodPaymentMethod.requestFocus()
            false
        }
        binding!!.etPin2GetWalletPinFragPaymentMethodPaymentMethod.setOnTouchListener { v, event ->
            binding!!.etPin2GetWalletPinFragPaymentMethodPaymentMethod.text.clear()
            binding!!.etPin3GetWalletPinFragPaymentMethodPaymentMethod.text.clear()
            binding!!.etPin4GetWalletPinFragPaymentMethodPaymentMethod.text.clear()
            binding!!.etPin5GetWalletPinFragPaymentMethodPaymentMethod5.text.toString()
            binding!!.etPin2GetWalletPinFragPaymentMethodPaymentMethod.requestFocus()
            false
        }
        binding!!.etPin3GetWalletPinFragPaymentMethodPaymentMethod.setOnTouchListener { v, event ->
            binding!!.etPin3GetWalletPinFragPaymentMethodPaymentMethod.text.clear()
            binding!!.etPin4GetWalletPinFragPaymentMethodPaymentMethod.text.clear()
            binding!!.etPin5GetWalletPinFragPaymentMethodPaymentMethod5.text.toString()
            binding!!.etPin3GetWalletPinFragPaymentMethodPaymentMethod.requestFocus()
            false
        }
        binding!!.etPin4GetWalletPinFragPaymentMethodPaymentMethod.setOnTouchListener { v, event ->
            binding!!.etPin4GetWalletPinFragPaymentMethodPaymentMethod.text.clear()
            binding!!.etPin5GetWalletPinFragPaymentMethodPaymentMethod5.text.toString()
            binding!!.etPin4GetWalletPinFragPaymentMethodPaymentMethod.requestFocus()
            false
        }
        binding!!.etPin5GetWalletPinFragPaymentMethodPaymentMethod5.setOnTouchListener { v, event ->
            binding!!.etPin5GetWalletPinFragPaymentMethodPaymentMethod5.text.clear()
            binding!!.etPin5GetWalletPinFragPaymentMethodPaymentMethod5.requestFocus()
            false
        }
        binding!!.etPin1GetWalletPinFragPaymentMethodPaymentMethod.addTextChangedListener(object :
            TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                binding!!.etPin2GetWalletPinFragPaymentMethodPaymentMethod.requestFocus()
            }
        })
        binding!!.etPin2GetWalletPinFragPaymentMethodPaymentMethod.addTextChangedListener(object :
            TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                binding!!.etPin3GetWalletPinFragPaymentMethodPaymentMethod.requestFocus()
            }
        })
        binding!!.etPin3GetWalletPinFragPaymentMethodPaymentMethod.addTextChangedListener(object :
            TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                binding!!.etPin4GetWalletPinFragPaymentMethodPaymentMethod.requestFocus()
            }
        })
        binding!!.etPin4GetWalletPinFragPaymentMethodPaymentMethod.addTextChangedListener(object :
            TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                binding!!.etPin5GetWalletPinFragPaymentMethodPaymentMethod5.requestFocus()
            }
        })
        binding!!.etPin5GetWalletPinFragPaymentMethodPaymentMethod5.addTextChangedListener(object :
            TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                binding!!.etPin5GetWalletPinFragPaymentMethodPaymentMethod5.requestFocus()
                pinCode =
                    binding!!.etPin1GetWalletPinFragPaymentMethodPaymentMethod.text.toString() + "" + binding!!.etPin2GetWalletPinFragPaymentMethodPaymentMethod.text + "" + binding!!.etPin3GetWalletPinFragPaymentMethodPaymentMethod.text + "" + binding!!.etPin4GetWalletPinFragPaymentMethodPaymentMethod.text + "" + binding!!.etPin5GetWalletPinFragPaymentMethodPaymentMethod5.text
                if (pinCode.length < 4) {
                    binding!!.etPin1GetWalletPinFragPaymentMethodPaymentMethod.requestFocus()
                    binding!!.etPin1GetWalletPinFragPaymentMethodPaymentMethod.text.clear()
                    binding!!.etPin2GetWalletPinFragPaymentMethodPaymentMethod.text.clear()
                    binding!!.etPin3GetWalletPinFragPaymentMethodPaymentMethod.text.clear()
                    binding!!.etPin4GetWalletPinFragPaymentMethodPaymentMethod.text.clear()
                    binding!!.etPin5GetWalletPinFragPaymentMethodPaymentMethod5.text.clear()
                    binding!!.btnPayNow.background =
                        applicationContext.resources.getDrawable(R.drawable.circle_light_grey)
                    binding!!.btnPayNow.isEnabled = false
                    binding!!.btnPayNow.clearAnimation()
                } else {
                    Global.hideKeyboard(this@EnterOtpActivity)
                    ActivatePaynowBtn()
                }
            }
        })
        binding!!.etOtp.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                if (charSequence.length > 5) {
                    ActivatePaynowBtn()
                }
            }

            override fun afterTextChanged(editable: Editable) {}
        })
    }

    private fun ActivatePaynowBtn() {
        binding!!.btnPayNow.background = application.resources.getDrawable(R.drawable.circle_blue)
        binding!!.btnPayNow.isEnabled = true
        //  binding!!.btnPayNow.startAnimation(Global.utils.startBlinkAnimation(this))
    }


}