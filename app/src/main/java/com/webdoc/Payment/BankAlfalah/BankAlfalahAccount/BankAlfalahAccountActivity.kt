package com.webdoc.payments.PaymentMethods.BankAlfalahAccount

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.webdoc.Essentials.Global
import com.webdoc.payments.PaymentMethods.BankAlfalahAccount.ViewModel.BankAlfalahAccountViewModel
import com.webdoc.theforum.R
import com.webdoc.theforum.databinding.ActivityBankAlfalahAccountBinding
import java.util.*

class BankAlfalahAccountActivity : AppCompatActivity() {

    var binding: ActivityBankAlfalahAccountBinding? = null
    var viewModel: BankAlfalahAccountViewModel? = null
    var orderID = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bank_alfalah_account)
        initViews()
        observers()
    }

    private fun initViews() {
        Global.applicationContext = this
        binding = ActivityBankAlfalahAccountBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        viewModel = ViewModelProviders.of(this@BankAlfalahAccountActivity).get(
            BankAlfalahAccountViewModel::class.java
        )
        openWEBVIEW()
    }

    private fun observers() {
        viewModel!!.LD_open_BankAlfalah_Account_webview().observe(this, { s ->
            if (s is String) {
                if (s.equals("Payment Succesfull"))
                {
                    Toast.makeText(this, "Payment Succesfull", Toast.LENGTH_LONG).show()
                    //todo : CAll your save payment APIS here
                }
            } else if (s.equals("PAYMENT FAIL!")) {
                Toast.makeText(this, "Payment Fail", Toast.LENGTH_LONG).show()
                binding!!.webView.removeAllViews()
                binding!!.webView.destroy()
                finish()

            } else if (s.equals("Please wait")) {
            } else if (s.equals("webview Loaded")) {

            }
        })

    }

    private fun openWEBVIEW() {
        val rand = Random()
        orderID = rand.nextInt(10000000)
        val url =
            "https://apnadoctor.webddocsystems.com/payment/credit.php?orderid=" + orderID + "&amount=" + "1" + "&id=2"
        viewModel!!.openWebviewBankAlfalahAccount(binding!!.webView, url)
    }
}