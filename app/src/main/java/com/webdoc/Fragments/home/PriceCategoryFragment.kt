package com.webdoc.Fragments.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.webdoc.Adapters.HotSellingAdapter
import com.webdoc.Adapters.PriceCategoriesAdapter
import com.webdoc.Adapters.PriceSubCategoryAdapter
import com.webdoc.ModelClasses.AppartmentsModel
import com.webdoc.ModelClasses.PriceCategories
import com.webdoc.theforum.R
import com.webdoc.theforum.databinding.FragmentHomeBinding
import com.webdoc.theforum.databinding.FragmentPriceCategoryBinding
import java.util.ArrayList


class PriceCategoryFragment : Fragment() {
    private lateinit var binding: FragmentPriceCategoryBinding
    var appartmentprice = arrayOf(
       "Starting Price : 2crore","Starting Price : 3crore","Starting Price : 50lac","Starting Price : 60lac","Starting Price : 70lac"
    )
    var appartmentid = arrayOf(
        "01", "02", "03","04","05"
    )

    var appartmenttitle = arrayOf(
        "Apartment No 1","Apartment No 2","Apartment No 3","Apartment No 4","Apartment No 5"
    )
    var appartmentdate = arrayOf(
        "Date: 4-14-2022", "Date: 4-14-2022", "Date: 4-14-2022","Date: 4-14-2022","Date: 4-14-2022"
    )
    var appartmenttime = arrayOf(
        "Time: 1:04", "Time: 1:05", "Time: 1:06", "Time: 1:07","Time: 1:08"
    )
    var subCatPropertyImage = intArrayOf(
        R.drawable.apparmentcurrent,
        R.drawable.apparmentcurrent,
        R.drawable.apparmentcurrent,
        R.drawable.apparmentcurrent,
        R.drawable.apparmentcurrent
    )
    var title: String = ""
    private var bundle: Bundle? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPriceCategoryBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        initViews()
        //clickListeners()
        return binding.root
    }

    companion object {
        fun newInstance(title: String): PriceCategoryFragment {
            val fragment = PriceCategoryFragment()
            val args = Bundle()
            args.putString("title",title)
            fragment.arguments = args
            return fragment
        }
    }

    private fun clickListeners() {

    }

    private fun initViews() {
        val bundle = this.arguments
        if (bundle != null) {
            title = bundle.getString("title").toString()
            binding.tvPriceTitle.setText(title)
        }

        priceSubcategoryPopulateRecycler()
//if(title.equals("Low Price")){
//
//}
//else if(title.equals("High Price")){
//
//}
//else if(title.equals("Installment")){
//
//}
    }

//    private fun priceSubcategoryPopulateRecycler() {
//        binding.rvSubCatPrice.setLayoutManager(GridLayoutManager(activity as AppCompatActivity, 1))
//        val adapter = PriceSubCategoryAdapter(activity as AppCompatActivity,)
//        binding.rvSubCatPrice.setAdapter(adapter)
//    }
    private fun priceSubcategoryPopulateRecycler() {
        val arrayListApartment: ArrayList<AppartmentsModel> = ArrayList<AppartmentsModel>()
        for (i in appartmentid.indices) {
            val modelApartment = AppartmentsModel(
                subCatPropertyImage.get(i),
                appartmenttitle.get(i), appartmentid.get(i) ,appartmentprice.get(i),
                appartmentdate.get(i),appartmenttime.get(i)
            )
            arrayListApartment.add(modelApartment)
        }

        val layoutManagerPrice: RecyclerView.LayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvSubCatPrice.setLayoutManager(layoutManagerPrice)
        val adapterPrice = PriceSubCategoryAdapter(activity as AppCompatActivity, arrayListApartment)
        binding.rvSubCatPrice.setAdapter(adapterPrice)
        adapterPrice.notifyDataSetChanged()
    }
}