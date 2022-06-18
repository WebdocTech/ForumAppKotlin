package com.webdoc.Payment.StripePayment.ViewModel

import android.app.Activity
import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import com.squareup.okhttp.OkHttpClient
import com.webdoc.ApiResponseModels.LoginResponse.LoginResponse
import com.webdoc.Essentials.Global
import com.webdoc.Essentials.jsonPlaceHolderApi
import com.webdoc.Payment.SoldPropertyResponse.SoldPropertyResponse
import com.webdoc.Payment.StripePayment.responseModel.DollerRateResponseModel
import com.webdoc.api.APIInterface
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class StripeViewModel(application: Application) : AndroidViewModel(application) {


    private val MLD_dollor_response_model: MutableLiveData<DollerRateResponseModel> =
        MutableLiveData<DollerRateResponseModel>()

    fun LD_getDollorRate(): LiveData<DollerRateResponseModel> {
        return MLD_dollor_response_model
    }

    private val MLDSoldProperty: MutableLiveData<SoldPropertyResponse> =
        MutableLiveData<SoldPropertyResponse>()

    fun LDSoldProperty(): LiveData<SoldPropertyResponse> {
        return MLDSoldProperty
    }


   fun callingSoldPropertyApi(
        propertyId: Int,
        customerProfileId: String,
        sellType: String,
        modeOfPayment: String?,
        transectionId: String?,
        bank: String,
        paidAmount: String,
        noOfInstallment: String,
        downPayment: String?,
        totalAmount: String,
        propertyName: String,
        installmentAmount: String
    ) {
        val postParams = JsonObject()
        postParams.addProperty("propertyId", propertyId)
        postParams.addProperty("customerProfileId", customerProfileId)
        postParams.addProperty("sellType", sellType)
        postParams.addProperty("modeOfPayment", modeOfPayment)
        postParams.addProperty("transectionId", transectionId)
        postParams.addProperty("bank", bank)
        postParams.addProperty("paidAmount", paidAmount)
        postParams.addProperty("noOfInstallment", noOfInstallment)
        postParams.addProperty("downPayment", downPayment)
        postParams.addProperty("totalAmount", totalAmount)
        postParams.addProperty("propertyName", propertyName)
        postParams.addProperty("installmentAmount", installmentAmount)
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        var client: okhttp3.OkHttpClient? = okhttp3.OkHttpClient()
        client = okhttp3.OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.MINUTES)
            .addInterceptor(interceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://theforumapi.webddocsystems.com/api/Properties/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client) // Set HttpClient to be used by Retrofit
            .build()
        val apiInterface = retrofit.create(APIInterface::class.java)
        val call: Call<SoldPropertyResponse> = apiInterface.soldProperty(postParams)
        call.enqueue(object : Callback<SoldPropertyResponse> {
            override fun onResponse(
                call: Call<SoldPropertyResponse>,
                response: Response<SoldPropertyResponse>
            ) {
                val soldPropertyResponse = response.body()
                MLDSoldProperty.postValue(soldPropertyResponse)
                if (!response.isSuccessful) {
                    Toast.makeText(getApplication(), response.body().toString(), Toast.LENGTH_SHORT)
                        .show()
                    return
                }
            }

            override fun onFailure(call: Call<SoldPropertyResponse>, t: Throwable) {
                //         Toast.makeText(Global.applicationContext, "onFailure called ", Toast.LENGTH_SHORT).show();
                call.cancel()
            }
        })
    }

    fun callDollorRateApi(activity: Activity?) {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        var client: okhttp3.OkHttpClient? = okhttp3.OkHttpClient()
        client = okhttp3.OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.MINUTES)
            .addInterceptor(interceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://openexchangerates.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client) // Set HttpClient to be used by Retrofit
            .build()
        val apiInterface = retrofit.create(APIInterface::class.java)
        val call: Call<DollerRateResponseModel> = apiInterface.callDollorRate()
        call.enqueue(object : Callback<DollerRateResponseModel> {
            override fun onResponse(
                call: Call<DollerRateResponseModel>,
                response: Response<DollerRateResponseModel>
            ) {
                val dollarRateResponse = response.body()
                MLD_dollor_response_model.postValue(dollarRateResponse)
                if (!response.isSuccessful) {
                    Toast.makeText(getApplication(), response.body().toString(), Toast.LENGTH_SHORT)
                        .show()
                    return
                }
            }

            override fun onFailure(call: Call<DollerRateResponseModel>, t: Throwable) {
                //         Toast.makeText(Global.applicationContext, "onFailure called ", Toast.LENGTH_SHORT).show();
                call.cancel()
            }
        })
    }
}




