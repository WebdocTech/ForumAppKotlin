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
import androidx.recyclerview.widget.RecyclerView
import com.takusemba.multisnaprecyclerview.MultiSnapHelper
import com.takusemba.multisnaprecyclerview.SnapGravity
import com.webdoc.Activities.MainActivity
import com.webdoc.Adapters.VideosAdapter
import com.webdoc.Essentials.Global
import com.webdoc.Essentials.SnapHelperOneByOne
import com.webdoc.Fragments.video.VideoResponse.Video
import com.webdoc.Fragments.video.VideoResponse.VideosResonse
import com.webdoc.api.APIInterface
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Video"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentVideoBinding.inflate(inflater, container, false)

        binding = FragmentVideoBinding.inflate(inflater, container, false)
        Global.activityy = activity as MainActivity
        biddingFragmentManager = (activity as MainActivity).supportFragmentManager
        // binding.rv.setHasFixedSize(true)
        val mLayoutManager: RecyclerView.LayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        //   val mLayoutManager = LinearLayoutManager(activity as MainActivity)
//        mLayoutManager.setReverseLayout(true);
//        mLayoutManager.setStackFromEnd(true);
        binding.rv.layoutManager = mLayoutManager
        //      loadFragment(FullPaymentFragment())
        if ((activity as MainActivity).isOnline(activity as MainActivity)) {
            Global.utils!!.showCustomLoadingDialog(activity as MainActivity)
            // binding.shimmerLoader.progress
            getVideosData();
        } else {
            Toast.makeText(
                activity as MainActivity, "Check Internet",
                Toast.LENGTH_SHORT
            ).show()
        }

        return binding.root
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

            .baseUrl("https://theforumapi.webddocsystems.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client) // Set HttpClient to be used by Retrofit
            .build()
        val apiInterface = retrofit.create(APIInterface::class.java)
        val call1: Call<VideosResonse> = apiInterface.getVideos()
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

                    // binding.shimmerLoader.visibility = View.GONE
                    Global.videoList = videosList
                    videosAdapter = VideosAdapter(activity as MainActivity, videosList)
                    binding.rv.adapter = videosAdapter
                    videosAdapter!!.notifyDataSetChanged()

                    val multiSnapHelper = MultiSnapHelper(
                        SnapGravity.START, 1,
                        50f
                    )
                    multiSnapHelper.attachToRecyclerView(binding.rv)

                    Global.utils!!.hideCustomLoadingDialog()
                }
                // MLDGetProperties.postValue(getPropertiesResponse)

                // Global.cartResponse = cartResponse;
                if (!response.isSuccessful()) {
                    Toast.makeText(
                        activity as MainActivity,
                        response.body().toString() + "ddsdfdsfsd",
                        Toast.LENGTH_SHORT
                    )
                        .show()

                    return
                }
            }

            override fun onFailure(call: Call<VideosResonse>, t: Throwable) {

            }


        });
    }
}
