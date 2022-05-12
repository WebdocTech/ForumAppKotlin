package com.webdoc.Payment

import android.os.Bundle
import android.widget.Adapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.webdoc.Adapters.PaymentAdapter
import com.webdoc.Essentials.Global
import com.webdoc.Models.PaymentModel
import com.webdoc.theforum.R
import com.webdoc.theforum.databinding.ActivityPaymentMethodsBinding

class PaymentMethodsActivity : AppCompatActivity() {

    var binding: ActivityPaymentMethodsBinding? = null
    var paymentMethodsAdapter: Adapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViews()

    }

    private fun initViews() {
        binding = ActivityPaymentMethodsBinding.inflate(layoutInflater)
        setContentView(binding!!.getRoot())
        //RECYCLER VIEW
        val layoutManager =
            LinearLayoutManager(MainActivity@ this, LinearLayoutManager.VERTICAL, false)
        binding!!.rvPaymentMethods.setLayoutManager(layoutManager)
        binding!!.rvPaymentMethods.setHasFixedSize(true)
        paymentMethodsAdapter = PaymentAdapter(MainActivity@ this)
        binding!!.rvPaymentMethods.setAdapter(paymentMethodsAdapter as PaymentAdapter)

        val logo =
            intArrayOf(
                R.drawable.bank_alfalah_logo,
                R.drawable.bank_alfalah_logo,

                /*    R.drawable.jazzcash,*/
                /*  R.drawable.easypaisa_icon,*/
                R.drawable.js_bank_logo,
                R.drawable.credit,
                R.drawable.authentication
                /*    R.drawable.easypaisa_icon,
                    R.drawable.easypaisa_icon*/
            )
        val title = arrayOf(
            "Bank Alfalah Account",
            "Bank Alfalah Wallet",
          /*  "Jazz Cash Wallet",
            "Easy Paisa Wallet",*/
            "Js Wallet",
            "Credit/Debit Card",
            "International Payment"
         /*   "OTC Through EasyPaisa",
            "Easy Paisa Credit Debit"*/
        )

        for (i in logo.indices) {
            val model = PaymentModel(logo[i], title[i])
            Global.paymentTitle.add(model)
        }

    }
}
