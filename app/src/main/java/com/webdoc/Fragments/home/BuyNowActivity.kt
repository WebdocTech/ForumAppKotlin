package com.webdoc.Fragments.home

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.webdoc.theforum.R
import com.webdoc.theforum.databinding.ActivityBuyNowBinding

class BuyNowActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBuyNowBinding
    private lateinit var buynowFragmentManager: FragmentManager
    private var id: String? = null
    private var name: String? = null
    private var description: String? = null
    private var pricePerSquareFoot: String? = null
    private var pricePerSquareFootDiscount: String? = null
    private var areainSquareFoot: String? = null
    private var areainlength: String? = null
    private var discountInPercent: String? = null
    private var downPayment: String? = null
    private var quarterPayment: String? = null
    private var totalAmount: String? = null
    private var discountedAmount: String? = null
    private var paymentandfloorplan: String? = null
    private var projectName: String? = null
    private var projectCompany: String? = null
    private var userid:  String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        InitViews()
        Clicklisteners()
    }

    private fun Clicklisteners() {
        binding.tvCurrentBid.setOnClickListener {
            loadFragment(FullPaymentFragment.newInstance(description!!, name!!, totalAmount!!,discountedAmount!!,areainSquareFoot!!,pricePerSquareFoot!!,pricePerSquareFootDiscount!!))

            binding.viewbid3.visibility = View.VISIBLE
            binding.viewbid4.visibility = View.GONE

            binding.tvCurrentBid.setTextColor(Color.YELLOW)
            binding.tvUpcomingBid.setTextColor(Color.WHITE)

        }
        binding.tvUpcomingBid.setOnClickListener {
            loadFragment(InstallmentPlanFragment.newInstance(description!!,name!!,downPayment!!,quarterPayment!!,totalAmount!!,areainSquareFoot!!,discountedAmount!!,
                pricePerSquareFoot!!,pricePerSquareFootDiscount!!))
            binding.viewbid3.visibility = View.GONE
            binding.viewbid4.visibility = View.VISIBLE

            binding.tvCurrentBid.setTextColor(Color.WHITE)
            binding.tvUpcomingBid.setTextColor(Color.YELLOW)
        }
    }

    private fun InitViews() {
        binding = ActivityBuyNowBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        buynowFragmentManager = supportFragmentManager

        val intent = intent
        id = intent.getStringExtra("id")
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
        userid = intent.getStringExtra("userid")
        loadFragment(FullPaymentFragment.newInstance(description!!, name!!,totalAmount!!, discountedAmount!!,areainSquareFoot!!,pricePerSquareFoot!!,pricePerSquareFootDiscount!!))

        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel(R.drawable.apparmentcurrent))
        imageList.add(SlideModel(R.drawable.apparmentupcoming))
        imageList.add(SlideModel(R.drawable.apparmentcurrent))
        binding.imgSliderBuy.setImageList(imageList, ScaleTypes.FIT)
    }

    private fun loadFragment(fragment: Fragment) {
        // load fragment
        buynowFragmentManager.beginTransaction()
            .replace(R.id.subFragmentContainer, fragment, fragment.javaClass.name).commit()

    }
}