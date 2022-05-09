package com.webdoc.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.webdoc.Fragments.home.PropertyDetailActivity
import com.webdoc.ModelClasses.AppartmentsModel
import com.webdoc.theforum.R
import java.util.*


class PriceSubCategoryAdapter(
    var mContext: Context,
    var arrayList: ArrayList<AppartmentsModel>
) :
    RecyclerView.Adapter<PriceSubCategoryAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.model_price_subcategory, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.iv_appartment.setImageResource(arrayList[position].getImage())
        holder.tv_appartmentTitle.setText(arrayList[position].getTitle())
        holder.tv_appartmentPrice.setText(arrayList[position].getPrice())
        holder.tv_appartmentDate.setText(arrayList[position].getDate())
        holder.tv_appartmentTime.setText(arrayList[position].getTime())

        val priceModel = arrayList[position]
//        val title=priceModel.appartmenttitle
//        val price=priceModel.appartmentPrice
//        val date=priceModel.appartmentDate
//        val time=priceModel.appartmentTime
        val id=priceModel.appartmentId

                holder.itemView.setOnClickListener {
//                    val bundle = Bundle()
//                    bundle.putString(Global.APARTMENT_ID_KEY, id!!)
//                    val internetWazifaDetailsFragment = PropertyDetailFragment.newInstance()
//                    internetWazifaDetailsFragment.arguments = bundle
                    val intent = Intent(mContext, PropertyDetailActivity::class.java)
                    mContext.startActivity(intent)


        }

    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv_appartmentTitle: TextView
        var tv_appartmentPrice: TextView
        var tv_appartmentDate: TextView
        var tv_appartmentTime: TextView
        var iv_appartment: ImageView

        init {
            tv_appartmentTitle = itemView.findViewById(R.id.tv_appartmentTitle)
            tv_appartmentPrice = itemView.findViewById(R.id.tv_appartmentPrice)
            tv_appartmentDate = itemView.findViewById(R.id.tv_appartmentDate)
            tv_appartmentTime = itemView.findViewById(R.id.tv_appartmentTime)
            iv_appartment = itemView.findViewById(R.id.iv_appartment)
        }
    }

}
