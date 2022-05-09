package com.webdoc.Fragments.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.webdoc.Adapters.PropertySubCatAdapter
import com.webdoc.Adapters.PropertyTypeAdapter
import com.webdoc.Adapters.RecommendedAdapter
import com.webdoc.ModelClasses.PropertySubCatModel
import com.webdoc.ModelClasses.PropertyTypeModel
import com.webdoc.theforum.R
import com.webdoc.theforum.databinding.FragmentAddBinding
import java.util.*

class AddFragment : Fragment() {
    private lateinit var binding: FragmentAddBinding


    var propertiestypearray = arrayOf(
        "Apartments", "Plots", "Commercial", "Residential"
    )
    var propertyTypeId = arrayOf(
        "01", "02", "03", "04"
    )
    var subCatPropertyTitle = arrayOf(
        "All", "Houses", "Flats", "Upper Portion","Lower Portion","Rooms"
    )
    var subCatPropertyImage = intArrayOf(
        R.drawable.all,
        R.drawable.ic_home,
        R.drawable.flat,
        R.drawable.upper,
        R.drawable.lower,
        R.drawable.rooms
    )
    var subCatPropertyId = arrayOf(
        "01", "02", "03", "04","05","06"
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddBinding.inflate(inflater, container, false)

        initViews()
        //  clickListeners()
        return binding.root
    }

    private fun clickListeners() {

    }

    private fun initViews() {
        propertyTypePopulateRecycler()
        subCatPropertyPopulateRecycler()
        recommendedPopulateRecycler()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Add a Property"
    }

    private fun propertyTypePopulateRecycler() {
        val arrayListPropertyType: ArrayList<PropertyTypeModel> = ArrayList<PropertyTypeModel>()
        for (i in propertiestypearray.indices) {
            val modelPropertyType = PropertyTypeModel(
                propertiestypearray.get(i), propertyTypeId.get(i)
            )
            arrayListPropertyType.add(modelPropertyType)
        }
        val layoutManager: RecyclerView.LayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvPropertyType.setLayoutManager(layoutManager)
        val adapterPropertyType =
            PropertyTypeAdapter(activity as AppCompatActivity, arrayListPropertyType)
        binding.rvPropertyType.setAdapter(adapterPropertyType)
    }

    private fun subCatPropertyPopulateRecycler() {
        val arrayListPropertySubCat: ArrayList<PropertySubCatModel> = ArrayList<PropertySubCatModel>()
        for (i in subCatPropertyImage.indices) {
            val modelPropertySubCat = PropertySubCatModel(
                subCatPropertyImage.get(i), subCatPropertyTitle.get(i),subCatPropertyId.get(i)
            )
            arrayListPropertySubCat.add(modelPropertySubCat)
        }
        val layoutManager: RecyclerView.LayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvPropertySubCat.setLayoutManager(layoutManager)
        val adapterPropertySubCat =
            PropertySubCatAdapter(activity as AppCompatActivity, arrayListPropertySubCat)
        binding.rvPropertySubCat.setAdapter(adapterPropertySubCat)
    }

    private fun recommendedPopulateRecycler() {
        binding.rvRecommended.setLayoutManager(GridLayoutManager(activity as AppCompatActivity, 1))
        val recomendedAdapter = RecommendedAdapter(activity as AppCompatActivity)
        binding.rvRecommended.setAdapter(recomendedAdapter)
    }
}