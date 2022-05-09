package com.webdoc.Fragments.account

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.webdoc.Activities.LoginAndRegistration.LoginOptionsActivity
import com.webdoc.Activities.MainActivity
import com.webdoc.Activities.WelcomeActivity
import com.webdoc.Essentials.Global
import com.webdoc.theforum.R
import com.webdoc.theforum.databinding.FragmentAccountBinding


class AccountFragment : Fragment() {

    private lateinit var binding: FragmentAccountBinding

    var userid: String = ""
    lateinit var fireBaseAuth: FirebaseAuth
    lateinit var mGoogleSignInClient: GoogleSignInClient
    private var email: String = ""
    private var name: String = ""
    private var databaseReference: DatabaseReference? = null
     var username: String = ""
     var useremail: String = ""
    lateinit var profileImage: Uri


  lateinit var prefs: SharedPreferences
    lateinit var edit: SharedPreferences.Editor

    //   var fireBaseUser: FirebaseUser? = null
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
        val fireBaseUser = fireBaseAuth.currentUser
        if (fireBaseUser != null) {
            //  val email = fireBaseUser.email
            name = fireBaseUser.displayName.toString()
            //    profileImage = fireBaseUser!!.photoUrl!!
            email = fireBaseUser.email.toString()
//            binding.tvUserName.text = name
//            Picasso.get().load(profileImage).into(binding.ivUser)
        }

        prefs = (activity as MainActivity).getSharedPreferences("abc", Context.MODE_PRIVATE)
        edit = prefs.edit()
        userid = prefs.getString("id", "").toString()
        username = prefs.getString("name", "").toString()
        useremail = prefs.getString("email", "").toString()

        if (!userid.equals("")) {
            binding.tvViewProfile.visibility = View.VISIBLE
            binding.tvUserName.setText(username)
        }
        else {
            binding.btnSignOut.text = "Sign In"
            binding.tvViewProfile.visibility = View.GONE
        }

        // prefs = (activity as MainActivity).getSharedPreferences("abc", Context.MODE_PRIVATE)
        //  edit = prefs.edit()
        // binding.btnSignOut.text = "Sign In"


        clickListeners()
        return binding.root
    }

    private fun clickListeners() {
        binding.btnSignOut.setOnClickListener {
            mGoogleSignInClient.signOut()
            fireBaseAuth.signOut()
            checkUser()
            edit.clear()
            edit.remove("")
            edit.apply()
        }
if(!userid.equals("")){
    binding.clTopProfile.setOnClickListener {

//            val bundle =Bundle()
//            bundle.putString("name",name)
//
//            val fragment = ViewProfileFragment()
//            fragment.arguments = bundle

        binding.tvViewProfile.visibility = View.VISIBLE
        (activity as MainActivity).loadFragment(
            ViewProfileFragment.newInstance(
                username, useremail
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
            val intent = Intent(activity as MainActivity, WelcomeActivity::class.java)
            startActivity(intent)
        }
    }
}