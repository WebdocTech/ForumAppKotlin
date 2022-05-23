package com.webdoc.Activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.webdoc.Activities.LoginAndRegistration.LoginActivity
import com.webdoc.Activities.LoginAndRegistration.LoginOptionsActivity
import com.webdoc.Essentials.PreferencesNew
import com.webdoc.theforum.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding
    private lateinit var fireBaseAuth: FirebaseAuth
    private lateinit var prefs: SharedPreferences
    private lateinit var edit: SharedPreferences.Editor
    var userLogin: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        initViews()
        clickListerners()
    }

    private fun clickListerners() {

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
       // fireBaseAuth = FirebaseAuth.getInstance()
        prefs = getSharedPreferences("abc", Context.MODE_PRIVATE)
        edit = prefs.edit()
        userLogin = prefs.getBoolean(PreferencesNew.KEY_IS_LOGIN, false)
        if (userLogin) {
            val intent = Intent(this@WelcomeActivity, MainActivity::class.java)
            startActivity(intent)
        }
    //    val fireBaseUser = fireBaseAuth.currentUser
//        if (fireBaseUser != null && userLogin) {
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//        }


    }
}
