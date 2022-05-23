package com.webdoc.Fragments.video

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.webdoc.Fragments.home.FullPaymentFragment
import com.webdoc.Fragments.home.InstallmentPlanFragment
import com.webdoc.theforum.R
import com.webdoc.theforum.databinding.FragmentVideoBinding


class VideoFragment : Fragment() {
    private lateinit var binding: FragmentVideoBinding
    private lateinit var biddingFragmentManager: FragmentManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVideoBinding.inflate(inflater, container, false)
        initViews()
        clickListeners()
        return binding.root
    }

    private fun clickListeners() {
//        binding.tvCurrentBid.setOnClickListener {
//            loadFragment(FullPaymentFragment())
//            binding.viewbid3.visibility = View.VISIBLE
//            binding.viewbid4.visibility = View.GONE
//
//            binding.tvCurrentBid.setTextColor(Color.YELLOW)
//            binding.tvUpcomingBid.setTextColor(Color.WHITE)
//
//        }
//        binding.tvUpcomingBid.setOnClickListener {
//            loadFragment(InstallmentPlanFragment())
//            binding.viewbid3.visibility = View.GONE
//            binding.viewbid4.visibility = View.VISIBLE
//
//            binding.tvCurrentBid.setTextColor(Color.WHITE)
//            binding.tvUpcomingBid.setTextColor(Color.YELLOW)
//        }
    }

    private fun initViews() {
      //  biddingFragmentManager = (activity as AppCompatActivity).supportFragmentManager
      //  loadFragment(FullPaymentFragment())

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Video"
    }

    private fun loadFragment(fragment: Fragment) {
        // load fragment
        biddingFragmentManager.beginTransaction()
            .replace(R.id.subFragmentContainer, fragment, fragment.javaClass.name).commit()

    }
}