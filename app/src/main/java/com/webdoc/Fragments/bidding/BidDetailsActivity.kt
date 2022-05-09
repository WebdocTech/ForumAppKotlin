package com.webdoc.Fragments.bidding

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBar
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.webdoc.ModelClasses.Score
import com.webdoc.theforum.R
import com.webdoc.theforum.databinding.ActivityBidDetailsBinding
import com.webdoc.theforum.databinding.ActivityPropertyDetailBinding
import java.text.SimpleDateFormat
import java.util.*

class BidDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBidDetailsBinding
    private lateinit var countDownTimer: CountDownTimer
    private lateinit var actionBar: ActionBar
    private var imagesCounter = 1
    private var scoreList = ArrayList<Score>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val timer = object: CountDownTimer(30000, 1000) {
//            override fun onTick(millisUntilFinished: Long) {
//                binding.tvTimer.setText("seconds remaining: " + millisUntilFinished / 1000);
//            }
//
//            override fun onFinish() {
//
//            }
//        }
//        timer.start()

        InitViews()
        printDifferenceDateForHours()
        Clicklisteners()
    }

    private fun Clicklisteners() {
//        binding.ivRightArrow.setOnClickListener {
//            if (imagesCounter <= 3) {
//                imagesCounter++
//                setImage(imagesCounter)
//            }
//        }
//
//        binding.ivLeftArrow.setOnClickListener {
//            if (imagesCounter > 1) {
//                imagesCounter--
//                setImage(imagesCounter)
//            }
//        }
    }

    private fun InitViews() {
        binding = ActivityBidDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        actionBar = supportActionBar!!
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar()?.setDisplayShowTitleEnabled(false);
//        setImage(imagesCounter)
        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel(R.drawable.apparmentcurrent))
        imageList.add(SlideModel(R.drawable.apparmentupcoming))
        imageList.add(SlideModel(R.drawable.apparmentcurrent))
        binding.imageSlider.setImageList(imageList, ScaleTypes.FIT)
        scoreList = getScoreList()

        initBarChart()


        //now draw bar chart with dynamic data
        val entries: ArrayList<BarEntry> = ArrayList()

        //you can replace this data object with  your custom object
        for (i in scoreList.indices) {
            val score = scoreList[i]
            entries.add(BarEntry(i.toFloat(), score.score.toFloat()))

        }
        val barDataSet = BarDataSet(entries, "")
        barDataSet.setColors(*ColorTemplate.LIBERTY_COLORS)

        val data = BarData(barDataSet)
        binding.barChart.data = data

        binding.barChart.invalidate()
    }
//    private fun setImage(imagesCounter: Int) {
//        if (imagesCounter == 1) {
//            binding.ivPropertyImg.setImageResource(R.drawable.apparmentcurrent)
//        } else if (imagesCounter == 2) {
//            binding.ivPropertyImg.setImageResource(R.drawable.apparmentupcoming)
//        } else if (imagesCounter == 3) {
//            binding.ivPropertyImg.setImageResource(R.drawable.apparmentcurrent)
//        }
//    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.action_bar_menu_favorite, menu)
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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun printDifferenceDateForHours() {

        val currentTime = Calendar.getInstance().time
        val endDateDay = "27/04/2022 00:00:00"
        val format1 = SimpleDateFormat("dd/MM/yyyy hh:mm:ss", Locale.getDefault())
        val endDate = format1.parse(endDateDay)

        //milliseconds
        val different = endDate.time - currentTime.time
        countDownTimer = object : CountDownTimer(different, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                var diff = millisUntilFinished
                val secondsInMilli: Long = 1000
                val minutesInMilli = secondsInMilli * 60
                val hoursInMilli = minutesInMilli * 60
                val daysInMilli = hoursInMilli * 24

                val elapsedDays = diff / daysInMilli
                diff %= daysInMilli

                val elapsedHours = diff / hoursInMilli
                diff %= hoursInMilli

                val elapsedMinutes = diff / minutesInMilli
                diff %= minutesInMilli

                val elapsedSeconds = diff / secondsInMilli

                binding.tvDay.text = "$elapsedDays"
                binding.tvHour.text = "$elapsedHours"
                binding.tvMin.text = "$elapsedMinutes"
                binding.tvSec.text = "$elapsedSeconds"
            }

            override fun onFinish() {
                binding.tvFinish.text = getString(R.string.bidding_finish)
                binding.llTimeLeft.visibility = View.GONE
                binding.llUnits.visibility = View.GONE
                binding.textView3.visibility = View.GONE
            }
        }.start()
    }

    private fun initBarChart() {
//        hide grid lines
        binding.barChart.axisLeft.setDrawGridLines(false)
        val xAxis: XAxis = binding.barChart.xAxis
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)

        //remove right y-axis
        binding.barChart.axisRight.isEnabled = false

        //remove legend
        binding.barChart.legend.isEnabled = false


        //remove description label
        binding.barChart.description.isEnabled = false


        //add animation
        binding.barChart.animateY(3000)

        // to draw label on xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM_INSIDE
        xAxis.valueFormatter = MyAxisFormatter()
        xAxis.setDrawLabels(true)
        xAxis.granularity = 1f
        xAxis.labelRotationAngle = +90f

    }

    private fun getScoreList(): ArrayList<Score> {
        scoreList.add(Score("John", 56))
        scoreList.add(Score("Rey", 75))
        scoreList.add(Score("Steve", 85))
        scoreList.add(Score("Kevin", 45))
        scoreList.add(Score("Jeff", 63))
        return scoreList
    }

    inner class MyAxisFormatter : IndexAxisValueFormatter() {

        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            val index = value.toInt()
            //  Log.d(TAG, "getAxisLabel: index $index")
            return if (index < scoreList.size) {
                scoreList[index].name
            } else {
                ""
            }
        }
    }
}