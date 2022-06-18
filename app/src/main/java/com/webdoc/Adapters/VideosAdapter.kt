package com.webdoc.Adapters

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.webdoc.Activities.MainActivity
import com.webdoc.Essentials.Global
import com.webdoc.Fragments.video.VideoResponse.Video
import com.webdoc.theforum.R


class VideosAdapter(var context: Context, videoModels: List<Video?>) :
    RecyclerView.Adapter<VideosAdapter.ViewHolder>() {
    var number: String? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.video_item_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mWebView.clearCache(true)
        holder.mWebView.clearHistory()
        holder.mWebView.settings.javaScriptEnabled = true
        holder.mWebView.setBackgroundColor(Color.BLACK);
        holder.mWebView.settings.javaScriptCanOpenWindowsAutomatically = true
        holder.mWebView.loadUrl(Global.videoResp.result.videos.get(position).videoUrl)
        holder.mWebView.webViewClient = WebViewClient()
        holder.tv_video_title.setText(Global.videoResp.result.videos.get(position).title)
        holder.tv_doctor_name.setText(
            Global.videoResp.result.videos.get(position).title
                .toString() + " | The Forum"
        )
        holder.tv_date.setText(Global.videoResp.result.videos.get(position).date)
        holder.tv_duration.setText(Global.videoResp.result.videos.get(position).duration)
    }

    override fun getItemCount(): Int {
        return Global.videoList.size
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mWebView: WebView
        var cc: CardView
        var tv_video_title: TextView
        var tv_doctor_name: TextView
        var tv_date: TextView
        var tv_duration: TextView


        init {
            mWebView = itemView.findViewById(R.id.webView)
            cc = itemView.findViewById(R.id.videocard)
            tv_video_title = itemView.findViewById(R.id.tv_video_title)
            tv_date = itemView.findViewById(R.id.tv_date)
            tv_doctor_name = itemView.findViewById(R.id.tv_doctor_name)
            tv_duration = itemView.findViewById(R.id.tv_duration)
        }
    }

    /* init {
         //Global.videoResp = videoModels
         notifyDataSetChanged()
     }*/
}