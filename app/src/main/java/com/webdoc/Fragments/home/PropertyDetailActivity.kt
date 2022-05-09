package com.webdoc.Fragments.home

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.telephony.PhoneNumberUtils
import android.view.*
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.webdoc.theforum.R
import com.webdoc.theforum.databinding.ActivityPropertyDetailBinding


class PropertyDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPropertyDetailBinding
    private lateinit var actionBar: ActionBar

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
        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel(R.drawable.apparmentcurrent))
        imageList.add(SlideModel(R.drawable.apparmentupcoming))
        imageList.add(SlideModel(R.drawable.apparmentcurrent))
        binding.imageSlider.setImageList(imageList, ScaleTypes.FIT)
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
        binding.ivWhatsApp.setOnClickListener {
            val phonestr = "+923443333737"
            val messagestr = "Hi"


            if (iswhatsAppInstalled()!!) {

                val sendIntent = Intent("android.intent.action.MAIN")

                sendIntent.type = "text/plain"
                sendIntent.putExtra(Intent.EXTRA_TEXT, messagestr)
                sendIntent.component = ComponentName("com.whatsapp", "com.whatsapp.Conversation")
                sendIntent.putExtra(
                    "jid",
                    PhoneNumberUtils.stripSeparators(phonestr) + "@s.whatsapp.net"
                )

                startActivity(sendIntent)
//                val i = Intent(
//                    Intent.ACTION_VIEW, Uri.parse(
//                        "https://api.whatsapp.com/send?phone=" + phonestr.toString() +
//                                "&text=" + messagestr
//                    )
//                )
//                startActivity(i)
//                val uri =
//                    Uri.parse("https://api.whatsapp.com/send?phone=" + phonestr + "&text=" + messagestr)
//
//                val sendIntent = Intent(Intent.ACTION_VIEW, uri)
//
//               startActivity(sendIntent)
//                message.setText("")
//                phone.setText("")
            } else {
                Toast.makeText(this@PropertyDetailActivity, "Whatsapp is not installed", Toast.LENGTH_SHORT)
                    .show()
            }
//            val url = "https://api.whatsapp.com/send?phone=+923306809669"
//            val i = Intent(Intent.ACTION_VIEW)
//            i.data = Uri.parse(url)
//            startActivity(i)
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
}

