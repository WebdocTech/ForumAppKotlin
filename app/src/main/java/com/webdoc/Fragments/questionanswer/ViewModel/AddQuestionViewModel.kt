package com.webdoc.Fragments.questionanswer.ViewModel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import com.webdoc.ApiResponseModels.AddQuestionResponse.AddQuestionResponse
import com.webdoc.ApiResponseModels.GetQuestionsResponse.GetQuestionsResponse
import com.webdoc.Essentials.Global
import com.webdoc.api.APIInterface
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class AddQuestionViewModel(application: Application) : AndroidViewModel(application) {
    private val MLDAQA: MutableLiveData<AddQuestionResponse> =
        MutableLiveData<AddQuestionResponse>()

    fun LDAQA(): LiveData<AddQuestionResponse> {
        return MLDAQA
    }

    private val MLDGQA: MutableLiveData<GetQuestionsResponse> =
        MutableLiveData<GetQuestionsResponse>()

    fun LDGQA(): LiveData<GetQuestionsResponse> {
        return MLDGQA
    }

    fun calAddQuestionApi(Question: String?, CustomerId: String?) {
        val postParams = JsonObject()
        postParams.addProperty("Question", Question)
        postParams.addProperty("CustomerId", CustomerId)

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
        val call: Call<AddQuestionResponse> = apiInterface.addQA(postParams)
        call.enqueue(object : Callback<AddQuestionResponse> {
            override fun onResponse(
                call: Call<AddQuestionResponse>,
                response: Response<AddQuestionResponse>
            ) {
                val addqaResponse = response.body()
                MLDAQA.postValue(addqaResponse)
                if (!response.isSuccessful) {
                    Toast.makeText(getApplication(), response.body().toString(), Toast.LENGTH_SHORT)
                        .show()
                    return
                }
            }

            override fun onFailure(call: Call<AddQuestionResponse>, t: Throwable) {
                //         Toast.makeText(Global.applicationContext, "onFailure called ", Toast.LENGTH_SHORT).show();
                call.cancel()
            }
        })
    }

    fun callGetQuestionApi(CustomerProfileId: String?) {
        val postParams = JsonObject()
        postParams.addProperty("CustomerProfileId", CustomerProfileId)

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
            .baseUrl("https://theforumapi.webddocsystems.com/api/Customer/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client) // Set HttpClient to be used by Retrofit
            .build()
        val apiInterface = retrofit.create(APIInterface::class.java)
        val call1: Call<GetQuestionsResponse> = apiInterface.getQuestion(postParams)
        call1.enqueue(object : Callback<GetQuestionsResponse> {
            override fun onResponse(
                call: Call<GetQuestionsResponse>,
                response: Response<GetQuestionsResponse>
            ) {
                val getQuestionResp = response.body()
                MLDGQA.postValue(getQuestionResp)

                // Global.cartResponse = cartResponse;
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplication(), response.body().toString(), Toast.LENGTH_SHORT)
                        .show()
                    return
                }
            }

            override fun onFailure(call: Call<GetQuestionsResponse>, t: Throwable) {
                //         Toast.makeText(Global.applicationContext, "onFailure called ", Toast.LENGTH_SHORT).show();
                call.cancel()
            }
        })
    }

}