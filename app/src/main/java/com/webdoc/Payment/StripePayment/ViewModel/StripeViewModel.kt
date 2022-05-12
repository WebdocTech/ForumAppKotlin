package com.webdoc.Payment.StripePayment.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.webdoc.Payment.StripePayment.responseModel.DollerRateResponseModel

class StripeViewModel(application: Application) : AndroidViewModel(application) {
    private var case_id: String? = null
    private var user_id: String? = null
    private var amount_paid: String? = null
    private var bank: String? = null
    private var account_number: String? = null
    private var mobile_number: String? = null
    private var transection_type: String? = null
    private var transaction_reference_number: String? = null
    private var transaction_datetime: String? = null
    private var center: String? = null
    private var IBCC_amount: String? = null
    private var webdoc_amount: String? = null
    private var status: String? = null
    private var userIdEq: String? = null

    private val MLD_dollor_response_model: MutableLiveData<DollerRateResponseModel> =
        MutableLiveData<DollerRateResponseModel>()

    //todo: Live Data

/*    fun LD_getDollorRate(): LiveData<DollerRateResponseModel> {
        return MLD_dollor_response_model
    }

    fun callSavePaymentEQApi(keyUserPhone: String?, pkrRate: Double) {

        val ibccAmount = pkrRate * 140
        val webdocAmount = pkrRate * 20
        val bankCharges = pkrRate * 15
        val courierFee = pkrRate * 5
        val totalAmount = pkrRate * 180
        user_id = "Global.userLoginResponse.getResult().getCustomerProfile().getId()"
        amount_paid = totalAmount.toString()
        bank = "Stripe Payment"
        account_number = "Global.userLoginResponse.getResult().getCustomerProfile().getMobile()"
        mobile_number = "Global.userLoginResponse.getResult().getCustomerProfile().getMobile()"
        transection_type = "International Payment"
        transaction_reference_number = ""
        transaction_datetime = ""
        center = "Global.center"
        IBCC_amount = ibccAmount.toString()
        webdoc_amount = webdocAmount.toString()
        status = "Success"
        val bank_charges = bankCharges.toString()
        val courier_amount = courierFee.toString()
        val order_id = ""
        userIdEq = "Global.userLoginResponse.getResult().getCustomerProfile().getId()"
        val platform = "Android"
        callingSavePaymentForEquilance(
            case_id, amount_paid!!, bank!!, account_number,
            mobile_number, transection_type!!, transaction_reference_number!!,
            transaction_datetime!!, userIdEq, status!!, webdoc_amount!!, IBCC_amount!!,
            bank_charges, courier_amount, order_id, platform
        )

        *//*  if (Global.isFromEquivalence) {
            //call savePayment Api for Equilance
            callingSavePaymentForEquilance(case_id, amount_paid, bank, account_number,
                    mobile_number, transection_type, transaction_reference_number,
                    transaction_datetime, userIdEq, status, webdoc_amount, IBCC_amount,
                    bank_charges, courier_amount, order_id, platform);
        }*//*
    }

    private fun callingSavePaymentForEquilance(
        case_id: String?,
        amount_paid: String,
        bank: String,
        account_number: String?,
        mobile_number: String?,
        transection_type: String,
        transaction_reference_number: String,
        transaction_datetime: String,
        userIdEq: String?,
        status: String,
        webdoc_amount: String,
        ibcc_amount: String,
        bank_charges: String,
        courier_amount: String,
        order_id: String,
        platform: String
    ) {
        val requestModel = EquilanceRequestModel()
        requestModel.setCase_id(case_id)
        requestModel.setAmount_paid(amount_paid)
        requestModel.setBank(bank)
        requestModel.setAccount_number(account_number)
        requestModel.setMobile_number(mobile_number)
        requestModel.setTransection_type(transection_type)
        requestModel.setTransaction_reference_number(transaction_reference_number)
        requestModel.setTransaction_datetime(transaction_datetime)
        requestModel.setUser_id(
            java.lang.String.valueOf(
                Global.equivalenceInitiateCaseResponse.getResult().getIntiateCaseResponseDetails()
                    .getCustomerId()
            )
        )
        requestModel.setIbcC_amount(IBCC_amount)
        requestModel.setWebdoc_amount(webdoc_amount)
        requestModel.setStatus(status)
        requestModel.setBank_charges(bank_charges)
        requestModel.setCourier_amount(courier_amount)
        requestModel.setOrder_id(order_id)
        requestModel.setPlatform(platform)
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        var client: OkHttpClient? = OkHttpClient()
        client = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.MINUTES)
            .addInterceptor(interceptor)
            .build()
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://ibcc.webddocsystems.com/api/Equivalence/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client) // Set HttpClient to be used by Retrofit
            .build()
        val jsonPlaceHolderApi: jsonPlaceHolderApi = retrofit.create(jsonPlaceHolderApi::class.java)
        val call1: Call<SavePaymentInfo> =
            jsonPlaceHolderApi.savePaymentInfoforEquialance(requestModel)
        call1.enqueue(object : Callback<SavePaymentInfo> {
            override fun onResponse(
                call: Call<SavePaymentInfo>,
                response: Response<SavePaymentInfo>
            ) {
                val savePaymentInfo: SavePaymentInfo? = response.body()
                if (!response.isSuccessful()) {
                    Toast.makeText(
                        Global.applicationContext,
                        response.body().toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }
                MLD_save_payment_info_from_equivalance.postValue(savePaymentInfo)
            }

            override fun onFailure(call: Call<SavePaymentInfo>, t: Throwable) {
                Toast.makeText(
                    Global.applicationContext,
                    "onFailure called in save payment api equilance ",
                    Toast.LENGTH_SHORT
                ).show()
                Log.i("kh", t.message!!)
                call.cancel()
            }
        })
    }

    fun callDollorRateApi(activity: Activity?) {

        Global.utils!!.showCustomLoadingDialog(activity)
        val apiInterface: APIInterface = APIClient.getClient("https://openexchangerates.org/")
        val call: Call<DollerRateResponseModel> = apiInterface.callDollorRate()
        call.enqueue(object : Callback<DollerRateResponseModel?> {
            override fun onResponse(
                call: Call<DollerRateResponseModel?>,
                response: Response<DollerRateResponseModel?>
            ) {
                Global.utils!!.hideCustomLoadingDialog()
                if (response.isSuccessful()) {
                    MLD_dollor_response_model.postValue(response.body())
                } else {
                    Toast.makeText(activity, "something went wrong !", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<DollerRateResponseModel?>, t: Throwable) {
                Global.utils!!.hideCustomLoadingDialog()
                Log.i("dsd", t.message!!)
                Toast.makeText(activity, "Ooops something went wrong !", Toast.LENGTH_SHORT)
                    .show()
            }
        })

    }*/
}