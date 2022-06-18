package com.webdoc.Fragments.myproperty

import android.Manifest
import android.app.DownloadManager
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Typeface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.webkit.CookieManager
import android.webkit.URLUtil
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.shawnlin.numberpicker.NumberPicker
import com.webdoc.Adapters.MyPropertyPaymentAdapter
import com.webdoc.ApiResponseModels.MyPropertyResponse.MyPropertyResponse
import com.webdoc.Essentials.Global
import com.webdoc.Essentials.PreferencesNew
import com.webdoc.theforum.R
import com.webdoc.theforum.databinding.ActivityMyPropDetailBinding
import java.text.DecimalFormat
import java.util.*
import kotlin.collections.ArrayList


class MyPropDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityMyPropDetailBinding
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
    private var totalRemaningAmount: String? = null
    private var installmentAmount: String? = null
    private var totalPaidAmount: String? = null
    private val WRITE_PERMISSION = 4

    private var myPropertyResponse: MyPropertyResponse? = null
    private var yourInstallment: String? = null
    private var paymentFormat: String? = null
    private var userid: String? = null
    private lateinit var prefs: SharedPreferences
    private lateinit var edit: SharedPreferences.Editor
    private var currentposition = 0
    var ischecked: Boolean = false
    var arrayOfinstalment = arrayOf(
        "1",
        "2",
        "3",
        "4",
        "5",
        "6",
        "7",
        "8",
        "9",
        "10",
        "11",
        "12"
    )

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
        prefs = getSharedPreferences("abc", Context.MODE_PRIVATE)
        edit = prefs.edit()
        userid = prefs.getString(PreferencesNew.KEY_ApplicationUserId, "").toString()
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
        //  paidInstallmentNEw = paidInstallment!! + 1
        remainingInstallment = intent.getStringExtra("remainingInstallment")
        totalRemaningAmount = intent.getStringExtra("totalRemaningAmount")
        installmentAmount = intent.getStringExtra("installmentAmount")
        totalPaidAmount = intent.getStringExtra("totalPaidAmount")


        binding.tvMyPropArea.setText(areainSquareFoot + "sq")
        val formatter = DecimalFormat("#,###,###")
        val yourFormattedString: String = formatter.format(totalPayment!!.toInt())
        binding.tvMyPropPrice.setText("Rs:" + yourFormattedString + "/-")
        binding.tvMyPropDescription.setText(description)
        binding.tvMyPropName.setText(name)
        test2(binding.root)


        if (sellType.equals("Installment")) {
            binding.clInst.visibility = View.VISIBLE
        } else if (sellType.equals("Full Payment")) {
            binding.clInst.visibility = View.GONE
         //   binding.tvMyInstallment.setText("Property Sold in\t" + "Rs:" + yourFormattedString + "/-")
        }

        val layoutManager = LinearLayoutManager(this@MyPropDetailActivity)
        binding.rvPayDetails.setLayoutManager(layoutManager)
        val adapter = MyPropertyPaymentAdapter(this@MyPropDetailActivity, Global.mypropResp!!)
        binding.rvPayDetails.setHasFixedSize(true)
        binding.rvPayDetails.setAdapter(adapter)
        binding.horizontalNumberPicker.setOnValueChangedListener(object :
            NumberPicker.OnValueChangeListener {
            override fun onValueChange(picker: NumberPicker?, oldVal: Int, newVal: Int) {
                Log.d(
                    TAG,
                    java.lang.String.format(Locale.US, "oldVal: %d, newVal: %d", oldVal, newVal)
                )
                yourInstallment = (newVal * installmentAmount!!.toInt()).toString()
                val yourinstalpayformat: String =
                    formatter.format(yourInstallment!!.toInt())
                binding.tvMyInstallment.setText("Rs:"+yourinstalpayformat+"/-")
            }
        })


        yourInstallment = (installmentAmount!!.toInt()).toString()

        val yourinstalpayformat: String =
            formatter.format(yourInstallment!!.toInt())
        binding.tvMyInstallment.setText("Rs:"+yourinstalpayformat+"/-")


    }

    private fun test2(view: View) {
        binding.horizontalNumberPicker.setDividerColor(
            ContextCompat.getColor(
                this,
                R.color.lightyellow
            )
        )
        binding.horizontalNumberPicker.setDividerColorResource(R.color.darkyellow)

// Set formatter

// Set formatter
        binding.horizontalNumberPicker.setFormatter(getString(R.string.number_picker_formatter))
        binding.horizontalNumberPicker.setFormatter(R.string.number_picker_formatter)

// Set selected text color

// Set selected text color
        binding.horizontalNumberPicker.setSelectedTextColor(
            ContextCompat.getColor(
                this,
                R.color.darkyellow
            )
        )
        binding.horizontalNumberPicker.setSelectedTextColorResource(R.color.darkyellow)

// Set selected text size

// Set selected text size
        binding.horizontalNumberPicker.setSelectedTextSize(resources.getDimension(R.dimen.selected_text_size))
        binding.horizontalNumberPicker.setSelectedTextSize(R.dimen.selected_text_size)

// Set selected typeface

// Set selected typeface
        binding.horizontalNumberPicker.setSelectedTypeface(
            Typeface.create(
                getString(R.string.roboto_light),
                Typeface.NORMAL
            )
        )
        binding.horizontalNumberPicker.setSelectedTypeface(
            getString(R.string.roboto_light),
            Typeface.NORMAL
        )
        binding.horizontalNumberPicker.setSelectedTypeface(getString(R.string.roboto_light))
        binding.horizontalNumberPicker.setSelectedTypeface(R.string.roboto_light, Typeface.NORMAL)
        binding.horizontalNumberPicker.setSelectedTypeface(R.string.roboto_light)

// Set text color

// Set text color
        binding.horizontalNumberPicker.setTextColor(
            ContextCompat.getColor(
                this,
                R.color.light_gray
            )
        )
        binding.horizontalNumberPicker.setTextColorResource(R.color.light_gray)

// Set text size

// Set text size
        binding.horizontalNumberPicker.setTextSize(resources.getDimension(R.dimen.text_size))
        binding.horizontalNumberPicker.setTextSize(R.dimen.text_size)

// Set typeface

// Set typeface
        binding.horizontalNumberPicker.setTypeface(
            Typeface.create(
                getString(R.string.roboto_light),
                Typeface.NORMAL
            )
        )
        binding.horizontalNumberPicker.setTypeface(
            getString(R.string.roboto_light),
            Typeface.NORMAL
        )
        binding.horizontalNumberPicker.setTypeface(getString(R.string.roboto_light))
        binding.horizontalNumberPicker.setTypeface(R.string.roboto_light, Typeface.NORMAL)
        binding.horizontalNumberPicker.setTypeface(R.string.roboto_light)

// Set value

// Set value
//        binding.horizontalNumberPicker.setMaxValue(59)
//        binding.horizontalNumberPicker.setMinValue(0)
//        binding.horizontalNumberPicker.setValue(3)

// Using string values
// IMPORTANT! setMinValue to 1 and call setDisplayedValues after setMinValue and setMaxValue

// Using string values
// IMPORTANT! setMinValue to 1 and call setDisplayedValues after setMinValue and setMaxValue
        val data = arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12")
      //  val data = arrayOf("1st", "2nd", "3rd", "4th", "5th", "6th", "7th", "8th", "9th", "10th", "11th", "12th")
//        val numlist: MutableList<String> = ArrayList()
//        for (i in 0 until data.size) {
//            if (paidInstallment!!.toInt() < data.get(i).toInt()) {
//                // No operation here
//            } else {
//                numlist.add(data.get(i))
//            }
//        }
//        data = numlist.toTypedArray()
        val arraylistnew: ArrayList<String> = ArrayList()

        for (i in data) {
            if (paidInstallment!!.toInt() < i.toInt()) {

                arraylistnew.add(i)

            }
        }

        binding.horizontalNumberPicker.setMinValue(1)
        binding.horizontalNumberPicker.setMaxValue(arraylistnew.size)
        binding.horizontalNumberPicker.setDisplayedValues(arraylistnew.toTypedArray())
        binding.horizontalNumberPicker.setValue(1)


        //binding.horizontalNumberPicker.setDisplayedValues(arraylistnew)


        Log.i("sff", "test2: " + arraylistnew.size)


// Set fading edge enabled

// Set fading edge enabled
        binding.horizontalNumberPicker.setFadingEdgeEnabled(true)

// Set scroller enabled

// Set scroller enabled
        binding.horizontalNumberPicker.setScrollerEnabled(true)
// Set wrap selector wheel

// Set wrap selector wheel
        binding.horizontalNumberPicker.setWrapSelectorWheel(false)

// Set accessibility description enabled

// Set accessibility description enabled
        binding.horizontalNumberPicker.setAccessibilityDescriptionEnabled(true)


//        binding.horizontalNumberPicker.setOnScrollListener(NumberPicker.OnScrollListener { picker, scrollState ->
//            if (scrollState == NumberPicker.OnScrollListener.SCROLL_STATE_IDLE) {
//                Log.d(TAG, java.lang.String.format(Locale.US, "newVal: %d", picker.value))
//
//            }
//        })


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

        binding.tvMyInstallment.setOnClickListener {
            //   test3(binding.root, paidInstallment, ischecked)
            //  binding.mySeekBar.isEnabled = true
            //  binding.mySeekBar.setProgress(paidInstallment!!.toFloat())
            //    binding.mySeekBar.configBuilder.progress(paidInstallment!!.toFloat())
        }
        binding.clMyPropNavigate.setOnClickListener {
//            val uri: String =
//                java.lang.String.format(Locale.ENGLISH, "geo:%f,%f", 33.52811569901712, 73.11350612580407)
//            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
//            startActivity(intent)

//            val navigationIntentUri =
//                Uri.parse("google.navigation:q=" + 33.54866653953476 + "," + 73.12450943347335)

            val uri: String =
                java.lang.String.format("http://maps.google.com/maps?daddr=33.54880707885513, 73.12449156826311")
            val mapIntent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
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

