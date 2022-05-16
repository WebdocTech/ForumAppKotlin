package com.webdoc.Adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.webdoc.ModelClasses.PropertySubCatModel
import com.webdoc.theforum.R
import java.util.*

class PropertySubCatAdapter(var mContext: Context, var arrayList: ArrayList<PropertySubCatModel>) :
    RecyclerView.Adapter<PropertySubCatAdapter.MyViewHolder>() {

    var row_index: Int = -1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.model_property_subcat, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.iv_subCatProperty.setImageResource(arrayList[position].getImage())
        holder.tv_subCatProperty.setText(arrayList[position].getTitle())

        holder.itemView.setOnClickListener {

            //  expertiesList.questionanswer(arrayList[position].getTitle())
            if (holder.cl_propSubCat.getBackground()
                    .getConstantState() === mContext.resources.getDrawable(R.drawable.bg_selected_border).constantState
            ){
                holder.cl_propSubCat.setBackgroundResource(R.drawable.marla_button_shape)
                holder.iv_subCatProperty.setColorFilter(Color.WHITE)
                holder.tv_subCatProperty.setTextColor(Color.parseColor("#ffffff"));
            }else{
                row_index = position;
                notifyDataSetChanged();
            }

            //  Toast.makeText(mContext, expertiesList.size.toString() + "", Toast.LENGTH_SHORT).show()


        }
        if(row_index==position){
            holder.iv_subCatProperty.setColorFilter(Color.YELLOW)
            holder.cl_propSubCat.setBackgroundResource(R.drawable.bg_selected_border)
            holder.tv_subCatProperty.setTextColor(Color.parseColor("#FBE736"));
        }
        else
        {
            holder.cl_propSubCat.setBackgroundResource(R.drawable.marla_button_shape)
            holder.iv_subCatProperty.setColorFilter(Color.WHITE)
            holder.tv_subCatProperty.setTextColor(Color.parseColor("#ffffff"));
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var iv_subCatProperty: ImageView
        var tv_subCatProperty: TextView
         var cl_propSubCat: ConstraintLayout

        init {
            iv_subCatProperty = itemView.findViewById(R.id.iv_subCatProperty)
            tv_subCatProperty = itemView.findViewById(R.id.tv_subCatProperty)
            cl_propSubCat = itemView.findViewById(R.id.cl_propSubCat)
        }
    }
}
