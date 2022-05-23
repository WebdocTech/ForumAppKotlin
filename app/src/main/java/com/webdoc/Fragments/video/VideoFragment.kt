package com.webdoc.Fragments.video

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.webdoc.Activities.MainActivity
import com.webdoc.Adapters.VideosAdapter
import com.webdoc.Essentials.Global
import com.webdoc.Essentials.SnapHelperOneByOne
import com.webdoc.Fragments.video.VideoResponse.Video
import com.webdoc.Fragments.video.VideoResponse.VideosResonse
import com.webdoc.api.APIInterface
import com.webdoc.theforum.R
import com.webdoc.theforum.databinding.FragmentVideoBinding
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class VideoFragment : Fragment() {
    var videosAdapter: VideosAdapter? = null
    private lateinit var binding: FragmentVideoBinding
    private lateinit var biddingFragmentManager: FragmentManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVideoBinding.inflate(inflater, container, false)
        initViews()
        clickListeners()
        return binding.root
    }

    private fun clickListeners() {

    }

    private fun initViews() {

        biddingFragmentManager = (activity as MainActivity).supportFragmentManager
        binding.rv.setHasFixedSize(true)
        val mLayoutManager = LinearLayoutManager(activity as MainActivity)
        mLayoutManager.reverseLayout = true
        mLayoutManager.stackFromEnd = true
        binding.rv.layoutManager = mLayoutManager
        //      loadFragment(FullPaymentFragment())

        getVideosData();


    }

    private fun getVideosData() {


        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        var client: OkHttpClient? = OkHttpClient()
        client = OkHttpClient.Builder()
            .connectTimeout(100, TimeUnit.SECONDS)
            .writeTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.MINUTES)
            .addInterceptor(interceptor)
            .build()
        val retrofit = Retrofit.Builder()


            .baseUrl(" https://theforumapi.webddocsystems.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client) // Set HttpClient to be used by Retrofit
            .build()
        val apiInterface = retrofit.create(APIInterface::class.java)
        val call1: Call<VideosResonse> = apiInterface.getvideos()
        call1.enqueue(object : Callback<VideosResonse> {
            override fun onResponse(
                call: Call<VideosResonse>,
                response: Response<VideosResonse>
            ) {
                val videoresp: VideosResonse? = response.body()
                if (videoresp != null) {
                    Global.videoResp = videoresp

                    var videosList: ArrayList<Video> = ArrayList()
                    for (i in videoresp.result.videos) {
                        videosList.add(i)
                    }

                    binding.shimmerLoader.visibility = View.GONE
                    Global.videoList = videosList
                    videosAdapter = VideosAdapter(activity as MainActivity, videosList)
                    binding.rv.adapter = videosAdapter
                    videosAdapter!!.notifyDataSetChanged()
                    val linearSnapHelper: LinearSnapHelper = SnapHelperOneByOne()
                    linearSnapHelper.attachToRecyclerView(binding.rv)
                }
                // MLDGetProperties.postValue(getPropertiesResponse)

                // Global.cartResponse = cartResponse;
                if (!response.isSuccessful()) {
                    Toast.makeText(
                        activity as MainActivity,
                        response.body().toString(),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    return
                }
            }

            override fun onFailure(call: Call<VideosResonse>, t: Throwable) {
                //         Toast.makeText(Global.applicationContext, "onFailure called ", Toast.LENGTH_SHORT).show();
                call.cancel()
            }
        })


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Video"
    }

    private fun loadFragment(fragment: Fragment) {
        // load fragment
        biddingFragmentManager.beginTransaction()
            .replace(R.id.subFragmentContainer, fragment, fragment.javaClass.name).commit()

    }
}