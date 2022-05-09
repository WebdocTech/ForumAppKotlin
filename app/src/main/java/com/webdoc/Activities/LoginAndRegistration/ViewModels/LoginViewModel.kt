package com.webdoc.Activities.LoginAndRegistration.ViewModels

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import com.webdoc.ApiResponseModels.LoginResponse.LoginResponse
import com.webdoc.api.APIInterface
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val MLDLogin: MutableLiveData<LoginResponse> =
        MutableLiveData<LoginResponse>()


    fun LDLogin(): LiveData<LoginResponse> {
        return MLDLogin
    }

    fun calLogin(phoneNumber: String?, type: String?, pin: String?, os: String?, deviceToken: String?) {
        val postParams = JsonObject()
        postParams.addProperty("PhonenumberOrEmail", phoneNumber)
        postParams.addProperty("Type", type)
        postParams.addProperty("Pin", pin)
        postParams.addProperty("OS", os)
        postParams.addProperty("DeviceToken", deviceToken)
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        var client: OkHttpClient? = OkHttpClient()
        client = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.MINUTES)
            .addInterceptor(interceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://theforumapi.webddocsystems.com/api/Customer/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client) // Set HttpClient to be used by Retrofit
            .build()
        val apiInterface = retrofit.create(APIInterface::class.java)
        val call: Call<LoginResponse> = apiInterface.loginUser(postParams)
        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                val loginResponse = response.body()
                MLDLogin.postValue(loginResponse)
                if (!response.isSuccessful) {
                    Toast.makeText(getApplication(), response.body().toString(), Toast.LENGTH_SHORT)
                        .show()
                    return
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                //         Toast.makeText(Global.applicationContext, "onFailure called ", Toast.LENGTH_SHORT).show();
                call.cancel()
            }
        })
    }

}