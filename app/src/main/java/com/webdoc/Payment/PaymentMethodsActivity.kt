package com.webdoc.Payment

import android.content.Intent
import android.os.Bundle
import android.widget.Adapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.webdoc.Adapters.PaymentAdapter
import com.webdoc.Essentials.Global
import com.webdoc.Models.PaymentModel
import com.webdoc.Payment.StripePayment.StripeActivity
import com.webdoc.theforum.R
import com.webdoc.theforum.databinding.ActivityPaymentMethodsBinding
import java.text.DecimalFormat

class PaymentMethodsActivity : AppCompatActivity() {

    var binding: ActivityPaymentMethodsBinding? = null
    var paymentMethodsAdapter: Adapter? = null
    private var instalment: String? = null
    private var fulPay: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViews()
        clicklistners()

    }

    private fun clicklistners() {
        binding!!.clInternational.setOnClickListener {
            val intent = Intent(this@PaymentMethodsActivity, StripeActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initViews() {
        binding = ActivityPaymentMethodsBinding.inflate(layoutInflater)
        setContentView(binding!!.getRoot())
       val formatter = DecimalFormat("#,###,###")
        if(Global.sellType.equals("Installment")){
            val installmentAmount: String? = formatter.format(Global.installmentAmount.toInt())
            binding!!.tvPayamount.setText("Rs:"+installmentAmount!!.toString() +"/-")
        }else if(Global.sellType.equals("Full Payment")){
            val totalAmount: String? = formatter.format(Global.totalAmount.toInt())
            binding!!.tvPayamount.setText("Rs:"+totalAmount!!.toString() + "/-")
        }

        //RECYCLER VIEW
//        val layoutManager =
//            LinearLayoutManager(MainActivity@ this, LinearLayoutManager.VERTICAL, false)
//        binding!!.rvPaymentMethods.setLayoutManager(layoutManager)
//        binding!!.rvPaymentMethods.setHasFixedSize(true)
//        paymentMethodsAdapter = PaymentAdapter(MainActivity@ this)
//        binding!!.rvPaymentMethods.setAdapter(paymentMethodsAdapter as PaymentAdapter)
//
//        val logo =
//            intArrayOf(
//                R.drawable.bank_alfalah_logo,
//                R.drawable.bank_alfalah_logo,
//
//                /*    R.drawable.jazzcash,*/
//                /*  R.drawable.easypaisa_icon,*/
//                R.drawable.js_bank_logo,
//                R.drawable.credit,
//                R.drawable.authentication
//                /*    R.drawable.easypaisa_icon,
//                    R.drawable.easypaisa_icon*/
//            )
//        val title = arrayOf(
//            "Bank Alfalah Account",
//            "Bank Alfalah Wallet",
//          /*  "Jazz Cash Wallet",
//            "Easy Paisa Wallet",*/
//            "Js Wallet",
//            "Credit/Debit Card",
//            "International Payment"
//         /*   "OTC Through EasyPaisa",
//            "Easy Paisa Credit Debit"*/
//        )
//
//        for (i in logo.indices) {
//            val model = PaymentModel(logo[i], title[i])
//            Global.paymentTitle.add(model)
//        }

    }
}
