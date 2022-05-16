package com.webdoc.Adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.webdoc.ModelClasses.MarlaCategories
import com.webdoc.ModelClasses.PriceCategories
import com.webdoc.theforum.R
import java.util.*

class MarlaCategoriesAdapter(var mContext: Context, var arrayList: ArrayList<MarlaCategories>) :
    RecyclerView.Adapter<MarlaCategoriesAdapter.MyViewHolder>() {

    var row_index: Int = -1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.model_marla_category, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.btn_marlaTitle.text = arrayList[position].marlatitle
        holder.itemView.setOnClickListener {

            //  expertiesList.questionanswer(arrayList[position].getTitle())
            if (holder.cl_marlaCat.getBackground()
                    .getConstantState() === mContext.resources.getDrawable(R.drawable.bg_selected_border).constantState
            ){
                holder.cl_marlaCat.setBackgroundResource(R.drawable.marla_button_shape)
                holder.btn_marlaTitle.setTextColor(Color.parseColor("#ffffff"));
            }else{
                row_index = position;
                notifyDataSetChanged();
            }

            //  Toast.makeText(mContext, expertiesList.size.toString() + "", Toast.LENGTH_SHORT).show()


        }
        if(row_index==position){
            holder.cl_marlaCat.setBackgroundResource(R.drawable.bg_selected_border)
            holder.btn_marlaTitle.setTextColor(Color.parseColor("#FBE736"));
        }
        else
        {
            holder.cl_marlaCat.setBackgroundResource(R.drawable.marla_button_shape)
            holder.btn_marlaTitle.setTextColor(Color.parseColor("#ffffff"));
        }

    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var btn_marlaTitle: TextView
        var cl_marlaCat: ConstraintLayout

        init {
            btn_marlaTitle = itemView.findViewById(R.id.btn_marlaTitle)
            cl_marlaCat = itemView.findViewById(R.id.cl_marlaCat)
        }
    }
}
