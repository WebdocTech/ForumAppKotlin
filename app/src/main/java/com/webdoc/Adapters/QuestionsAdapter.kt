package com.webdoc.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.webdoc.Fragments.home.PropertyDetailActivity
import com.webdoc.theforum.R


class QuestionsAdapter(var context: Context) :
    RecyclerView.Adapter<QuestionsAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.model_questions, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
//            val intent = Intent(context, PropertyDetailActivity::class.java)
//            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return 4
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var btnAddToCart: Button? = null

        init {

            //            btnAddToCart = itemView.findViewById(R.id.btnAddToCart);
        }
    }
}
