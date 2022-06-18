package com.webdoc.Payment.StripePayment

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.webdoc.Activities.MainActivity
import com.webdoc.Essentials.Global
import com.webdoc.Fragments.myproperty.MyPropertyFragment
import com.webdoc.Payment.StripePayment.ViewModel.StripeViewModel
import com.webdoc.theforum.databinding.ActivityStripeBinding
import kotlin.math.roundToInt

class StripeActivity : AppCompatActivity() {
    private var binding: ActivityStripeBinding? = null
    private var viewModel: StripeViewModel? = null
    private var totalusd: Double? = 0.0
    private var priceInDollors: Double? = 0.0
    private var convrt: Double? = 0.0
    private var finalamount: Int? = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        observers()
    }

    private fun observers() {
        viewModel!!.LD_getDollorRate().observe(this) { response ->
            if (response != null) {
                try {
                    // val amountPay = 100
                    priceInDollors = response.rates!!.pkr
                    if (Global.sellType.equals("Installment")) {
                        convrt = Global.installmentAmount.toDouble() / priceInDollors!!

                    } else if (Global.sellType.equals("Full Payment")) {
                        convrt = Global.totalAmount.toDouble() / priceInDollors!!
                    }
                    totalusd = (convrt!! * 100.0).roundToInt() / 100.0
                    finalamount = totalusd!!.toInt()

                    openWebView()
                } catch (ex: Exception) {
                    Log.i("Dollar ex", "observers: " + ex)
                }
            }
        }
        viewModel!!.LDSoldProperty().observe(this) { response ->
            if (response != null) {
                if (response.result!!.responseCode.equals("0000")) {
                    Toast.makeText(this, "Property Sold", Toast.LENGTH_LONG).show()
                    startActivity(Intent(this@StripeActivity, MainActivity::class.java))
                    finishAffinity()
                    Global.utils!!.hideCustomLoadingDialog()
                } else {
                    Global.utils!!.hideCustomLoadingDialog()
                    Toast.makeText(this, "Property Not Sold", Toast.LENGTH_LONG).show()
                }
            }
        }

    }

    private fun openWebView() {

        try {
            var url =
                "https://testingibcc.webddocsystems.com/InternationalPayment/internationalpayment.php?totalprice=" + finalamount + "&caseid=" + Global.id
            binding!!.webView.getSettings().setJavaScriptEnabled(true)
            binding!!.webView.getSettings().setLoadWithOverviewMode(true)
            binding!!.webView.getSettings().setUseWideViewPort(true)
            binding!!.webView.getSettings().setDomStorageEnabled(true);
            binding!!.webView.getSettings().setBuiltInZoomControls(true)
            binding!!.webView.getSettings().setPluginState(WebSettings.PluginState.ON)
            binding!!.webView.setWebViewClient(MyWebViewClient())
            binding!!.webView.loadUrl(url)
            Global.utils!!.hideCustomLoadingDialog()
        } catch (ex: java.lang.Exception) {
            Toast.makeText(this, ex.toString(), Toast.LENGTH_LONG)
        }

        //  try {

        binding!!.webView.setWebViewClient(object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                Log.d("WebView", "your current url when webpage loading..$url")

            }

            override fun onPageFinished(view: WebView, url: String) {
                Log.d("WebView", "your current url when webpage loading.. finish$url")
                Global.utils!!.hideCustomLoadingDialog()
//                    if (url.contains("https://testingibcc.webddocsystems.com/InternationalPayment/internationalpayment.php?totalprice=" + finalamount)) {
//                        //todo: payment success\\
//
//
//
//                    }
                if (url.contains("https://testingibcc.webddocsystems.com/index.php/InternationalPayment/stripePost")) {
                   // Global.utils!!.showSuccessSnakeBar(this@StripeActivity, "Success")
                    Global.utils!!.showCustomLoadingDialog(this@StripeActivity)
                    if (Global.sellType.equals("Full Payment")) {
                       // Global.utils!!.showCustomLoadingDialog(this@StripeActivity)
                        viewModel!!.callingSoldPropertyApi(
                            Global.id, Global.userid, Global.sellType,
                            Global.modeOfPayment, "123456Abc", "International Payment",
                            finalamount.toString(), "0", Global.downPayment, Global.totalAmount,
                            Global.propertyName, "0"
                        )
                        binding!!.webView.removeAllViews()
                        binding!!.webView.destroy()
                    } else if (Global.sellType.equals("Installment")) {

                        viewModel!!.callingSoldPropertyApi(
                            Global.id, Global.userid, Global.sellType,
                            Global.modeOfPayment, "123456Abc", "International Payment",
                            finalamount.toString(), Global.installmentNo, Global.downPayment, Global.totalAmount,
                            Global.propertyName, Global.installmentAmount
                        )
                        binding!!.webView.removeAllViews()
                        binding!!.webView.destroy()
                    }


                }/*else {
                    //todo: payment Fail
                    Toast.makeText(StripeActivity.this, "PAYMENT FAIL!", Toast.LENGTH_SHORT).show();
                    binding.webView.removeAllViews();
                    binding.webView.destroy();
                    finish();
                    Global.utils.hideCustomLoadingDialog();
                }*/
                super.onPageFinished(view, url)
            }

            override fun onLoadResource(view: WebView, url: String) {
                // TODO Auto-generated method stub
                super.onLoadResource(view, url)
              //  Global.utils!!.showCustomLoadingDialog(this@StripeActivity)
            }

            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                println("when you click on any interlink on webview that time you got url :-$url")
                Global.utils!!.showCustomLoadingDialog(this@StripeActivity)
                return super.shouldOverrideUrlLoading(view, url)

            }
        })
        //   }
//    catch (ex: java.lang.Exception) {
//            Toast.makeText(this, ex.toString(), Toast.LENGTH_LONG).show()
//        }

    }

    private fun initViews() {
        binding = ActivityStripeBinding.inflate(layoutInflater)
        setContentView(binding!!.getRoot())
        // preferences = Preferences(this@StripeActivity)
        viewModel = ViewModelProvider(this).get(StripeViewModel::class.java)
        Global.utils!!.showCustomLoadingDialog(this@StripeActivity)
        viewModel!!.callDollorRateApi(this@StripeActivity)

        // openWebView()
        //viewModel.callSavePaymentEQApi(preferences.getKeyUserPhone());
    }

    //todo : methods
    public inner class MyWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            return false
        }
    }

//    fun calculateInternationalCharges(pkrRate: Double): Double {
//        //200 courier fee
//          documentsTotalFee = Global.documentsTotalFee;
//        sevenDollar = pkrRate * 7; //webdoc amount:
//        double amount = documentsTotalFee + 200 + sevenDollar;
//        fivePercentAmount = amount * 0.05; //bank charges:\
//        totalAmountinRupees = amount + fivePercentAmount;
//        finalAmountinDollor = totalAmountinRupees / pkrRate;
//        return finalAmountinDollor
//    }
}