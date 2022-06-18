package com.webdoc.Fragments.account

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import com.webdoc.Activities.LoginAndRegistration.LoginActivity
import com.webdoc.Activities.MainActivity
import com.webdoc.Essentials.PreferencesNew
import com.webdoc.theforum.R
import com.webdoc.theforum.databinding.FragmentAccountBinding


class AccountFragment : Fragment() {

    private lateinit var binding: FragmentAccountBinding
    var userid: String = ""
    lateinit var fireBaseAuth: FirebaseAuth
    lateinit var mGoogleSignInClient: GoogleSignInClient
    var username: String = ""
    var useremail: String = ""
    var phoneNum: String = ""
    var imageUser: String = ""


   private lateinit var prefs: SharedPreferences
   private lateinit var edit: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAccountBinding.inflate(inflater, container, false)
        fireBaseAuth = FirebaseAuth.getInstance()

        val gso: GoogleSignInOptions =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .requestProfile()
                .build()
        mGoogleSignInClient = GoogleSignIn.getClient(activity as MainActivity, gso);

        prefs = (activity as MainActivity).getSharedPreferences("abc", Context.MODE_PRIVATE)
        edit = prefs.edit()
        userid = prefs.getString(PreferencesNew.KEY_ApplicationUserId, "").toString()
        username = prefs.getString(PreferencesNew.KEY_USER_NAME, "").toString()
        useremail = prefs.getString(PreferencesNew.KEY_USER_EMAIL, "").toString()
        phoneNum = prefs.getString(PreferencesNew.KEY_USER_PHONE, "").toString()
        imageUser = prefs.getString(PreferencesNew.KEY_USER_IMAGE, "").toString()
        // imageUser = profileImage.toString()
        if (!username.equals("")) {
            binding.tvViewProfile.visibility = View.VISIBLE
            binding.tvUserName.setText(username)
            if(!imageUser.equals("")){
                Picasso.get().load(imageUser).into(binding.ivUser)
            }

        } else {
            binding.btnSignOut.text = "Sign In"
            binding.tvViewProfile.visibility = View.GONE
        }


        clickListeners()
        return binding.root
    }

    private fun clickListeners() {
        binding.btnSignOut.setOnClickListener {
            mGoogleSignInClient.signOut()
            edit.putBoolean(PreferencesNew.KEY_IS_LOGIN, false)
            fireBaseAuth.signOut()
            checkUser()
            edit.clear()
            edit.remove("")
            edit.apply()
            (activity as MainActivity).finish()
        }
        if (!username.equals("")) {
            binding.clTopProfile.setOnClickListener {

                binding.tvViewProfile.visibility = View.VISIBLE
                (activity as MainActivity).loadFragment(
                    ViewProfileFragment.newInstance(
                        username, useremail, phoneNum, imageUser
                    )
                )
            }
        }

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Account"
    }


    fun checkUser() {
        val fireBaseUser = fireBaseAuth.currentUser
        if (fireBaseUser == null) {
            val intent = Intent(activity as MainActivity, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}

