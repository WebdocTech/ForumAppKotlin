package com.webdoc.Payment.StripePayment

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.webdoc.Essentials.Global
import com.webdoc.Essentials.Preferences
import com.webdoc.Payment.StripePayment.ViewModel.StripeViewModel
import com.webdoc.theforum.databinding.ActivityStripeBinding

class StripeActivity : AppCompatActivity() {
    private var binding: ActivityStripeBinding? = null
    private var viewModel: StripeViewModel? = null
    private var preferences: Preferences? = null
    private val totalAmountinRupees = 0.0
    private val sevenDollar = 0.0
    private val fivePercentAmount = 0.0
    private val finalAmountinDollor = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        //  observers()
    }

    /*  private fun observers() {
          viewModel?.LD_savePaymentInfoEquialance()?.observe(this) { s ->
              if (s != null) {
                  if (s.getResult().getResponseCode().equals("0000")) {

                      Toast.makeText(this, "payment Succesfull", Toast.LENGTH_LONG)
                      Global.utils?.hideCustomLoadingDialog()

                  } else {
                      Global.utils?.hideCustomLoadingDialog()
                      Toast.makeText(this, "resp fail ", Toast.LENGTH_SHORT).show()
                  }
              }
          }
          viewModel?.LD_getDollorRate()?.observe(this) { response ->
              if (response != null) {
                  //    double priceInDollors = calculateInternationalCharges(response.getRates().getPkr());
                  val pkrRate: Double = response.getRates().getPkr()
                  viewModel?.callSavePaymentEQApi(preferences!!.getKeyUserPhone(), pkrRate)
              }
          }
      }*/

    private fun openWebView() {
        val url =
            "https://ibccportal.webddocsystems.com/InternationalPayment/internationalpayment.php?totalprice=" + finalAmountinDollor + "&caseid=" + 1
        binding!!.webView.getSettings().setJavaScriptEnabled(true)
        binding!!.webView.getSettings().setLoadWithOverviewMode(true)
        binding!!.webView.getSettings().setUseWideViewPort(true)
        binding!!.webView.getSettings().setBuiltInZoomControls(true)
        binding!!.webView.getSettings().setPluginState(WebSettings.PluginState.ON)
        binding!!.webView.setWebViewClient(MyWebViewClient())
        binding!!.webView.loadUrl(url)
        binding!!.webView.setWebViewClient(object : WebViewClient() {
            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap) {
                super.onPageStarted(view, url, favicon)
                Log.d("WebView", "your current url when webpage loading..$url")
            }

            override fun onPageFinished(view: WebView, url: String) {
                Log.d("WebView", "your current url when webpage loading.. finish$url")
                if (url.contains("https://ibccportal.webddocsystems.com/index.php/InternationalPayment/stripePost")) {
                    //todo: payment success\\

                    binding!!.webView.removeAllViews()
                    binding!!.webView.destroy()
                    Global.utils!!.showSuccessSnakeBar(this@StripeActivity, "Success")

                } /*else {
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
            }

            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                println("when you click on any interlink on webview that time you got url :-$url")
                return super.shouldOverrideUrlLoading(view, url)
            }
        })
    }

    private fun initViews() {
        binding = ActivityStripeBinding.inflate(layoutInflater)
        setContentView(binding!!.getRoot())
        preferences = Preferences(this@StripeActivity)
        viewModel = ViewModelProviders.of(this).get(StripeViewModel::class.java)
        //  viewModel!!.callDollorRateApi(this)
        openWebView()
        //viewModel.callSavePaymentEQApi(preferences.getKeyUserPhone());
    }

    //todo : methods
    private inner class MyWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            return false
        }
    }

    fun calculateInternationalCharges(pkrRate: Double): Double {
        //200 courier fee
        /*  int documentsTotalFee = Global.documentsTotalFee;
        sevenDollar = pkrRate * 7; //webdoc amount:
        double amount = documentsTotalFee + 200 + sevenDollar;
        fivePercentAmount = amount * 0.05; //bank charges:\
        totalAmountinRupees = amount + fivePercentAmount;
        finalAmountinDollor = totalAmountinRupees / pkrRate;*/
        return finalAmountinDollor
    }
}