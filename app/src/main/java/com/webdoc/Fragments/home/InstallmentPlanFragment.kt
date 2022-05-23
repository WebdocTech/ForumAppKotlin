package com.webdoc.Fragments.home

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.webdoc.theforum.databinding.FragmentInstallmentPlanBinding
import com.zhouyou.view.seekbar.SignSeekBar
import com.zhouyou.view.seekbar.SignSeekBar.OnProgressChangedListener
import java.text.DecimalFormat
import java.util.*
import kotlin.math.roundToInt


class InstallmentPlanFragment : Fragment() {
    private lateinit var binding: FragmentInstallmentPlanBinding
    private var description: String? = null
    private var name: String? = null
    private var tv_ins_area: String? = null
    private var tv_ins_pricePerSquareFoot: String? = null
    private var tv_ins_pricePerSquareFootDiscount: String? = null
    private var tv_ins_down_pay: String? = null
    private var tv_ins_quart_pay: String? = null
    private var tv_ins_totalAmount: String? = null
    private var tv_ins_discountedAmount: String? = null
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
        return binding.root
    }

    private fun initViews() {

        val args = this.arguments
        if (args != null) {
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
            val pricepersqdisPayFormat: String = formatter.format(tv_ins_pricePerSquareFootDiscount!!.toInt())
            binding.tvInsDescription.setText(description)
            binding.tvPropInsName.setText(name)
            binding.tvInsTotalAmount.setText(totalpayformat)
            binding.tvInsArea.setText(tv_ins_area + "\nsqft")
            binding.tvInsDiscountedAmount.setText(discountPayFormat)
            binding.tvInsDownPay.setText(downPayFormat)
            binding.tvInsQuartPay.setText(quarterPayFormat)
            binding.tvInsPricePerSquareFoot.setText(pricepersqPayFormat)
            binding.tvInsPricePerSquareFootDiscount.setText(pricepersqdisPayFormat)
            test3(binding.root)
        }

        binding.seekBar.setOnProgressChangedListener(object : OnProgressChangedListener {
            override fun onProgressChanged(
                signSeekBar: SignSeekBar,
                progress: Int,
                progressFloat: Float,
                fromUser: Boolean
            ) {
//

                if (progress == 0) {
                    binding.tvYourInstallment.visibility = View.GONE
                    // binding.tvYourInstallment.visibility = View.VISIBLE
                    abc = ((progress * tv_ins_quart_pay!!.toInt()).toString())
                    binding.tvYourInstallment.setText("Total:\t" + abc.toString() + "/Rs")
                } else {
                    val formatter = DecimalFormat("#,###,###")
                    binding.tvYourInstallment.visibility = View.VISIBLE
                    abc =
                        (progress * tv_ins_quart_pay!!.toInt() + tv_ins_down_pay!!.toInt()).toString()
                    val yourFormattedString: String = formatter.format(abc!!.toInt())
                    binding.tvYourInstallment.setText("Total:\t" + yourFormattedString.toString() + "/Rs")
                }

            }

            override fun getProgressOnActionUp(
                signSeekBar: SignSeekBar,
                progress: Int,
                progressFloat: Float
            ) {

                val s: String = "hello"

                //    binding.tvInsDes.setText(progress.toString());
            }

            override fun getProgressOnFinally(
                signSeekBar: SignSeekBar,
                progress: Int,
                progressFloat: Float,
                fromUser: Boolean
            ) {


                //  binding.tvInsDes.setText(progress.toString());
            }
        })
        //  upcomingBidPopulateRecycler()

    }

    private fun test3(view: View) {
        // val signSeekBar = view.findViewById<View>(R.id.demo_5_seek_bar_3) as SignSeekBar
        binding.seekBar.configBuilder
            .min(0f)
            .max(12f)
            .progress(0f)
            .sectionCount(12)
            .thumbColor(ContextCompat.getColor(requireContext(), R.color.holo_green_light))
            .sectionTextColor(
                ContextCompat.getColor(
                    requireContext(), R.color.background_light

                )
            )
            .sectionTextSize(16)
            .setUnit("Installment")
            .sectionTextPosition(SignSeekBar.TextPosition.BELOW_SECTION_MARK)
            .build()
    }


//    private fun upcomingBidPopulateRecycler() {
//        binding.rvUpcomingBid.setLayoutManager(GridLayoutManager(activity as AppCompatActivity, 1))
//        val adapter = UpcomingBiddingAdapter(activity as AppCompatActivity)
//        binding.rvUpcomingBid.setAdapter(adapter)
//    }
}

