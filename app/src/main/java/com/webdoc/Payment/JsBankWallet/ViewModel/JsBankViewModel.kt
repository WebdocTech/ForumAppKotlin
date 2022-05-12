package com.webdoc.webviewlibaray.JsBankWallet.ViewModel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.webdoc.Essentials.Global
import com.webdoc.Essentials.jsonPlaceHolderApi
import com.webdoc.webviewlibaray.JsBankWallet.RequestModels.AccountInquiryRequest
import com.webdoc.webviewlibaray.JsBankWallet.RequestModels.JsReqModel
import com.webdoc.webviewlibaray.JsBankWallet.ResponseModels.JsBankAuthApi
import com.webdoc.webviewlibaray.JsBankWallet.ResponseModels.JsDebitInquiryResult.JsDebitInquiryResult
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class JsBankViewModel(application: Application) : AndroidViewModel(application) {
    private val MLD_JsAuthAPi: MutableLiveData<JsBankAuthApi?> = MutableLiveData<JsBankAuthApi?>()
    private val MLD_JsPaymentInquiryApi: MutableLiveData<JsDebitInquiryResult?> =
        MutableLiveData<JsDebitInquiryResult?>()
    var mobileno: String? = null
    var pkgPrice: String? = null
    fun LD_JsAuthAPi(): LiveData<JsBankAuthApi?> {
        return MLD_JsAuthAPi
    }

    fun LD_JsPAymentInquiryApi(): LiveData<JsDebitInquiryResult?> {
        return MLD_JsPaymentInquiryApi
    }

    //todo:1st Api for JsBank payment
    fun JSBankAuthApi() {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        var client: OkHttpClient? = OkHttpClient()
        client = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.MINUTES)
            .addInterceptor(interceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://sandbox.jsbl.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client) // Set HttpClient to be used by Retrofit
            .build()
        val jsonPlaceHolderApi: jsonPlaceHolderApi = retrofit.create(jsonPlaceHolderApi::class.java)
        val call1: Call<JsBankAuthApi> = jsonPlaceHolderApi.jsAuthApi()
        call1.enqueue(object : Callback<JsBankAuthApi> {
            override fun onResponse(call: Call<JsBankAuthApi>, response: Response<JsBankAuthApi>) {
                val authApi: JsBankAuthApi? = response.body()
                Global.authApiResp = authApi!!
                if (!response.isSuccessful()) {
                    Toast.makeText(
                        Global.applicationContext,
                        response.body().toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }
                MLD_JsAuthAPi.postValue(authApi)
            }

            override fun onFailure(call: Call<JsBankAuthApi>, t: Throwable) {
                Toast.makeText(Global.applicationContext, "onFailure called ", Toast.LENGTH_SHORT)
                    .show()
                call.cancel()
            }
        })
    }

    //todo: 2nd APi for JsBank Payment
    fun jsPaymentInquiryApi(mobileNo: String?) {
        mobileno = mobileNo
        randomNumberString
        pkgPrice = Global.price.toString()
        Global.hideKeyboard(Global.applicationContext!!)
        //  Global.utils.showCustomLoadingDialog(Global.applicationContext)
        CallingApi(Global.newToken!!)
    }

    private fun CallingApi(newToken: String) {
        val reqModel = JsReqModel()
        reqModel.processingCode = "DebitInquiry"
        reqModel.merchantType = "0088"
        reqModel.traceNo = randomNumberString
        reqModel.companyName = "IBCC"
        reqModel.dateTime = (currentlyDateTime)
        reqModel.mobileNo = (mobileno)
        reqModel.channelId = ("IBCC")
        reqModel.terminalId = ("IBCC")
        reqModel.productId = ("10245249")
        reqModel.pinType = ("02")
        reqModel.transactionAmount = ("200")
        reqModel.reserved1 = ""
        reqModel.reserved2 = ""
        reqModel.reserved3 = ""
        reqModel.reserved4 = ""
        reqModel.reserved5 = ""
        reqModel.reserved6 = ""
        reqModel.reserved7 = ""
        reqModel.reserved8 = ""
        reqModel.reserved9 = ""
        reqModel.reserved10 = ""
        val jsreqmodel1 = AccountInquiryRequest()
        jsreqmodel1.jsReqModel = reqModel
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        var client: OkHttpClient? = OkHttpClient()
        client = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.MINUTES)
            .addInterceptor(interceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://sandbox.jsbl.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client) // Set HttpClient to be used by Retrofit
            .build()
        val token = newToken
        val jsonPlaceHolderApi: jsonPlaceHolderApi = retrofit.create(jsonPlaceHolderApi::class.java)
        val call1 =
            jsonPlaceHolderApi.addToPlaylist("Bearer " + token, "application/json", reqModel)

        call1.enqueue(object : Callback<JsDebitInquiryResult> {
            override fun onResponse(
                call: Call<JsDebitInquiryResult>,
                response: Response<JsDebitInquiryResult>
            ) {

                val debitInquiryResult: JsDebitInquiryResult? = response.body()
                Global.paymentInquiry = debitInquiryResult!!
                if (!response.isSuccessful()) {
                    Toast.makeText(
                        Global.applicationContext,
                        response.body().toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }

                MLD_JsPaymentInquiryApi.postValue(debitInquiryResult)
            }

            override fun onFailure(call: Call<JsDebitInquiryResult>, t: Throwable) {
                Toast.makeText(
                    Global.applicationContext,
                    "onFailure called " + t.message,
                    Toast.LENGTH_SHORT
                )
                    .show()
                call.cancel()
            }
        })
    }

    val currentlyDateTime: String
        get() {
            val dateFormat = SimpleDateFormat("yyyyMMddHHmmss")
            return dateFormat.format(Date())
        }

    companion object {
        // It will generate 6 digit random Number.
        // from 0 to 999999
        val randomNumberString: String
            // this will convert any number sequence into 6 character.
            get() {
                // It will generate 6 digit random Number.
                // from 0 to 999999
                val rnd = Random()
                val SixDigitnumber = rnd.nextInt(999999)
                // this will convert any number sequence into 6 character.
                return String.format("%06d", SixDigitnumber)
            }
    }
}
