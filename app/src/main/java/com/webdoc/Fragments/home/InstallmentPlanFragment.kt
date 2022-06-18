package com.webdoc.Fragments.home

import android.R
import android.content.ContentValues
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.shawnlin.numberpicker.NumberPicker
import com.webdoc.Activities.MainActivity
import com.webdoc.Essentials.Global
import com.webdoc.Payment.PaymentMethodsActivity
import com.webdoc.Payment.StripePayment.StripeActivity
import com.webdoc.theforum.databinding.FragmentInstallmentPlanBinding
import com.zhouyou.view.seekbar.SignSeekBar
import com.zhouyou.view.seekbar.SignSeekBar.OnProgressChangedListener
import java.text.DecimalFormat
import java.util.*
import kotlin.math.roundToInt


class InstallmentPlanFragment : Fragment() {
    private lateinit var binding: FragmentInstallmentPlanBinding
    private var id: Int? = null
    private var userid: String? = null
    private var description: String? = null
    private var name: String? = null
    private var tv_ins_area: String? = null
    private var tv_ins_pricePerSquareFoot: String? = null
    private var tv_ins_pricePerSquareFootDiscount: String? = null
    private var tv_ins_down_pay: String? = null
    private var tv_ins_quart_pay: String? = null
    private var tv_ins_totalAmount: String? = null
    private var tv_ins_discountedAmount: String? = null
    private var yourInstallment: String? = null
    private var abc: String? = null
    var arrayOfCities = arrayOf(
        "Select City",
        "Rawalpindi",
        "Islamabad",
        "Lahore",
        "Faisalabad",
        "Karachi",
        "Peshawar",
        "Islamabad",
        "Lahore",
        "Faisalabad",
        "Karachi",
        "Peshawar"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    companion object {
        fun newInstance(
            id: Int,
            userid: String,
            description: String,
            name: String,
            tv_ins_down_pay: String,
            tv_ins_quart_pay: String,
            tv_instotalAmount_full: String,
            tv_ins_area: String,
            tv_insdiscountedAmount_dis: String,
            tv_ins_persq_price: String,
            tv_ins_persq_discount: String
        ):
                InstallmentPlanFragment {
            val fragment = InstallmentPlanFragment()
            val args = Bundle()
            args.putInt("id", id)
            args.putString("userid", userid)
            args.putString("description", description)
            args.putString("name", name)
            args.putString("area", tv_ins_area)
            args.putString("price", tv_instotalAmount_full)
            args.putString("offer", tv_insdiscountedAmount_dis)
            args.putString("downPay", tv_ins_down_pay)
            args.putString("quartPay", tv_ins_quart_pay)
            args.putString("persqPrice", tv_ins_persq_price)
            args.putString("persqDiscount", tv_ins_persq_discount)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInstallmentPlanBinding.inflate(inflater, container, false)
        initViews()
        clicklisteners()
        return binding.root
    }

    private fun clicklisteners() {
        binding.btnInstallmentProceed.setOnClickListener {

            val intent = Intent(activity as BuyNowActivity, PaymentMethodsActivity::class.java)
            startActivity(intent)
        }

    }

    private fun initViews() {

        val args = this.arguments
        if (args != null) {
            id = args.getInt("id")
            userid = args.getString("userid").toString()
            description = args.getString("description").toString()
            name = args.getString("name").toString()
            tv_ins_totalAmount = args.getString("price").toString()
            tv_ins_area = args.getString("area").toString()
            tv_ins_discountedAmount = args.getString("offer").toString()
            tv_ins_down_pay = args.getString("downPay").toString()
            tv_ins_quart_pay = args.getString("quartPay").toString()
            tv_ins_pricePerSquareFoot = args.getString("persqPrice").toString()
            tv_ins_pricePerSquareFootDiscount = args.getString("persqDiscount").toString()


            val formatter = DecimalFormat("#,###,###")
            val totalpayformat: String = formatter.format(tv_ins_totalAmount!!.toInt())
            val discountPayFormat: String = formatter.format(tv_ins_discountedAmount!!.toInt())
            val downPayFormat: String = formatter.format(tv_ins_down_pay!!.toInt())
            val quarterPayFormat: String = formatter.format(tv_ins_quart_pay!!.toInt())
            val pricepersqPayFormat: String = formatter.format(tv_ins_pricePerSquareFoot!!.toInt())
            val pricepersqdisPayFormat: String =
                formatter.format(tv_ins_pricePerSquareFootDiscount!!.toInt())
            binding.tvInsDescription.setText(description)
            binding.tvPropInsName.setText(name)
            binding.tvInsTotalAmount.setText(totalpayformat)
            binding.tvInsArea.setText(tv_ins_area + "\nsqft")
            binding.tvInsDiscountedAmount.setText(discountPayFormat)
            binding.tvInsDownPay.setText(downPayFormat)
            binding.tvInsQuartPay.setText(quarterPayFormat)
            binding.tvInsPricePerSquareFoot.setText(pricepersqPayFormat)
            binding.tvInsPricePerSquareFootDiscount.setText(pricepersqdisPayFormat)
            test2(binding.root)

        }
        val formatter = DecimalFormat("#,###,###")
        binding.horizontNumberPicker.setOnValueChangedListener(object :
            NumberPicker.OnValueChangeListener {
            override fun onValueChange(picker: NumberPicker?, oldVal: Int, newVal: Int) {
                Log.d(
                    ContentValues.TAG,
                    java.lang.String.format(Locale.US, "oldVal: %d, newVal: %d", oldVal, newVal)
                )
//                if (newVal == 1) {
//                    binding.tvYourInstallment.visibility = View.GONE
//                    binding.btnInstallmentProceed.visibility = View.GONE
//                 //   binding.tvYourInstallment.visibility = View.GONE
//                    abc = ((newVal * tv_ins_quart_pay!!.toInt()).toString())
//                    binding.tvYourInstallment.setText("Total:\t" + abc.toString() + "/Rs")
//
//                }
           //     else
         //       {
                    val formatter = DecimalFormat("#,###,###")
//                    binding.tvYourInstallment.visibility = View.VISIBLE
//                    binding.btnInstallmentProceed.visibility = View.VISIBLE
                    abc =
                        (newVal * tv_ins_quart_pay!!.toInt() + tv_ins_down_pay!!.toInt()).toString()
                    val yourFormattedString: String = formatter.format(abc!!.toInt())
                    binding.tvYourInstallment.setText("Total:\t" + yourFormattedString + "/Rs")

                    Global.id = id!!.toInt()
                    Global.userid = userid.toString()
                    Global.installmentAmount = abc.toString()
                    Global.propertyName = name.toString()
                    Global.totalAmount = tv_ins_discountedAmount.toString()
                    Global.downPayment = tv_ins_down_pay.toString()
                    Global.sellType = "Installment"
                    Global.modeOfPayment = "Online"
                    Global.installmentNo = newVal.toString()
              //  }
            }
        })
        abc = ( tv_ins_quart_pay!!.toInt() + tv_ins_down_pay!!.toInt()).toString()
        val yourFormattedString: String = formatter.format(abc!!.toInt())
        binding.tvYourInstallment.setText("Total:\t" + yourFormattedString + "/Rs")

//        binding.seekBar.setOnProgressChangedListener(object : OnProgressChangedListener {
//            override fun onProgressChanged(
//                signSeekBar: SignSeekBar,
//                progress: Int,
//                progressFloat: Float,
//                fromUser: Boolean
//            ) {
////
//                if (progress == 0) {
//                    binding.tvYourInstallment.visibility = View.GONE
//                    binding.btnInstallmentProceed.visibility = View.GONE
//                 //   binding.tvYourInstallment.visibility = View.GONE
//                    abc = ((progress * tv_ins_quart_pay!!.toInt()).toString())
//                    binding.tvYourInstallment.setText("Total:\t" + abc.toString() + "/Rs")
//
//                } else {
//                    val formatter = DecimalFormat("#,###,###")
//                    binding.tvYourInstallment.visibility = View.VISIBLE
//                    binding.btnInstallmentProceed.visibility = View.VISIBLE
//                    abc =
//                        (progress * tv_ins_quart_pay!!.toInt() + tv_ins_down_pay!!.toInt()).toString()
//                    val yourFormattedString: String = formatter.format(abc!!.toInt())
//                    binding.tvYourInstallment.setText("Total:\t" + yourFormattedString.toString() + "/Rs")
//
//                    Global.id = id!!.toInt()
//                    Global.userid = userid.toString()
//                    Global.installmentAmount = abc.toString()
//                    Global.propertyName = name.toString()
//                    Global.totalAmount = tv_ins_discountedAmount.toString()
//                    Global.downPayment = tv_ins_down_pay.toString()
//                    Global.sellType = "Installment"
//                    Global.modeOfPayment = "Online"
//                    Global.installmentNo = progress.toString()
//                }
//
//            }
//
//            override fun getProgressOnActionUp(
//                signSeekBar: SignSeekBar,
//                progress: Int,
//                progressFloat: Float
//            ) {
//
//                val s: String = "hello"
//
//                //    binding.tvInsDes.setText(progress.toString());
//            }
//
//            override fun getProgressOnFinally(
//                signSeekBar: SignSeekBar,
//                progress: Int,
//                progressFloat: Float,
//                fromUser: Boolean
//            ) {
//
//
//                //  binding.tvInsDes.setText(progress.toString());
//            }
//        })
        //  upcomingBidPopulateRecycler()

    }
    private fun test2(view: View) {
        binding.horizontNumberPicker.setDividerColor(
            ContextCompat.getColor(
                activity as BuyNowActivity,
                com.webdoc.theforum.R.color.lightyellow
            )
        )
        binding.horizontNumberPicker.setDividerColorResource(com.webdoc.theforum.R.color.darkyellow)

// Set formatter

// Set formatter
        binding.horizontNumberPicker.setFormatter(getString(com.webdoc.theforum.R.string.number_picker_formatter))
        binding.horizontNumberPicker.setFormatter(com.webdoc.theforum.R.string.number_picker_formatter)

// Set selected text color

// Set selected text color
        binding.horizontNumberPicker.setSelectedTextColor(
            ContextCompat.getColor(
                activity as BuyNowActivity,
                com.webdoc.theforum.R.color.darkyellow
            )
        )
        binding.horizontNumberPicker.setSelectedTextColorResource(com.webdoc.theforum.R.color.darkyellow)

// Set selected text size

// Set selected text size
        binding.horizontNumberPicker.setSelectedTextSize(resources.getDimension(com.webdoc.theforum.R.dimen.selected_text_size))
        binding.horizontNumberPicker.setSelectedTextSize(com.webdoc.theforum.R.dimen.selected_text_size)

// Set selected typeface

// Set selected typeface
        binding.horizontNumberPicker.setSelectedTypeface(
            Typeface.create(
                getString(com.webdoc.theforum.R.string.roboto_light),
                Typeface.NORMAL
            )
        )
        binding.horizontNumberPicker.setSelectedTypeface(
            getString(com.webdoc.theforum.R.string.roboto_light),
            Typeface.NORMAL
        )
        binding.horizontNumberPicker.setSelectedTypeface(getString(com.webdoc.theforum.R.string.roboto_light))
        binding.horizontNumberPicker.setSelectedTypeface(com.webdoc.theforum.R.string.roboto_light, Typeface.NORMAL)
        binding.horizontNumberPicker.setSelectedTypeface(com.webdoc.theforum.R.string.roboto_light)

// Set text color

// Set text color
        binding.horizontNumberPicker.setTextColor(
            ContextCompat.getColor(
                activity as BuyNowActivity,
                com.webdoc.theforum.R.color.light_gray
            )
        )
        binding.horizontNumberPicker.setTextColorResource(com.webdoc.theforum.R.color.light_gray)

// Set text size

// Set text size
        binding.horizontNumberPicker.setTextSize(resources.getDimension(com.webdoc.theforum.R.dimen.text_size))
        binding.horizontNumberPicker.setTextSize(com.webdoc.theforum.R.dimen.text_size)

// Set typeface

// Set typeface
        binding.horizontNumberPicker.setTypeface(
            Typeface.create(
                getString(com.webdoc.theforum.R.string.roboto_light),
                Typeface.NORMAL
            )
        )
        binding.horizontNumberPicker.setTypeface(
            getString(com.webdoc.theforum.R.string.roboto_light),
            Typeface.NORMAL
        )
        binding.horizontNumberPicker.setTypeface(getString(com.webdoc.theforum.R.string.roboto_light))
        binding.horizontNumberPicker.setTypeface(com.webdoc.theforum.R.string.roboto_light, Typeface.NORMAL)
        binding.horizontNumberPicker.setTypeface(com.webdoc.theforum.R.string.roboto_light)
        val data = arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12")
       // val data = arrayOf("1st", "2nd", "3rd", "4th", "5th", "6th", "7th", "8th", "9th", "10th", "11th", "12th")


        val arraylistnew: MutableList<String> = ArrayList()

//        for (i in data) {
//            if (paidInstallment!!.toInt() < i.toInt()) {
//
//                arraylistnew.add(i)
//
//            }
//        }

        binding.horizontNumberPicker.setMinValue(1)
        binding.horizontNumberPicker.setMaxValue(data.size)
        binding.horizontNumberPicker.setDisplayedValues(data)
        binding.horizontNumberPicker.setValue(1)


        //binding.horizontNumberPicker.setDisplayedValues(arraylistnew)


        Log.i("sff", "test2: " + arraylistnew.size)


// Set fading edge enabled

// Set fading edge enabled
        binding.horizontNumberPicker.setFadingEdgeEnabled(true)

// Set scroller enabled

// Set scroller enabled
        binding.horizontNumberPicker.setScrollerEnabled(true)
// Set wrap selector wheel

// Set wrap selector wheel
        binding.horizontNumberPicker.setWrapSelectorWheel(false)

// Set accessibility description enabled

// Set accessibility description enabled
        binding.horizontNumberPicker.setAccessibilityDescriptionEnabled(true)
        
    }
//    private fun test3(view: View) {
//        // val signSeekBar = view.findViewById<View>(R.id.demo_5_seek_bar_3) as SignSeekBar
//        binding.seekBar.configBuilder
//            .min(0f)
//            .max(12f)
//            .progress(0f)
//            .sectionCount(12)
//            .thumbColor(ContextCompat.getColor(requireContext(), R.color.holo_green_light))
//            .sectionTextColor(
//                ContextCompat.getColor(
//                    requireContext(), R.color.background_light
//
//                )
//            )
//            .sectionTextSize(16)
//            .setUnit("Installment")
//            .sectionTextPosition(SignSeekBar.TextPosition.BELOW_SECTION_MARK)
//            .build()
//    }


//    private fun upcomingBidPopulateRecycler() {
//        binding.rvUpcomingBid.setLayoutManager(GridLayoutManager(activity as AppCompatActivity, 1))
//        val adapter = UpcomingBiddingAdapter(activity as AppCompatActivity)
//        binding.rvUpcomingBid.setAdapter(adapter)
//    }
}

