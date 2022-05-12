package com.webdoc.payments.BankAlfalahWallet

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.webdoc.Essentials.Global
import com.webdoc.payments.BankAlfalahWallet.ViewModel.BankAlfalahWalletViewModel
import com.webdoc.theforum.databinding.ActivityBankAlfalahWalletBinding
import java.util.*

class BankAlfalahWalletActivity : AppCompatActivity() {

    var orderid = 0
    var binding: ActivityBankAlfalahWalletBinding? = null
    var viewModel: BankAlfalahWalletViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        observers()
    }

    private fun initViews() {

        Global.applicationContext = this
        binding = ActivityBankAlfalahWalletBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        viewModel = ViewModelProviders.of(this@BankAlfalahWalletActivity).get(
            BankAlfalahWalletViewModel::class.java
        )
        openWEBVIEW()
    }

    private fun openWEBVIEW() {

        val rand = Random()
        orderid = rand.nextInt(10000000)
        val url =
            "https://apnadoctor.webddocsystems.com/payment/credit.php?orderid=" + orderid + "&amount=" + "2" + "&id=1"
        viewModel!!.openBankAlfalahWalletWebView(binding!!.webView, url)
    }

    private fun observers() {
        viewModel!!.LD_open_BankAlfalah_Wallet_webview().observe(this) { s ->
            if (s is String) {
                if (s.equals("Payment Succesfull")) {


                    Toast.makeText(this, "Payment Succesfull", Toast.LENGTH_LONG).show()

                } else if (s.equals("PAYMENT FAIL!")) {

                    Toast.makeText(this, "Payment Fail", Toast.LENGTH_LONG).show()
                    binding!!.webView.removeAllViews()
                    binding!!.webView.destroy()
                    finish()

                } else if (s.equals("Please wait")) {
                } else if (s.equals("webview Loaded")) {
                }
            }
        }
    }
}