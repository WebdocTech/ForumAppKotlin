package com.webdoc.Adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.webdoc.ModelClasses.PropertyTypeModel
import com.webdoc.theforum.R
import java.util.*

class PropertyTypeAdapter(var mContext: Context, var arrayList: ArrayList<PropertyTypeModel>) :
    RecyclerView.Adapter<PropertyTypeAdapter.MyViewHolder>() {
    var row_index: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.model_property_type, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.btn_propertyTypeTitle.text = arrayList[position].propertytypetitle
        holder.itemView.setOnClickListener {

            if (holder.cl_propertyType.getBackground()
                    .getConstantState() === mContext.resources.getDrawable(R.drawable.bg_selected).constantState
            ){
                holder.cl_propertyType.setBackgroundResource(R.drawable.marla_button_shape)
                holder.btn_propertyTypeTitle.setTextColor(Color.parseColor("#ffffff"));
            }else{
                row_index = position;
                notifyDataSetChanged();
            }


        }
        if(row_index==position){
            holder.cl_propertyType.setBackgroundResource(R.drawable.bg_selected)
            holder.btn_propertyTypeTitle.setTextColor(Color.parseColor("#000000"));
        }
        else
        {
            holder.cl_propertyType.setBackgroundResource(R.drawable.marla_button_shape)
            holder.btn_propertyTypeTitle.setTextColor(Color.parseColor("#ffffff"));
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var btn_propertyTypeTitle: TextView
         var cl_propertyType: ConstraintLayout

        init {
            btn_propertyTypeTitle = itemView.findViewById(R.id.btn_propertyTypeTitle)
            cl_propertyType = itemView.findViewById(R.id.cl_propertyType)
        }
    }
}
