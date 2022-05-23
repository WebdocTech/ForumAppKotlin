package com.webdoc.Fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.webdoc.theforum.R
import com.webdoc.theforum.databinding.FragmentFullPaymentBinding

class FullPaymentFragment : Fragment() {
    private lateinit var binding: FragmentFullPaymentBinding
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
        fun newInstance(description: String, name: String,amount: String,
                        discountAmount: String,area: String,pricePerSqFoot: String,pricePerSqFootdiscount: String): FullPaymentFragment {
            val fragment = FullPaymentFragment()
            val args = Bundle()
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
        return binding.root
    }

    private fun initViews() {
        val args = this.arguments
        if (args != null) {
            description = args.getString("description").toString()
            name = args.getString("name").toString()
            amount = args.getString("amount").toString()
            discountAmount = args.getString("discountAmount").toString()
            area = args.getString("area").toString()
            pricePerSqFoot = args.getString("pricePerSqFoot").toString()
            pricePerSqFootdiscount = args.getString("pricePerSqFootdiscount").toString()
            binding.tvDescriptionPay.setText(description)
            binding.tvProprtyName.setText(name)
            binding.tvFullTotalAmount.setText(amount)
            binding.tvFullDiscountedAmount.setText(discountAmount)
            binding.tvFullArea.setText(area+"\nsqft")
            binding.tvFullPricePerSquareFoot.setText(pricePerSqFoot)
            binding.tvFullPricePerSquareFootDiscount.setText(pricePerSqFootdiscount)


        }

    }
//    private fun currentBidPopulateRecycler() {
//        binding.rvCurrentBid.setLayoutManager(GridLayoutManager(activity as AppCompatActivity, 1))
//        val adapter = CurrentBiddingAdapter(activity as AppCompatActivity)
//        binding.rvCurrentBid.setAdapter(adapter)
//    }

}