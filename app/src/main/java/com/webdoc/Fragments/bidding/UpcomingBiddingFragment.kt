package com.webdoc.Fragments.bidding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.webdoc.Adapters.UpcomingBiddingAdapter
import com.webdoc.theforum.R
import com.webdoc.theforum.databinding.FragmentBiddingBinding
import com.webdoc.theforum.databinding.FragmentUpcomingBiddingBinding


class UpcomingBiddingFragment : Fragment() {
    private lateinit var binding: FragmentUpcomingBiddingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpcomingBiddingBinding.inflate(inflater, container, false)
        initViews()
        return binding.root
    }

    private fun initViews() {
        upcomingBidPopulateRecycler()
    }

    private fun upcomingBidPopulateRecycler() {
        binding.rvUpcomingBid.setLayoutManager(GridLayoutManager(activity as AppCompatActivity, 1))
        val adapter = UpcomingBiddingAdapter(activity as AppCompatActivity)
        binding.rvUpcomingBid.setAdapter(adapter)
    }
}