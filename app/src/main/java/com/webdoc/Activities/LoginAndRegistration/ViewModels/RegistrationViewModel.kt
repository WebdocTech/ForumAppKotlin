package com.webdoc.Activities.LoginAndRegistration.ViewModels

import android.app.Application
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.webdoc.ApiResponseModels.RegisterationResponse.RegisterationResponse
import com.webdoc.api.APIInterface
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

class RegistrationViewModel(application: Application) : AndroidViewModel(application) {
    private val MLDRegistration: MutableLiveData<RegisterationResponse> =
        MutableLiveData<RegisterationResponse>()


    fun LDRegistration(): LiveData<RegisterationResponse> {
        return MLDRegistration
    }

    fun calRegisterUserMobile(
        name: String?, email: String?, mobileNo: String?,
        type: String?, pin: String?,
        imageLink: Uri
    ) {
        val requestBody: RequestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("Name", name)
            .addFormDataPart("Email", email)
            .addFormDataPart("MobileNumber", mobileNo)
            .addFormDataPart("Type", type)
            .addFormDataPart("Pin", pin)
            .addFormDataPart(
                "ProfilePicture", imageLink.toString(),
                RequestBody.create(
                    MediaType.parse("application/octet-stream"),
                    File(imageLink.toString())
                )
            )
            .build()
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        var client: OkHttpClient? = OkHttpClient()
        client = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.MINUTES)
            .addInterceptor(interceptor)
            .build()
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://theforumapi.webddocsystems.com/api/Customer/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client) // Set HttpClient to be used by Retrofit
            .build()
        val apiInterface: APIInterface = retrofit.create(APIInterface::class.java)
        val call: retrofit2.Call<RegisterationResponse?> = apiInterface.registerUser(requestBody)
        call.enqueue(object : Callback<RegisterationResponse?> {
            override fun onResponse(
                call: retrofit2.Call<RegisterationResponse?>?,
                response: Response<RegisterationResponse?>
            ) {
                val basicRegistration: RegisterationResponse = response.body()!!
                MLDRegistration.postValue(basicRegistration)
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplication(), response.body().toString(), Toast.LENGTH_SHORT)
                        .show()
                    return
                }
            }

            override fun onFailure(call: retrofit2.Call<RegisterationResponse?>, t: Throwable?) {
                Toast.makeText(getApplication(), "onFailure called ", Toast.LENGTH_SHORT).show();
                Log.i("dfdf", t.toString())
                call.cancel()
            }
        })
    }
    fun calRegisterUserGoogle(
        name: String?, email: String?, mobileNo: String?,
        type: String?, pin: String?,
         googleImage: String?
    ) {
        val requestBody: RequestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("Name", name)
            .addFormDataPart("Email", email)
            .addFormDataPart("MobileNumber", mobileNo)
            .addFormDataPart("Type", type)
            .addFormDataPart("Pin", pin)
            .addFormDataPart("ImageUrl",googleImage)
            .build()
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        var client: OkHttpClient? = OkHttpClient()
        client = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.MINUTES)
            .addInterceptor(interceptor)
            .build()
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://theforumapi.webddocsystems.com/api/Customer/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client) // Set HttpClient to be used by Retrofit
            .build()
        val apiInterface: APIInterface = retrofit.create(APIInterface::class.java)
        val call: retrofit2.Call<RegisterationResponse?> = apiInterface.registerUser(requestBody)
        call.enqueue(object : Callback<RegisterationResponse?> {
            override fun onResponse(
                call: retrofit2.Call<RegisterationResponse?>?,
                response: Response<RegisterationResponse?>
            ) {
                val basicRegistration: RegisterationResponse = response.body()!!
                MLDRegistration.postValue(basicRegistration)
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplication(), response.body().toString(), Toast.LENGTH_SHORT)
                        .show()
                    return
                }
            }

            override fun onFailure(call: retrofit2.Call<RegisterationResponse?>, t: Throwable?) {
                Toast.makeText(getApplication(), "onFailure called ", Toast.LENGTH_SHORT).show();
                Log.i("dfdf", t.toString())
                call.cancel()
            }
        })
    }

}