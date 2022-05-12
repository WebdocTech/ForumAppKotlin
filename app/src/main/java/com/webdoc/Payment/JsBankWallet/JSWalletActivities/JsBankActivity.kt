package com.webdoc.webviewlibaray.JsBankWallet.JSWalletActivities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.webdoc.Essentials.Global
import com.webdoc.theforum.databinding.ActivityJsBankBinding
import com.webdoc.webviewlibaray.JsBankWallet.ResponseModels.JsBankAuthApi
import com.webdoc.webviewlibaray.JsBankWallet.ResponseModels.JsDebitInquiryResult.JsDebitInquiryResult
import com.webdoc.webviewlibaray.JsBankWallet.ViewModel.JsBankViewModel


class JsBankActivity : AppCompatActivity() {

    var binding: ActivityJsBankBinding? = null
    var viewModel: JsBankViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViews()
        clickListeners()
        observers()
    }

    private fun initViews() {
        Global.applicationContext = this
        binding = ActivityJsBankBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        //todo:connecting View Model with activity
        viewModel = ViewModelProviders.of(this)[JsBankViewModel::class.java]
        // Global.utils.showCustomLoadingDialog(this@JsBankActivity)
        viewModel!!.JSBankAuthApi()
    }

    private fun clickListeners() {
        binding!!.etAccountNo.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (binding!!.etAccountNo.text!!.length === 11) {
                    Global.hideKeyboard(this@JsBankActivity)
                    binding!!.btnNext.visibility = View.VISIBLE
                } else {
                    binding!!.ccEnterCnic.visibility = View.GONE
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })
        binding!!.btnNext.setOnClickListener {
            val MobileNo = binding!!.etAccountNo.text.toString()
            Global.JS_Wallet_Account_Number = MobileNo
            viewModel!!.jsPaymentInquiryApi(MobileNo)
        }
    }

    private fun observers() {
        viewModel!!.LD_JsAuthAPi().observe(this, { s ->
            if (s is JsBankAuthApi) {
                if (Global.authApiResp.accessToken != null) {
                    Global.newToken = s.accessToken
                    // Global.utils.hideCustomLoadingDialog()
                }
            }
        })
        viewModel!!.LD_JsPAymentInquiryApi().observe(this, { s ->
            if (s is JsDebitInquiryResult) {
                if (s.responseCode.equals("00")) {
                    val intent = Intent(this@JsBankActivity, EnterOtpActivity::class.java)
                    startActivity(intent)
                    //Global.utils.hideCustomLoadingDialog()
                } else if (s.responseCode.equals("98")) {
                    // Global.utils.hideCustomLoadingDialog()
                    Toast.makeText(this, "Merchant type missing or invalid", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })
    }

}