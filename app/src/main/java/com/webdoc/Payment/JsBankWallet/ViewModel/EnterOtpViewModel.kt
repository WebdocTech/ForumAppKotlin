package com.webdoc.webviewlibaray.JsBankWallet.ViewModel

import android.app.Application
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.webdoc.Essentials.Global
import com.webdoc.Essentials.jsonPlaceHolderApi
import com.webdoc.webviewlibaray.JsBankWallet.RequestModels.JSthirdApiObjectModel
import com.webdoc.webviewlibaray.JsBankWallet.RequestModels.JsThirdAPiReqModel
import com.webdoc.webviewlibaray.JsBankWallet.ResponseModels.JsDebitPaymentResponse.JsDebitPaymentResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class EnterOtpViewModel(application: Application) : AndroidViewModel(application) {

    //Mutable live data
    private val MLD_JsPaymentFinal: MutableLiveData<JsDebitPaymentResponse?> =
        MutableLiveData<JsDebitPaymentResponse?>()

    //live data
    fun LD_JsPaymentFinal(): LiveData<JsDebitPaymentResponse?> {
        return MLD_JsPaymentFinal
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    fun OnPayNowClick(pinCode: String?) {
        // Global.utils.showCustomLoadingDialog(Global.applicationContext)
        val encryptedPin: String = Global.enccriptData(pinCode!!)!!
        val params1 = JSONObject()
        val params = JSONObject()
        val processingCode = "DebitPayment"
        val merchantType = "0088"
        val traceNo = randomNumberString
        val companyName = "IBCC"
        val dateTime = currentlyDateTime
        val mobileNo: String = Global.JS_Wallet_Account_Number!!
        val channelId = "IBCC"
        val terminalId = "IBCC"
        val productId = "10245249"
        val pinType = "02"
        val transactionAmount: String = Global.price.toString()
        val Reserved1 = ""
        val Reserved2 = ""
        val Reserved3 = ""
        val Reserved4 = ""
        val Reserved5 = ""
        val Reserved6 = ""
        val Reserved7 = ""
        val Reserved8 = ""
        val Reserved9 = ""
        val Reserved10 = ""
        try {
            params1.put("PaymentRequest", params)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        callingJsFinalApi(
            Global.newToken!!, encryptedPin, processingCode, merchantType, traceNo,
            companyName, dateTime, mobileNo, channelId,
            terminalId, productId, encryptedPin, pinType, transactionAmount,
            Reserved1, Reserved2, Reserved3, Reserved4, Reserved5, Reserved6,
            Reserved7, Reserved8, Reserved9, Reserved10
        )
    }

    private fun callingJsFinalApi(
        newToken: String,
        encryptedPin: String,
        processingCode: String,
        merchantType: String,
        traceNo: String,
        companyName: String,
        dateTime: String,
        mobileNo: String,
        channelId: String,
        terminalId: String,
        productId: String,
        otpPin: String,
        pinType: String,
        transactionAmount: String,
        reserved1: String,
        reserved2: String,
        reserved3: String,
        reserved4: String,
        reserved5: String,
        reserved6: String,
        reserved7: String,
        reserved8: String,
        reserved9: String,
        reserved10: String
    ) {
        val thirdAPiReqModel = JsThirdAPiReqModel()
        thirdAPiReqModel.processingCode = "DebitInquiry"
        thirdAPiReqModel.merchantType = "0088"
        thirdAPiReqModel.traceNo = JsBankViewModel.randomNumberString
        thirdAPiReqModel.companyName = "IBCC"
        thirdAPiReqModel.dateTime = currentlyDateTime
        thirdAPiReqModel.mobileNo = mobileNo
        thirdAPiReqModel.channelId = ("IBCC")
        thirdAPiReqModel.terminalId = ("IBCC")
        thirdAPiReqModel.productId = ("10245249")
        thirdAPiReqModel.pinType = pinType
        thirdAPiReqModel.otpPin = otpPin
        thirdAPiReqModel.transactionAmount = transactionAmount
        thirdAPiReqModel.reserved1 = ""
        thirdAPiReqModel.reserved2 = ""
        thirdAPiReqModel.reserved3 = ""
        thirdAPiReqModel.reserved4 = ""
        thirdAPiReqModel.reserved5 = ""
        thirdAPiReqModel.reserved6 = ""
        thirdAPiReqModel.reserved7 = ""
        thirdAPiReqModel.reserved8 = ""
        thirdAPiReqModel.reserved9 = ""
        thirdAPiReqModel.reserved10 = ""
        val jSthirdApiObjectModel = JSthirdApiObjectModel()
        jSthirdApiObjectModel.jsThirdAPiReqModel = thirdAPiReqModel
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
            .baseUrl("https://sandbox.jsbl.com/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client) // Set HttpClient to be used by Retrofit
            .build()
        val jsonPlaceHolderApi: jsonPlaceHolderApi = retrofit.create(jsonPlaceHolderApi::class.java)
        val call1: Call<JsDebitPaymentResponse> = jsonPlaceHolderApi.JsPaymentFinal(
            "Bearer $newToken",
            "application/json",
            thirdAPiReqModel
        )
        call1.enqueue(object : Callback<JsDebitPaymentResponse> {
            override fun onResponse(
                call: Call<JsDebitPaymentResponse>,
                response: Response<JsDebitPaymentResponse>
            ) {
                if (!response.isSuccessful()) {
                    Toast.makeText(
                        Global.applicationContext,
                        response.body().toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }
                val pAymentResponse: JsDebitPaymentResponse? = response.body()
                Global.jsPaymentFinal = pAymentResponse!!
                MLD_JsPaymentFinal.postValue(pAymentResponse)
            }

            override fun onFailure(call: Call<JsDebitPaymentResponse>, t: Throwable) {
                Toast.makeText(Global.applicationContext, "onFailure called ", Toast.LENGTH_SHORT)
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
        var sixDigitNumber = 0

        // It will generate 6 digit random Number.
        // from 0 to 999999
        val randomNumberString: String
            // this will convert any number sequence into 6 character.
            get() {
                // It will generate 6 digit random Number.
                // from 0 to 999999
                val rnd = Random()
                sixDigitNumber = rnd.nextInt(999999)
                // this will convert any number sequence into 6 character.
                return String.format("%06d", sixDigitNumber)
            }

        fun thirteenRandomDigits(): Long {
            val r = Random()
            return (1000000000L * (r.nextInt(9000) + 1000)
                    + r.nextInt(1000000000))
        }
    }
}

