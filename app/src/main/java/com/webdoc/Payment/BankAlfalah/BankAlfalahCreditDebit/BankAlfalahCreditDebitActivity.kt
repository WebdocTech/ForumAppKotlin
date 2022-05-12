package com.webdoc.payments.BankAlfalahCreditDebit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.webdoc.Essentials.Global
import com.webdoc.payments.BankAlfalahCreditDebit.ViewModel.CreditDebitViewModel
import com.webdoc.theforum.databinding.ActivityBankAlfalahCreditDebitBinding
import java.util.*

class BankAlfalahCreditDebitActivity : AppCompatActivity() {

    var orderid = 0
    var binding: ActivityBankAlfalahCreditDebitBinding? = null
    var viewModel: CreditDebitViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViews()
        observers()

    }


    private fun observers() {
        viewModel!!.LD_openCreditDebitWebview().observe(this, { s ->
            if (s is String) {
                if (s.equals("Payment Succesfull")) {

                } else if (s.equals("PAYMENT FAIL!")) {

                    binding!!.webview.removeAllViews()
                    binding!!.webview.destroy()
                    finish()

                } else if (s.equals("Please wait")) {
                } else if (s.equals("webview Loaded")) {
                }
            }
        })

    }

    private fun initViews() {

        Global.applicationContext = this

        //todo:binding xml file with java file
        binding = ActivityBankAlfalahCreditDebitBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        //todo: connecting view model class with my activity
        viewModel =
            ViewModelProviders.of(this@BankAlfalahCreditDebitActivity)[CreditDebitViewModel::class.java]

        openWebview()


    }

    private fun openWebview() {
        val rand = Random()
        orderid = rand.nextInt(10000000)
        val url =
            "https://apnadoctor.webddocsystems.com/payment/credit.php?orderid=$orderid&amount=1200&id=3"
        viewModel!!.openCreditDebitWebview(binding!!.webview, url)
    }
}