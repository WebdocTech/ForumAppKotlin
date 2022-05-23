package com.webdoc.Fragments.myproperty

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.webdoc.Activities.MainActivity
import com.webdoc.Adapters.MyPropertyAdapter
import com.webdoc.Essentials.Global
import com.webdoc.Essentials.PreferencesNew
import com.webdoc.Fragments.myproperty.ViewModel.MyPropertyViewModel
import com.webdoc.theforum.databinding.FragmentMyPropertyBinding


class MyPropertyFragment : Fragment() {

    private lateinit var binding: FragmentMyPropertyBinding
    private lateinit var prefs: SharedPreferences
    private lateinit var edit: SharedPreferences.Editor
    var myPropertyViewModel: MyPropertyViewModel? = null
    private var userid: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyPropertyBinding.inflate(inflater, container, false)
        initViews()
        clickListerners()
        observers()
        //clickListeners()
        return binding.root

    }

    private fun observers() {
        myPropertyViewModel!!.LDMProp().observe(activity as MainActivity) { response ->
            if (response != null) {

                if (response.result!!.responseCode.equals("0000")) {

                    Global.mypropResp = response
                    val layoutManager = LinearLayoutManager(activity as MainActivity)
                    binding.rvMyProperty.setLayoutManager(layoutManager)
                    val getPropertyAdapter = MyPropertyAdapter(activity as MainActivity, response)
                    binding.rvMyProperty.setAdapter(getPropertyAdapter)
                    binding.tvMyPropNoItem.visibility = View.GONE
                    Global.utils!!.hideCustomLoadingDialog()


                } else {
                    Global.utils!!.hideCustomLoadingDialog()
                    binding.tvMyPropNoItem.setVisibility(View.VISIBLE)
                }
            }
        }
    }

    private fun clickListerners() {

    }


    private fun initViews() {
        myPropertyViewModel = ViewModelProvider(this).get(MyPropertyViewModel::class.java)

        prefs = (activity as MainActivity).getSharedPreferences("abc", Context.MODE_PRIVATE)
        edit = prefs.edit()

        userid = prefs.getString(PreferencesNew.KEY_ApplicationUserId, "").toString()
        Global.utils!!.showCustomLoadingDialog(activity as MainActivity)
        myPropertyViewModel!!.calMyPropertyApi(userid)
        //    projectsPopulateRecycler()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "My Property"
    }
//    private fun projectsPopulateRecycler() {
//        binding.rvMyProperty.setLayoutManager(GridLayoutManager(activity as AppCompatActivity, 1))
//        val projectsAdapter = MyPropertyAdapter(activity as AppCompatActivity)
//        binding.rvMyProperty.setAdapter(projectsAdapter)
//    }
}