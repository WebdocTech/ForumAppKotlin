package com.webdoc.Fragments.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.webdoc.Essentials.Global
import com.webdoc.Payment.PaymentMethodsActivity
import com.webdoc.theforum.R
import com.webdoc.theforum.databinding.FragmentFullPaymentBinding
import java.text.DecimalFormat

class FullPaymentFragment : Fragment() {
    private lateinit var binding: FragmentFullPaymentBinding
    private var id: Int? = null
    private var userid: String? = null
    private var description: String? = null
    private var name: String? = null
    private var amount: String? = null
    private var discountAmount: String? = null
    private var area: String? = null
    private var pricePerSqFoot: String? = null
    private var pricePerSqFootdiscount: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    companion object {
        fun newInstance(id: Int,userid: String,description: String, name: String,amount: String,
                        discountAmount: String,area: String,pricePerSqFoot: String,pricePerSqFootdiscount: String): FullPaymentFragment {
            val fragment = FullPaymentFragment()
            val args = Bundle()
            args.putInt("id", id)
            args.putString("userid", userid)
            args.putString("description", description)
            args.putString("name", name)
            args.putString("amount", amount)
            args.putString("discountAmount", discountAmount)
            args.putString("area", area)
            args.putString("pricePerSqFoot", pricePerSqFoot)
            args.putString("pricePerSqFootdiscount", pricePerSqFootdiscount)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFullPaymentBinding.inflate(inflater, container, false)
        initViews()
        clickListners()
        return binding.root
    }

    private fun clickListners() {
       binding.btnFullpaymentProceed.setOnClickListener {

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
            amount = args.getString("amount").toString()
            discountAmount = args.getString("discountAmount").toString()
            area = args.getString("area").toString()
            pricePerSqFoot = args.getString("pricePerSqFoot").toString()
            pricePerSqFootdiscount = args.getString("pricePerSqFootdiscount").toString()

            val formatter = DecimalFormat("#,###,###")
            val totalpayformat: String = formatter.format(amount!!.toInt())
            val discountpayformat: String = formatter.format(discountAmount!!.toInt())
            val persqpayformat: String = formatter.format(pricePerSqFoot!!.toInt())
            val persqdispayformat: String = formatter.format(pricePerSqFootdiscount!!.toInt())
            binding.tvDescriptionPay.setText(description)
            binding.tvProprtyName.setText(name)
            binding.tvFullTotalAmount.setText(totalpayformat)
            binding.tvFullDiscountedAmount.setText(discountpayformat)
            binding.tvFullArea.setText(area+"\nsqft")
            binding.tvFullPricePerSquareFoot.setText(persqpayformat)
            binding.tvFullPricePerSquareFootDiscount.setText(persqdispayformat)

            Global.id = id!!.toInt()
            Global.userid = userid.toString()
            Global.installmentAmount = "0"
            Global.propertyName = name.toString()
            Global.totalAmount = discountAmount.toString()
            Global.downPayment = "0"
            Global.sellType = "Full Payment"
            Global.modeOfPayment = "Online"
           // Global.ins = "Online"
        }

    }
//    private fun currentBidPopulateRecycler() {
//        binding.rvCurrentBid.setLayoutManager(GridLayoutManager(activity as AppCompatActivity, 1))
//        val adapter = CurrentBiddingAdapter(activity as AppCompatActivity)
//        binding.rvCurrentBid.setAdapter(adapter)
//    }

}