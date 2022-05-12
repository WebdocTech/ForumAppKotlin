package com.webdoc.payments.BankAlfalahWallet.ViewModel

import android.app.Application
import android.graphics.Bitmap
import android.util.Log
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


class BankAlfalahWalletViewModel  //todo:constructor
    (application: Application) : AndroidViewModel(application) {
    var wb: WebView? = null

    //todo:Mutable Live Data
    private val MLD_open_BankAlfalah_Wallet_Webview = MutableLiveData<String>()


    //todo:Live Data
    fun LD_open_BankAlfalah_Wallet_webview(): LiveData<String> {
        return MLD_open_BankAlfalah_Wallet_Webview
    }


    //todo:Methods
    private inner class MyWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            return false
        }
    }

    fun openBankAlfalahWalletWebView(webView: WebView?, url: String?) {
        wb = webView
        wb!!.settings.javaScriptEnabled = true
        wb!!.settings.loadWithOverviewMode = true
        wb!!.settings.useWideViewPort = true
        wb!!.settings.builtInZoomControls = true
        wb!!.settings.pluginState = WebSettings.PluginState.ON
        wb!!.webViewClient = MyWebViewClient()
        wb!!.loadUrl(url!!)
        wb!!.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                Log.d("WebView", "your current url when webpage loading..$url")
            }

            override fun onPageFinished(view: WebView, url: String) {
                Log.d("WebView", "your current url when webpage loading.. finish$url")
                if (url.contains("https://merchants.bankalfalah.com/Payments/Payments/SuccessPayment/00")) {

                    //todo: payment success
                    MLD_open_BankAlfalah_Wallet_Webview.postValue("Payment Succesfull")
                } else if (url.contains("www.webdoc.com.pk?RC")) {

                    //todo: payment Fail
                    MLD_open_BankAlfalah_Wallet_Webview.postValue("PAYMENT FAIL!")
                } else if (url.contains("/SSO/InvalidRequest")) {

                    //todo: invalid request
                    MLD_open_BankAlfalah_Wallet_Webview.postValue("InvalidRequest")
                } else if (url.contains("https://portal.webdoc.com.pk/transection/A/webdoc.php?orderid=")) {
                    MLD_open_BankAlfalah_Wallet_Webview.postValue("Please wait")
                } else if (url.contains("https://merchants.bankalfalah.com/PaymentsProd/PaymentsProd/Create?")) {
                    MLD_open_BankAlfalah_Wallet_Webview.postValue("webview Loaded")
                }
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
        }
    }


}


