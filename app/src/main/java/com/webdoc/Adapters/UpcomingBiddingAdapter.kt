package com.webdoc.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.webdoc.theforum.R

class UpcomingBiddingAdapter(var context: Context) :
    RecyclerView.Adapter<UpcomingBiddingAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.model_upcoming_bidding, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        holder.itemView.setOnClickListener {
//            context.startActivity(
//                Intent(
//                   // context, DressDetailsActivity::class.java
//                )
//            )
//        }

//        holder.btnAddToCart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
    }

    override fun getItemCount(): Int {
        return 5
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var btnAddToCart: Button? = null

        init {

            //            btnAddToCart = itemView.findViewById(R.id.btnAddToCart);
        }
    }
}
