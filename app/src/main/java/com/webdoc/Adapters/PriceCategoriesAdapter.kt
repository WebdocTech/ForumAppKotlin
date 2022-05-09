package com.webdoc.Adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.webdoc.Activities.MainActivity
import com.webdoc.Fragments.home.PriceCategoryFragment
import com.webdoc.ModelClasses.PriceCategories
import com.webdoc.theforum.R
import java.util.*

class PriceCategoriesAdapter(
    var mContext: Context,
    var arrayList: ArrayList<PriceCategories>
) :
    RecyclerView.Adapter<PriceCategoriesAdapter.MyViewHolder>() {

    private lateinit var fragmentManager: FragmentManager
    var row_index: Int = -1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.model_price_category, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val priceModel = arrayList[position]
        val title= priceModel.pricetitle;
        holder.btn_priceTitle.text = title
        holder.itemView.setOnClickListener {

            if (holder.cl_priceCat.getBackground()
                    .getConstantState() === mContext.resources.getDrawable(R.drawable.bg_price_border).constantState
            ){
                holder.cl_priceCat.setBackgroundResource(R.drawable.edittext_shape)
                holder.btn_priceTitle.setTextColor(Color.parseColor("#ffffff"));
            }else{
                row_index = position;
                notifyDataSetChanged();
                if(title.equals("Low Price")){

                    (mContext as MainActivity).loadFragment(PriceCategoryFragment.newInstance(title))
                }else if(title.equals("High Price")){

                    (mContext as MainActivity).loadFragment(PriceCategoryFragment.newInstance(title))
                }
                else if(title.equals("Installment")){

                    (mContext as MainActivity).loadFragment(PriceCategoryFragment.newInstance(title))
                }
            }
        }
        if(row_index==position){
            holder.cl_priceCat.setBackgroundResource(R.drawable.bg_price_border)
            holder.btn_priceTitle.setTextColor(Color.parseColor("#FBE736"));
        }
        else
        {
            holder.cl_priceCat.setBackgroundResource(R.drawable.edittext_shape)
            holder.btn_priceTitle.setTextColor(Color.parseColor("#ffffff"));
        }
//        holder.btn_priceTitle.setOnClickListener {
//
//        //    val mainActivity: MainActivity = mContext as MainActivity
//
//
//        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var btn_priceTitle: TextView
        lateinit var cl_priceCat: ConstraintLayout
        init {
            btn_priceTitle = itemView.findViewById(R.id.btn_priceTitle)
            cl_priceCat = itemView.findViewById(R.id.cl_priceCat)
        }
    }

}
