package com.webdoc.Fragments.home

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.DownloadManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Environment
import android.telephony.PhoneNumberUtils
import android.util.Log
import android.view.*
import android.webkit.CookieManager
import android.webkit.URLUtil
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.webdoc.Essentials.PreferencesNew
import com.webdoc.theforum.R
import com.webdoc.theforum.databinding.ActivityPropertyDetailBinding
import java.text.DecimalFormat
import java.util.*
import kotlin.collections.ArrayList


class PropertyDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPropertyDetailBinding
    private lateinit var actionBar: ActionBar
    private var propid: Int? = null
    var name: String? = null
    var description: String? = null
    var pricePerSquareFoot: String? = null
    var pricePerSquareFootDiscount: String? = null
    var areainSquareFoot: String? = null
    var areainlength: String? = null
    var discountInPercent: String? = null
    var downPayment: String? = null
    var quarterPayment: String? = null
    var totalAmount: String? = null
    var discountedAmount: String? = null
    var paymentandfloorplan: String? = null
    var projectName: String? = null
    var projectCompany: String? = null
    var status: String? = null
    private val WRITE_PERMISSION = 3
    private var userid: String = ""
    lateinit var prefs: SharedPreferences
    lateinit var edit: SharedPreferences.Editor

    // var count = 1
    var imagesCounter = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        InitViews()
        Clicklisteners()
    }

    private fun InitViews() {
        binding = ActivityPropertyDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        actionBar = supportActionBar!!
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar()?.setDisplayShowTitleEnabled(false);
//        if(checkPermission()){
//            requestPermission()
//        }

        val intent = intent
        propid = intent.getIntExtra("id",0)
        name = intent.getStringExtra("name")
        description = intent.getStringExtra("description")
        pricePerSquareFoot = intent.getStringExtra("pricePerSquareFoot")
        pricePerSquareFootDiscount = intent.getStringExtra("pricePerSquareFootDiscount")
        areainSquareFoot = intent.getStringExtra("areainSquareFoot")
        areainlength = intent.getStringExtra("areainlength")
        discountInPercent = intent.getStringExtra("discountInPercent")
        downPayment = intent.getStringExtra("downPayment")
        quarterPayment = intent.getStringExtra("quarterPayment")
        totalAmount = intent.getStringExtra("totalAmount")
        discountedAmount = intent.getStringExtra("discountedAmount")
        paymentandfloorplan = intent.getStringExtra("paymentandfloorplan")
        projectName = intent.getStringExtra("projectName")
        projectCompany = intent.getStringExtra("projectCompany")
        status = intent.getStringExtra("status")

        prefs = getSharedPreferences("abc", Context.MODE_PRIVATE)
        edit = prefs.edit()

        userid = prefs.getString(PreferencesNew.KEY_ApplicationUserId, "").toString()
        val formatter = DecimalFormat("#,###,###")
        val totalpayformat: String = formatter.format(discountedAmount!!.toInt())

        binding.tvArea.setText(areainSquareFoot + "/sq")
        binding.tvPrice.setText("Rs:"+totalpayformat + "/-")
        binding.tvDescription.setText(description)
        binding.tvAreainlength.setText(areainlength)
        binding.tvProjectCompany.setText(projectCompany)
        binding.tvProjectName.setText(projectName)
        binding.tvStatus.setText(status)


        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel(R.drawable.apparmentcurrent))
        imageList.add(SlideModel(R.drawable.apparmentupcoming))
        imageList.add(SlideModel(R.drawable.apparmentcurrent))
        binding.imageSlider.setImageList(imageList, ScaleTypes.FIT)
        if(status.equals("Partial Sold")){
         //   binding.clBuy.visibility=View.GONE
        }
        else if(status.equals("Availabe")){
          //  binding.clBuy.visibility=View.VISIBLE
        }
        //  val adapterView = ImageAdapter(this)
        // binding.vpPropertyImg.adapter = adapterView
        //setImage(imagesCounter)
    }

    private fun Clicklisteners() {
//        binding.ivRightArrow.setOnClickListener {
//            if (imagesCounter <= 3) {
//                imagesCounter++
//                setImage(imagesCounter)
//            }
//        }

//        binding.ivLeftArrow.setOnClickListener {
//            if (imagesCounter > 1) {
//                imagesCounter--
//                setImage(imagesCounter)
//            }
//        }
        binding.clWhatsapp.setOnClickListener {
            val phonestr = "+923443333737"
            val messagestr = "Hi"

            if (iswhatsAppInstalled()!!) {
                val i = Intent(
                    Intent.ACTION_VIEW, Uri.parse(
                        "https://api.whatsapp.com/send?phone=" + phonestr.toString() +
                                "&text=" + messagestr
                    )
                )
                startActivity(i)
            } else {
                Toast.makeText(
                    this@PropertyDetailActivity,
                    "Whatsapp is not installed",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }

        binding.clNavigate.setOnClickListener {
//            val uri: String =
//                java.lang.String.format(Locale.ENGLISH, "geo:%f,%f", 33.52811569901712, 73.11350612580407)
//            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
//            startActivity(intent)
           // http://maps.google.com/maps?q=loc:%f,%f
           // google.navigation:q=
//            val navigationIntentUri =
//                Uri.parse("google.navigation:q=" + 33.54866653953476 + "," + 73.12450943347335)
            val uri: String =
                java.lang.String.format("http://maps.google.com/maps?daddr=  33.54874003625339,73.12495291094207")
            val mapIntent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }
        binding.clPlan.setOnClickListener {
            try {
                if (checkSelfPermission(
                        applicationContext,
                        READ_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    if (SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(
                            arrayOf(
                                WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE
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
        binding.ivContact.setOnClickListener {

            val u: Uri = Uri.parse("tel:" + "+923443333737")
            val i = Intent(Intent.ACTION_DIAL, u)

            try {
                startActivity(i)
            } catch (s: SecurityException) {
                Toast.makeText(this, "An error occurred", Toast.LENGTH_LONG)
                    .show()
            }
        }
        binding.btnBuyNow.setOnClickListener {

            val intent = Intent(this, BuyNowActivity::class.java)
            intent.putExtra("id", propid)
            intent.putExtra("name", name)
            intent.putExtra("description", description)
            intent.putExtra("pricePerSquareFoot", pricePerSquareFoot)
            intent.putExtra("pricePerSquareFootDiscount", pricePerSquareFootDiscount)
            intent.putExtra("areainSquareFoot", areainSquareFoot)
            intent.putExtra("areainlength", areainlength)
            intent.putExtra("discountInPercent", discountInPercent)
            intent.putExtra("downPayment", downPayment)
            intent.putExtra("quarterPayment", quarterPayment)
            intent.putExtra("totalAmount", totalAmount)
            intent.putExtra("discountedAmount", discountedAmount)
            intent.putExtra("paymentandfloorplan", paymentandfloorplan)
            intent.putExtra("projectName", projectName)
            intent.putExtra("projectCompany", projectCompany)
            intent.putExtra("userid", userid)
            startActivity(intent)

        }
    }

//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        when (requestCode) {
//            WRITE_PERMISSION -> {
//                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_DENIED) {
//
//                } else {
//                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_LONG).show()
//                }
//            }
//        }
//    }

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
        Toast.makeText(this@PropertyDetailActivity, "Downloading Start...", Toast.LENGTH_SHORT)
            .show()
    }

    //    private fun checkPermission(): Boolean {
//        return if (SDK_INT >= Build.VERSION_CODES.R) {
//            Environment.isExternalStorageManager()
//        } else {
//            val result =
//                checkSelfPermission(this@PropertyDetailActivity, READ_EXTERNAL_STORAGE)
//            val result1 =
//                checkSelfPermission(this@PropertyDetailActivity, WRITE_EXTERNAL_STORAGE)
//            result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED
//        }
//    }
//    private fun requestPermission() {
//        if (SDK_INT >= Build.VERSION_CODES.R) {
//            try {
//                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
//                intent.addCategory("android.intent.category.DEFAULT")
//                intent.data =
//                    Uri.parse(String.format("package:%s", applicationContext.packageName))
//                startActivityForResult(intent, 2296)
//            } catch (e: java.lang.Exception) {
//                val intent = Intent()
//                intent.action = Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
//                startActivityForResult(intent, 2296)
//            }
//        } else {
//            //below android 11
//            ActivityCompat.requestPermissions(
//                this@PropertyDetailActivity,
//                arrayOf(WRITE_EXTERNAL_STORAGE),
//                WRITE_PERMISSION
//            )
//        }
//    }
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
//    private fun setImage(imagesCounter: Int) {
//        if (imagesCounter == 1) {
//            binding.ivPropertyImg.setImageResource(R.drawable.apparmentcurrent)
//        } else if (imagesCounter == 2) {
//            binding.ivPropertyImg.setImageResource(R.drawable.apparmentupcoming)
//        } else if (imagesCounter == 3) {
//            binding.ivPropertyImg.setImageResource(R.drawable.apparmentcurrent)
//        }
//    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.action_bar_menu_favorite, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.notification -> {

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    class ImageAdapter internal constructor(context: Context) : PagerAdapter() {
        var mContext: Context
        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view === `object` as ImageView
        }

        private val sliderImageId = intArrayOf(
            R.drawable.apparmentcurrent,
            R.drawable.apparmentupcoming,
            R.drawable.apparmentcurrent
        )

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val imageView = ImageView(mContext)
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP)
            imageView.setImageResource(sliderImageId[position])
            (container as ViewPager).addView(imageView, 0)
            return imageView
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            (container as ViewPager).removeView(`object` as ImageView)
        }

        override fun getCount(): Int {
            return sliderImageId.size
        }

        init {
            mContext = context
        }
    }

    private fun iswhatsAppInstalled(): Boolean? {
        val packageManager: PackageManager = applicationContext.getPackageManager()
        val whatappisntalled: Boolean
        whatappisntalled = try {
            packageManager.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
        return whatappisntalled
    }

    private fun isGoogleMapINstalled(): Boolean {
        val packageManager: PackageManager = applicationContext.getPackageManager()
        val googlemapisntalled: Boolean
        googlemapisntalled = try {
            packageManager.getPackageInfo("com.google.android.gms.maps ", 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
        return googlemapisntalled
    }
}


