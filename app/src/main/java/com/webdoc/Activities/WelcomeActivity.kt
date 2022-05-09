package com.webdoc.Activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.firebase.auth.FirebaseAuth
import com.webdoc.Activities.LoginAndRegistration.AuthenticationActivity
import com.webdoc.Activities.LoginAndRegistration.LoginActivity
import com.webdoc.Activities.LoginAndRegistration.LoginOptionsActivity
import com.webdoc.Activities.LoginAndRegistration.RegistrationActivity
import com.webdoc.theforum.R
import com.webdoc.theforum.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {
    private lateinit var fragmentManager: FragmentManager
    private lateinit var binding: ActivityWelcomeBinding
    private lateinit var fireBaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        initViews()
        clickListerners()
    }

    private fun clickListerners() {
        binding.btnGuest.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.btnSignUp.setOnClickListener {
            val intent = Intent(this, LoginOptionsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initViews() {
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        fireBaseAuth = FirebaseAuth.getInstance()
        val fireBaseUser = fireBaseAuth.currentUser
        if (fireBaseUser != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        fragmentManager = supportFragmentManager
    }

//    private fun loadFragment(fragment: Fragment) {
//        // load fragment
//        fragmentManager.beginTransaction()
//            .replace(R.id.nav_fragment, fragment, fragment.javaClass.name)
//            .addToBackStack(fragment.javaClass.name).commit()
//
//    }
}