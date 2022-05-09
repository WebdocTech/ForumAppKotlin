package com.webdoc.Fragments.bidding

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.webdoc.theforum.R
import com.webdoc.theforum.databinding.FragmentBiddingBinding


class BiddingFragment : Fragment() {
    private lateinit var binding: FragmentBiddingBinding
    private lateinit var biddingFragmentManager: FragmentManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBiddingBinding.inflate(inflater, container, false)
        initViews()
        clickListeners()
        return binding.root
    }

    private fun clickListeners() {
        binding.tvCurrentBid.setOnClickListener {
            loadFragment(CurrentBiddingFragment())
            binding.viewbid3.visibility = View.VISIBLE
            binding.viewbid4.visibility = View.GONE

            binding.tvCurrentBid.setTextColor(Color.YELLOW)
            binding.tvUpcomingBid.setTextColor(Color.WHITE)

        }
        binding.tvUpcomingBid.setOnClickListener {
            loadFragment(UpcomingBiddingFragment())
            binding.viewbid3.visibility = View.GONE
            binding.viewbid4.visibility = View.VISIBLE

            binding.tvCurrentBid.setTextColor(Color.WHITE)
            binding.tvUpcomingBid.setTextColor(Color.YELLOW)
        }
    }

    private fun initViews() {
        biddingFragmentManager = (activity as AppCompatActivity).supportFragmentManager
        loadFragment(CurrentBiddingFragment())

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Bidding"
    }

    private fun loadFragment(fragment: Fragment) {
        // load fragment
        biddingFragmentManager.beginTransaction()
            .replace(R.id.subFragmentContainer, fragment, fragment.javaClass.name).commit()

    }
}