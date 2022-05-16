package com.webdoc.Fragments.home.ViewModels

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.webdoc.ApiResponseModels.GetPropertiesResponse.GetPropertiesResponse
import com.webdoc.api.APIInterface

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class GetPropertiesViewModel(application: Application) : AndroidViewModel(application) {
    private val MLDGetProperties: MutableLiveData<GetPropertiesResponse> =
        MutableLiveData<GetPropertiesResponse>()


    fun LDGetProperties(): LiveData<GetPropertiesResponse> {
        return MLDGetProperties
    }

    fun getPropertiesAPi() {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        var client: OkHttpClient? = OkHttpClient()
        client = OkHttpClient.Builder()
            .connectTimeout(100, TimeUnit.SECONDS)
            .writeTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.MINUTES)
            .addInterceptor(interceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://theforumapi.webddocsystems.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client) // Set HttpClient to be used by Retrofit
            .build()
        val apiInterface = retrofit.create(APIInterface::class.java)
        val call1: Call<GetPropertiesResponse> = apiInterface.properties
        call1.enqueue(object : Callback<GetPropertiesResponse> {
            override fun onResponse(call: Call<GetPropertiesResponse>, response: Response<GetPropertiesResponse>) {
                val getPropertiesResponse: GetPropertiesResponse? = response.body()
                MLDGetProperties.postValue(getPropertiesResponse)

                // Global.cartResponse = cartResponse;
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplication(), response.body().toString(), Toast.LENGTH_SHORT)
                        .show()
                    return
                }
            }

            override fun onFailure(call: Call<GetPropertiesResponse>, t: Throwable) {
                //         Toast.makeText(Global.applicationContext, "onFailure called ", Toast.LENGTH_SHORT).show();
                call.cancel()
            }
        })
    }

}