package com.webdoc.Fragments.account

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import com.webdoc.Activities.MainActivity
import com.webdoc.theforum.databinding.FragmentViewProfileBinding


class ViewProfileFragment : Fragment() {

    private lateinit var binding: FragmentViewProfileBinding
    var name: String = ""
    var email: String = ""
    var image: String = ""
    var number: String = ""
    var phoneNo: String? = null
    private var prefs: SharedPreferences? = null
    private var edit: SharedPreferences.Editor? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    companion object {
        fun newInstance(name: String, email: String): ViewProfileFragment {
            val fragment = ViewProfileFragment()
            val args = Bundle()
            args.putString("name", name)
            // args.putString("profileImage", profileImage)
            args.putString("email", email)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentViewProfileBinding.inflate(inflater, container, false)

        val args = this.arguments
        if (args != null) {
            name = args.getString("name").toString()
            email = args.getString("email").toString()
            //  image = args.getString("profileImage").toString()
            binding.tvProfileName.setText(name)
            //  Picasso.get().load(image).into(binding.ivShowprofile)
            binding.tvEmail.setText(email)
        }
        prefs = (activity as MainActivity).getSharedPreferences("abc", Context.MODE_PRIVATE)
        edit = prefs!!.edit()
        phoneNo = prefs!!.getString("phoneNo", "").toString()
        if (!phoneNo.equals("")) {
            binding.tvNumber.setText(phoneNo)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Profile"
    }
}