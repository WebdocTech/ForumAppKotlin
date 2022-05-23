package com.webdoc.Fragments.myproperty

import android.Manifest
import android.app.DownloadManager
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.webkit.CookieManager
import android.webkit.URLUtil
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.webdoc.Activities.MainActivity
import com.webdoc.Adapters.MyPropertyPaymentAdapter
import com.webdoc.ApiResponseModels.MyPropertyResponse.MyPropertyResponse
import com.webdoc.ApiResponseModels.MyPropertyResponse.PaymentDetail
import com.webdoc.Essentials.Global
import com.webdoc.theforum.R
import com.webdoc.theforum.databinding.ActivityMyPropDetailBinding
import java.text.DecimalFormat

class MyPropDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyPropDetailBinding
    private var id: String? = null
    private var positionn: String? = null
    private var name: String? = null
    private var description: String? = null
    private var pricePerSquareFoot: String? = null
    private var pricePerSquareFootDiscount: String? = null
    private var areainSquareFoot: String? = null
    private var areainlength: String? = null
    private var paymentandfloorplan: String? = null
    private var projectName: String? = null
    private var projectCompany: String? = null
    private var purchaseDate: String? = null
    private var nextPaymentDate: String? = null
    private var sellType: String? = null
    private var status: String? = null
    private var paymentStatus: String? = null
    private var totalPayment: String? = null
    private var totalInstallment: String? = null
    private var paidInstallment: String? = null
    private var remainingInstallment: String? = null
    private val WRITE_PERMISSION = 4
    private var myPropertyResponse: MyPropertyResponse? = null

    private var paymentFormat: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        clickListeners()
    }


    private fun initViews() {
        binding = ActivityMyPropDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel(R.drawable.apparmentcurrent))
        imageList.add(SlideModel(R.drawable.apparmentupcoming))
        imageList.add(SlideModel(R.drawable.apparmentcurrent))
        binding.myPropImgSlider.setImageList(imageList, ScaleTypes.FIT)

        val intent = intent

        id = intent.getStringExtra("id")
        name = intent.getStringExtra("name")
        description = intent.getStringExtra("description")
        pricePerSquareFoot = intent.getStringExtra("pricePerSquareFoot")
        pricePerSquareFootDiscount = intent.getStringExtra("pricePerSquareFootDiscount")
        areainSquareFoot = intent.getStringExtra("areainSquareFoot")
        areainlength = intent.getStringExtra("areainlength")
        paymentandfloorplan = intent.getStringExtra("paymentandfloorplan")
        projectName = intent.getStringExtra("projectName")
        projectCompany = intent.getStringExtra("projectCompany")
        purchaseDate = intent.getStringExtra("purchaseDate")
        nextPaymentDate = intent.getStringExtra("nextPaymentDate")
        sellType = intent.getStringExtra("sellType")
        status = intent.getStringExtra("status")
        paymentStatus = intent.getStringExtra("paymentStatus")
        totalPayment = intent.getStringExtra("totalPayment")
        totalInstallment = intent.getStringExtra("totalInstallment")
        paidInstallment = intent.getStringExtra("paidInstallment")
        remainingInstallment = intent.getStringExtra("remainingInstallment")


        binding.tvMyPropArea.setText(areainSquareFoot + "sq")
        val formatter = DecimalFormat("#,###,###")
        val yourFormattedString: String = formatter.format(totalPayment!!.toInt())
        binding.tvMyPropPrice.setText(yourFormattedString)
        binding.tvMyPropDescription.setText(description)
        binding.tvMyPropName.setText(name)


        var array: ArrayList<PaymentDetail> = ArrayList()


        /*
               for (i in Global.mypropResp.result.myPropertyDetails) {
                   array.add(Global.mypropResp.result.myPropertyDetails.get(i).paymentDetails.get(positionn).debit)
                  *//* for (j in i.paymentDetails) {

                val id = j.id
                val debit = j.debit
                val credit = j.credit
                val modeOfPayment = j.modeOfPayment
                val transectionId = j.transectionId
                val addedBy = j.addedBy
                val date = j.date
                val remarks = j.remarks
                val balance = j.balance
                //Log.i("ds", namee)
            }*//*
        }*/

        val layoutManager = LinearLayoutManager(this@MyPropDetailActivity)
        binding.rvPayDetails.setLayoutManager(layoutManager)
        val adapter = MyPropertyPaymentAdapter(this@MyPropDetailActivity, Global.mypropResp!!)
        binding.rvPayDetails.setHasFixedSize(true)
        binding.rvPayDetails.setAdapter(adapter)

    }

    private fun startDownloaing() {
        val request = DownloadManager.Request(Uri.parse(paymentandfloorplan))
        val title = URLUtil.guessFileName(paymentandfloorplan, null, null)
        request.setTitle(projectName + "\tPlan")
        request.setDescription("Downloading file please wait...")
        val cookie = CookieManager.getInstance().getCookie(paymentandfloorplan)
        request.addRequestHeader("cookie", cookie)
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, title)
        val downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        downloadManager.enqueue(request)
        Toast.makeText(this@MyPropDetailActivity, "Downloading Start...", Toast.LENGTH_SHORT)
            .show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            WRITE_PERMISSION -> if (grantResults.size > 0) {
                val READ_EXTERNAL_STORAGE = grantResults[0] == PackageManager.PERMISSION_GRANTED
                val WRITE_EXTERNAL_STORAGE = grantResults[1] == PackageManager.PERMISSION_GRANTED
                if (READ_EXTERNAL_STORAGE && WRITE_EXTERNAL_STORAGE) {
                    // perform action when allow permission success
                } else {

                    Toast.makeText(this, "Allow permission for storage access!", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun clickListeners() {
        binding.clMyPropNavigate.setOnClickListener {
//            val uri: String =
//                java.lang.String.format(Locale.ENGLISH, "geo:%f,%f", 33.52811569901712, 73.11350612580407)
//            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
//            startActivity(intent)

            val navigationIntentUri =
                Uri.parse("google.navigation:q=" + 33.54866653953476 + "," + 73.12450943347335)
            val mapIntent = Intent(Intent.ACTION_VIEW, navigationIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }
        binding.clMyPropPlan.setOnClickListener {
            try {
                if (ContextCompat.checkSelfPermission(
                        applicationContext,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(
                            arrayOf(
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE
                            ), WRITE_PERMISSION
                        )
                    }
                } else {
                    startDownloaing()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}