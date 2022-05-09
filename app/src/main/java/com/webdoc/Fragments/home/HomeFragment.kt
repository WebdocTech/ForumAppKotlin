package com.webdoc.Fragments.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.webdoc.Activities.MainActivity
import com.webdoc.Adapters.CurrentBiddingAdapter
import com.webdoc.Adapters.MarlaCategoriesAdapter
import com.webdoc.Adapters.PriceCategoriesAdapter
import com.webdoc.Essentials.PreferencesNew
import com.webdoc.ModelClasses.MarlaCategories
import com.webdoc.ModelClasses.PriceCategories
import com.webdoc.theforum.databinding.FragmentHomeBinding
import java.util.*


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var editt: SharedPreferences.Editor
    private lateinit var prefs: SharedPreferences
    var preferences: PreferencesNew? = null
    var check = 1
    var city = ""
    var arrayOfCities = arrayOf(
        "Select City",
        "Rawalpindi",
        "Islamabad",
        "Lahore",
        "Faisalabad",
        "Karachi",
        "Peshawar"
    )
    var priceCategory = arrayOf(
        "Low Price", "High Price", "Installment"
    )
    var priceCatId = arrayOf(
        "01", "02", "03"
    )

    var marlaCategory = arrayOf(
        "1 Bedroom", "2 Bedroom", "3 Marla", "4 Marla"
    )
    var marlaCatId = arrayOf(
        "01", "02", "03", "04"
    )
    var userid: String = ""
    private var databaseReference: DatabaseReference? = null

    lateinit var edit: SharedPreferences.Editor
    var username: String = ""
    var useremail: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        preferences = PreferencesNew(activity as MainActivity)
        initViews()
        clickListeners()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Home"

    }

    private fun initViews() {
        prefs = (activity as MainActivity).getSharedPreferences("abc", Context.MODE_PRIVATE)
        edit = prefs.edit()
        userid = prefs.getString("id", "").toString()
        readData(userid)
        pricePopulateRecycler()
        marlaPopulateRecycler()
        hotSellingPopulateRecycler()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("ResourceAsColor")
    private fun clickListeners() {
        binding.tvForSale.setOnClickListener {
            binding.view1.visibility = View.VISIBLE
            binding.view2.visibility = View.GONE
        }

        binding.tvForBuy.setOnClickListener {
            binding.view1.visibility = View.GONE
            binding.view2.visibility = View.VISIBLE
        }

        binding.clAppartment.setOnClickListener {
            //    PreferencesNew.getInstance(applicationContext).kEY_ApplicationUserId == "jadoon"




            edit.putString(PreferencesNew.KEY_ApplicationUserId, "jadoon");
            edit.commit();
            val useridss = prefs.getString(PreferencesNew.KEY_ApplicationUserId, "").toString()
            Log.i(
                "temp",
                useridss + ""
            )


            binding.view5.visibility = View.VISIBLE
            binding.view6.visibility = View.GONE
            binding.view7.visibility = View.GONE
            binding.ivAprtmnt.setColorFilter(Color.YELLOW)
            binding.ivPlt.setColorFilter(Color.WHITE)
            binding.ivComrcial.setColorFilter(Color.WHITE)
            binding.tvApartment.setTextColor(Color.YELLOW)
            binding.tvPlot.setTextColor(Color.WHITE)
            binding.tvCommercial.setTextColor(Color.WHITE)

            //  binding.tvApartment.leftDrawable(R.drawable.ic_apartment, colorRes = R.color.darkyellow)
        }

        binding.clPlot.setOnClickListener {
            binding.view5.visibility = View.GONE
            binding.view6.visibility = View.VISIBLE
            binding.view7.visibility = View.GONE
            binding.ivAprtmnt.setColorFilter(Color.WHITE)
            binding.ivPlt.setColorFilter(Color.YELLOW)
            binding.ivComrcial.setColorFilter(Color.WHITE)
            binding.tvApartment.setTextColor(Color.WHITE)
            binding.tvPlot.setTextColor(Color.YELLOW)
            binding.tvCommercial.setTextColor(Color.WHITE)
        }

        binding.clComrcial.setOnClickListener {
            binding.view5.visibility = View.GONE
            binding.view6.visibility = View.GONE
            binding.view7.visibility = View.VISIBLE
            binding.ivAprtmnt.setColorFilter(Color.WHITE)
            binding.ivPlt.setColorFilter(Color.WHITE)
            binding.ivComrcial.setColorFilter(Color.YELLOW)
            binding.tvApartment.setTextColor(Color.WHITE)
            binding.tvPlot.setTextColor(Color.WHITE)
            binding.tvCommercial.setTextColor(Color.YELLOW)

        }
    }

    private fun pricePopulateRecycler() {
        val arrayListPrice: ArrayList<PriceCategories> = ArrayList<PriceCategories>()
        for (i in priceCategory.indices) {
            val modelPrice = PriceCategories(
                priceCategory.get(i), priceCatId.get(i)
            )
            arrayListPrice.add(modelPrice)
        }

        val layoutManagerPrice: RecyclerView.LayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        binding.rvPriceCat.setLayoutManager(layoutManagerPrice)
        val adapterPrice = PriceCategoriesAdapter(activity as AppCompatActivity, arrayListPrice)
        binding.rvPriceCat.setAdapter(adapterPrice)
        adapterPrice.notifyDataSetChanged()
    }

    private fun marlaPopulateRecycler() {
        val arrayListMarla: ArrayList<MarlaCategories> = ArrayList<MarlaCategories>()
        for (i in marlaCategory.indices) {
            val modelMarla = MarlaCategories(
                marlaCategory.get(i), marlaCatId.get(i)
            )
            arrayListMarla.add(modelMarla)
        }

        val layoutManager: RecyclerView.LayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvMarlaCat.setLayoutManager(layoutManager)
        val adapterMarla = MarlaCategoriesAdapter(activity as AppCompatActivity, arrayListMarla)
        binding.rvMarlaCat.setAdapter(adapterMarla)
    }

    private fun hotSellingPopulateRecycler() {
        val layoutManager: RecyclerView.LayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvHotSelling.setLayoutManager(layoutManager)
        val adapter = CurrentBiddingAdapter(activity as AppCompatActivity)
        binding.rvHotSelling.setAdapter(adapter)
    }

    fun TextView.leftDrawable(
        @DrawableRes id: Int = 0,
        @DimenRes sizeRes: Int = 0,
        @ColorInt color: Int = 0,
        @ColorRes colorRes: Int = 0
    ) {
        val drawable = ContextCompat.getDrawable(context, id)

        if (color != 0) {
            drawable?.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        } else if (colorRes != 0) {
            val colorInt = ContextCompat.getColor(context, colorRes)
            drawable?.setColorFilter(colorInt, PorterDuff.Mode.SRC_ATOP)
        }
        this.setCompoundDrawables(drawable, null, null, null)
    }

    private fun readData(id: String) {
        databaseReference = FirebaseDatabase.getInstance().getReference("user")
        databaseReference!!.child(id).get()
            .addOnCompleteListener(object : OnCompleteListener<DataSnapshot?> {
                override fun onComplete(task: Task<DataSnapshot?>) {
                    if (task.isSuccessful()) {
                        if (task.getResult()!!.exists()) {
                            // Toast.makeText(activity as AppCompatActivity, "Successfull", Toast.LENGTH_SHORT).show()
                            val dataSnapshot: DataSnapshot = task.getResult()!!
                            username = dataSnapshot.child("name").value.toString()
                            useremail = dataSnapshot.child("email").value.toString()
                            edit.putString("name", username)
                            edit.putString("email", useremail)
                            edit.commit()
                            edit.apply()

                            //  binding.tvUserName.setText(username)
                            //  binding.tvViewProfile.setText(email)
                        } else {
                            Toast.makeText(
                                activity as MainActivity,
                                "User Doesn't Exist",
                                Toast.LENGTH_SHORT
                            )
                                .show()

                        }
                    } else {
                        Toast.makeText(
                            activity as MainActivity,
                            "Failed to read",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            })
    }
}


