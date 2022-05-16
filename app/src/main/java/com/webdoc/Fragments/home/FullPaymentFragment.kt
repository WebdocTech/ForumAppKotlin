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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    companion object {
        fun newInstance(description: String, name: String,amount: String): FullPaymentFragment {
            val fragment = FullPaymentFragment()
            val args = Bundle()
            args.putString("description", description)
            args.putString("name", name)
            args.putString("amount", amount)
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
            binding.tvDescriptionPay.setText(description)
            binding.tvProprtyName.setText(name)
            binding.tvPropAmount.setText(amount)


        }

    }
//    private fun currentBidPopulateRecycler() {
//        binding.rvCurrentBid.setLayoutManager(GridLayoutManager(activity as AppCompatActivity, 1))
//        val adapter = CurrentBiddingAdapter(activity as AppCompatActivity)
//        binding.rvCurrentBid.setAdapter(adapter)
//    }

}