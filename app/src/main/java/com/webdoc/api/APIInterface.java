package com.webdoc.api;

import com.google.gson.JsonObject;
import com.webdoc.ApiResponseModels.AddQuestionResponse.AddQuestionResponse;
import com.webdoc.ApiResponseModels.GetPropertiesResponse.GetPropertiesResponse;
import com.webdoc.ApiResponseModels.GetQuestionsResponse.GetQuestionsResponse;
import com.webdoc.ApiResponseModels.LoginResponse.LoginResponse;
import com.webdoc.ApiResponseModels.MyPropertyResponse.MyPropertyResponse;
import com.webdoc.ApiResponseModels.RegisterationResponse.RegisterationResponse;
import com.webdoc.Fragments.video.VideoResponse.VideosResonse;
import com.webdoc.Payment.SoldPropertyResponse.SoldPropertyResponse;
import com.webdoc.Payment.StripePayment.responseModel.DollerRateResponseModel;

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

    @POST("AddNewQuestion")
    Call<AddQuestionResponse> addQA(@Body JsonObject jsonObject);

    @POST("GetQuestions")
    Call<GetQuestionsResponse> getQuestion(@Body JsonObject jsonObject);


    @POST("SoldPropertyDetails")
    Call<SoldPropertyResponse> soldProperty(@Body JsonObject jsonObject);

    @GET("Properties")
    Call<GetPropertiesResponse> getProperties();

    @POST("MyProperty")
    Call<MyPropertyResponse> myProperties(@Body JsonObject jsonObject);

    @GET("Customer/Video")
    Call<VideosResonse> getVideos();

    @GET("api/latest.json?app_id=fb516545580f47859cadb37203688a08")
    Call<DollerRateResponseModel> callDollorRate();
}