package com.webdoc.Fragments.home

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.webdoc.Activities.MainActivity
import com.webdoc.Adapters.GetPropertiesAdapter
import com.webdoc.Adapters.MarlaCategoriesAdapter
import com.webdoc.Adapters.PriceCategoriesAdapter
import com.webdoc.Essentials.Global
import com.webdoc.Fragments.home.ViewModels.GetPropertiesViewModel
import com.webdoc.ModelClasses.MarlaCategories
import com.webdoc.ModelClasses.PriceCategories
import com.webdoc.Payment.PaymentMethodsActivity
import com.webdoc.theforum.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    var getPropertiesViewModel: GetPropertiesViewModel? = null
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
        initViews()
        clickListeners()
        observers()
        return binding.root
    }

    private fun observers() {
        getPropertiesViewModel!!.LDGetProperties().observe(activity as MainActivity) { response ->
            if (response != null) {
                if (response.result!=null) {
                  //  cartResponse = response

                    val layoutManager = LinearLayoutManager(activity as MainActivity)
                    binding.rvHotSelling.setLayoutManager(layoutManager)
                   val getPropertyAdapter = GetPropertiesAdapter(activity as MainActivity, response)
                    binding.rvHotSelling.setAdapter(getPropertyAdapter)
                    binding.tvNoItem.visibility = View.GONE
                    Global.utils!!.hideCustomLoadingDialog()

                } else {
                    Global.utils!!.hideCustomLoadingDialog()
                    binding.tvNoItem.setVisibility(View.VISIBLE)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Home"

    }

    private fun initViews() {
        pricePopulateRecycler()
        marlaPopulateRecycler()
        getPropertiesViewModel = ViewModelProvider(this).get(GetPropertiesViewModel::class.java)
        Global.utils!!.showCustomLoadingDialog(activity as MainActivity)
        getPropertiesViewModel!!.getPropertiesAPi()
     //   hotSellingPopulateRecycler()

    }

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("ResourceAsColor")
    private fun clickListeners() {

        binding.ivPayment.setOnClickListener {
            val intent = Intent(activity as MainActivity, PaymentMethodsActivity::class.java)
            startActivity(intent)


        }
//        binding.tvForSale.setOnClickListener {
//            binding.view1.visibility = View.VISIBLE
//            binding.view2.visibility = View.GONE
//        }
//
//        binding.tvForBuy.setOnClickListener {
//            binding.view1.visibility = View.GONE
//            binding.view2.visibility = View.VISIBLE
//        }

        binding.clAppartment.setOnClickListener {
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

//    private fun hotSellingPopulateRecycler() {
//        val layoutManager: RecyclerView.LayoutManager =
//            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
//        binding.rvHotSelling.setLayoutManager(layoutManager)
//        val adapter = GetPropertiesAdapter(activity as AppCompatActivity)
//        binding.rvHotSelling.setAdapter(adapter)
//    }


    /* private fun readData(id: String) {
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
     }*/
}


