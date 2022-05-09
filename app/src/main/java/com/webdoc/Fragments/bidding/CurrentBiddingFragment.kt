package com.webdoc.Fragments.bidding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.webdoc.Adapters.CurrentBiddingAdapter
import com.webdoc.theforum.R
import com.webdoc.theforum.databinding.FragmentBiddingBinding
import com.webdoc.theforum.databinding.FragmentCurrentBiddingBinding

class CurrentBiddingFragment : Fragment() {
    private lateinit var binding: FragmentCurrentBiddingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCurrentBiddingBinding.inflate(inflater, container, false)
        initViews()
        return binding.root
    }

    private fun initViews() {
        currentBidPopulateRecycler()
    }
    private fun currentBidPopulateRecycler() {
        binding.rvCurrentBid.setLayoutManager(GridLayoutManager(activity as AppCompatActivity, 1))
        val adapter = CurrentBiddingAdapter(activity as AppCompatActivity)
        binding.rvCurrentBid.setAdapter(adapter)
    }

}