package com.webdoc.Fragments.home

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.webdoc.Activities.MainActivity
import com.webdoc.theforum.databinding.FragmentInstallmentPlanBinding
import com.zhouyou.view.seekbar.SignSeekBar


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

            binding.tvInsDescription.setText(description)
            binding.tvPropInsName.setText(name)
            binding.tvInsTotalAmount.setText(tv_ins_totalAmount)
            binding.tvInsArea.setText(tv_ins_area + "\nsqft")
            binding.tvInsDiscountedAmount.setText(tv_ins_discountedAmount)
            binding.tvInsDownPay.setText(tv_ins_down_pay)
            binding.tvInsQuartPay.setText(tv_ins_quart_pay)
            binding.tvInsPricePerSquareFoot.setText(tv_ins_pricePerSquareFoot)
            binding.tvInsPricePerSquareFootDiscount.setText(tv_ins_pricePerSquareFootDiscount)
            test3(binding.root)

        }


        //  upcomingBidPopulateRecycler()
    }

    private fun test3(view: View) {
        // val signSeekBar = view.findViewById<View>(R.id.demo_5_seek_bar_3) as SignSeekBar
        binding.seekBar.configBuilder
            .min(0f)
            .max(4f)
            .progress(2f)
            .sectionCount(4)
            .thumbColor(ContextCompat.getColor(requireContext(), R.color.holo_orange_light))
            .sectionTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.holo_blue_bright
                )
            )
            .sectionTextSize(16)
            .setUnit("s")
            .sectionTextPosition(SignSeekBar.TextPosition.BELOW_SECTION_MARK)
            .build()
    }

//    private fun upcomingBidPopulateRecycler() {
//        binding.rvUpcomingBid.setLayoutManager(GridLayoutManager(activity as AppCompatActivity, 1))
//        val adapter = UpcomingBiddingAdapter(activity as AppCompatActivity)
//        binding.rvUpcomingBid.setAdapter(adapter)
//    }
}