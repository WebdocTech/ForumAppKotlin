package com.webdoc.Activities

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.material.navigation.NavigationBarView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.webdoc.Activities.LoginAndRegistration.LoginOptionsActivity
import com.webdoc.Fragments.account.AccountFragment
import com.webdoc.Fragments.add.AddFragment
import com.webdoc.Fragments.bidding.BiddingFragment
import com.webdoc.Fragments.home.HomeFragment
import com.webdoc.Fragments.projects.ProjectsFragment
import com.webdoc.theforum.R
import com.webdoc.theforum.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var fragmentManager: FragmentManager
    private lateinit var actionBar: ActionBar
    private var bse: FragmentManager.BackStackEntry? = null
    var dialog: Dialog? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        initViews()
        if (savedInstanceState == null) {
            loadFragment(HomeFragment())
        }
    }

    private var navigationItemSelectedListener = NavigationBarView.OnItemSelectedListener { item ->
        when (item.itemId) {
            R.id.nav_home -> {
                loadFragment(HomeFragment())
                return@OnItemSelectedListener true
            }
            R.id.nav_bidding -> {
                loadFragment(BiddingFragment())
                return@OnItemSelectedListener true
            }
            R.id.nav_add -> {
                loadFragment(AddFragment())
                return@OnItemSelectedListener true
            }
            R.id.nav_projects -> {
                loadFragment(ProjectsFragment())
                return@OnItemSelectedListener true
            }
            R.id.nav_account -> {
                loadFragment(AccountFragment())
                return@OnItemSelectedListener true
            }
            else -> false
        }
    }

    private fun initViews() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        fragmentManager = supportFragmentManager

        actionBar = supportActionBar!!



        // actionBar!!.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE)


        binding.bottomNavigatinView.setOnItemSelectedListener(navigationItemSelectedListener)
        fragmentManager.addOnBackStackChangedListener {
            if (fragmentManager.backStackEntryCount < 1) {
                return@addOnBackStackChangedListener
            }
            bse = fragmentManager.getBackStackEntryAt(fragmentManager.backStackEntryCount - 1)
            if (bse == null || bse!!.name == null || bse!!.name!!.isEmpty()) {
                return@addOnBackStackChangedListener
            }
            Log.e("TAG", "onCreate: " + bse!!.name)
            if (bse!!.name == BiddingFragment::class.java.name) {
                binding.bottomNavigatinView.menu.getItem(1)?.isChecked = true
            } else if (bse!!.name == HomeFragment::class.java.name) {
                binding.bottomNavigatinView.menu.getItem(0)?.isChecked = true
            } else if (bse!!.name == AddFragment::class.java.name) {
                binding.bottomNavigatinView.menu.getItem(2)?.isChecked = true
            } else if (bse!!.name == ProjectsFragment::class.java.name) {
                binding.bottomNavigatinView.menu.getItem(3)?.isChecked = true
            } else if (bse!!.name == AccountFragment::class.java.name) {
                binding.bottomNavigatinView.menu.getItem(4)?.isChecked = true
            }
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 20) {
            if (data != null) {
                val requiredValue = data.getStringExtra("fName")
                Log.e("TAG", "onActivityResult: $requiredValue")
                selectBottomNavIcon(requiredValue)
            }
        }
    }

    override fun onBackPressed() {
        if (fragmentManager.backStackEntryCount < 1) finish()
        val bse = fragmentManager.getBackStackEntryAt(
            fragmentManager.backStackEntryCount - 1
        )
        if (bse.name != null && bse.name == HomeFragment::class.java.name) {
            binding.bottomNavigatinView.menu.getItem(0).isChecked = true
            exitDialog()
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.action_bar_menu_notification, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.notification -> {

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        if (dialog != null) {
            dialog!!.dismiss()
        }
        //
        super.onDestroy()
    }


    fun selectBottomNavIcon(bse: String?) {
        if (bse != null && binding.bottomNavigatinView != null) {
            if (bse == BiddingFragment::class.java.getName()) {
                binding.bottomNavigatinView.getMenu().getItem(1).setChecked(true)
            } else if (bse == HomeFragment::class.java.name) {
                binding.bottomNavigatinView.getMenu().getItem(0).setChecked(true)
            } else if (bse == AddFragment::class.java.getName()) {
                binding.bottomNavigatinView.getMenu().getItem(2).setChecked(true)
            } else if (bse == ProjectsFragment::class.java.getName()) {
                binding.bottomNavigatinView.getMenu().getItem(3).setChecked(true)
            } else if (bse == AccountFragment::class.java.getName()) {
                binding.bottomNavigatinView.getMenu().getItem(4).setChecked(true)
            }
        }
    }

    private fun exitDialog() {
        dialog = Dialog(this)
        dialog!!.setCancelable(false)
        dialog!!.setContentView(R.layout.dialog_exit_app)
        val btn_dia_exi_yes: Button
        val btn_dia_exi_no: Button
        btn_dia_exi_yes = dialog!!.findViewById<Button>(R.id.btn_dia_exi_yes)
        btn_dia_exi_no = dialog!!.findViewById<Button>(R.id.btn_dia_exi_no)
        btn_dia_exi_yes.setOnClickListener { view: View? ->
            finishAffinity()
        }
        btn_dia_exi_no.setOnClickListener { view: View? -> dialog!!.dismiss() }
        if (dialog != null) {
            dialog!!.show()
        }
    }

    fun loadFragment(fragment: Fragment) {
        // load fragment
        fragmentManager.beginTransaction()
            .replace(R.id.nav_fragment, fragment, fragment.javaClass.name)
            .addToBackStack(fragment.javaClass.name).commit()
    }




}