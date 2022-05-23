package com.webdoc.api;

import com.google.gson.JsonObject;
import com.webdoc.ApiResponseModels.GetPropertiesResponse.GetPropertiesResponse;
import com.webdoc.ApiResponseModels.LoginResponse.LoginResponse;
import com.webdoc.ApiResponseModels.MyPropertyResponse.MyPropertyResponse;
import com.webdoc.ApiResponseModels.RegisterationResponse.RegisterationResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIInterface {



    @POST("Register")
    Call<RegisterationResponse> registerUser(@Body RequestBody body);

    @POST("Login")
    Call<LoginResponse> loginUser(@Body JsonObject jsonObject);

    @GET("Properties")
    Call<GetPropertiesResponse> getProperties();


    @POST("MyProperty")
    Call<MyPropertyResponse> myProperties(@Body JsonObject jsonObject);
}