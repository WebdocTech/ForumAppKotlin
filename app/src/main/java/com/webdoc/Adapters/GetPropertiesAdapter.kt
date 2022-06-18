package com.webdoc.Adapters

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.webdoc.ApiResponseModels.GetPropertiesResponse.GetPropertiesResponse
import com.webdoc.Fragments.home.PropertyDetailActivity
import com.webdoc.theforum.R
import java.text.DecimalFormat


class GetPropertiesAdapter(var context: Context, var getPropResponse: GetPropertiesResponse) :
    RecyclerView.Adapter<GetPropertiesAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.model_getproperties_items, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val propid = getPropResponse!!.result!!.get(position).id
        val name = getPropResponse!!.result!!.get(position).name
        val description = getPropResponse!!.result!!.get(position).discription
        val pricePerSquareFoot = getPropResponse!!.result!!.get(position).pricePerSquareFoot
        val pricePerSquareFootDiscount =
            getPropResponse!!.result!!.get(position).pricePerSquareFootDiscount
        val areainSquareFoot = getPropResponse!!.result!!.get(position).areainSquareFoot
        val areainlength = getPropResponse!!.result!!.get(position).areainlength
        val discountInPercent = getPropResponse!!.result!!.get(position).discountInPercent
        val downPayment = getPropResponse!!.result!!.get(position).downPayment
        val quarterPayment = getPropResponse!!.result!!.get(position).quarterPayment
        val totalAmount = getPropResponse!!.result!!.get(position).totalAmount
        val discountedAmount = getPropResponse!!.result!!.get(position).discountedAmount
        val paymentandfloorplan = getPropResponse!!.result!!.get(position).paymentandfloorplan
        val projectName = getPropResponse!!.result!!.get(position).projectName
        val projectCompany = getPropResponse!!.result!!.get(position).projectCompany
        val status = getPropResponse!!.result!!.get(position).status

        val formatter = DecimalFormat("#,###,###")
        val totalpayformat: String =
            formatter.format(getPropResponse!!.result!!.get(position).totalAmount!!.toInt())
        val discountAmount: String = formatter.format(discountedAmount!!.toInt())

        if (status.equals("Availabe")) {
            holder.tv_disc_price!!.visibility = View.VISIBLE
            holder.tv_prop_price!!.visibility = View.VISIBLE
        } else if (status.equals("Partial Sold")) {
            holder.tv_sold!!.visibility = View.VISIBLE
            holder.tv_prop_discount!!.visibility = View.GONE
            holder.iv_property!!.alpha = 0.5f
            holder.tv_descrip!!.setText(description)
            holder.tv_descrip!!.visibility = View.VISIBLE
        }
        holder.tv_prop_name!!.setText(
            getPropResponse!!.result!!.get(position).name
        )
        holder.tv_prop_price!!.setText(
            "Rs:" + "\t" + totalpayformat + "/-"
        )
        holder.tv_disc_price!!.setText(
            "Rs:" + "\t" + discountAmount + "/-"
        )
        holder.tv_prop_price!!.setPaintFlags(holder.tv_prop_price!!.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)
        holder.tv_prop_area!!.setText("Area" + "\t" + getPropResponse!!.result!!.get(position).areainSquareFoot + "\tsq")
        holder.tv_prop_discount!!.setText(
            "Discount" + "\t" + getPropResponse!!.result!!.get(
                position
            ).discountInPercent + "%"
        )

        holder.itemView.setOnClickListener {
//                    val bundle = Bundle()
//                    bundle.putString(Global.APARTMENT_ID_KEY, id!!)
//                    val internetWazifaDetailsFragment = PropertyDetailFragment.newInstance()
//                    internetWazifaDetailsFragment.arguments = bundle
            val intent = Intent(context, PropertyDetailActivity::class.java)


            intent.putExtra("id", propid)

            intent.putExtra("name", name)
            intent.putExtra("description", description)
            intent.putExtra("pricePerSquareFoot", pricePerSquareFoot)
            intent.putExtra("pricePerSquareFootDiscount", pricePerSquareFootDiscount)
            intent.putExtra("areainSquareFoot", areainSquareFoot)
            intent.putExtra("areainlength", areainlength)
            intent.putExtra("discountInPercent", discountInPercent)
            intent.putExtra("downPayment", downPayment)
            intent.putExtra("quarterPayment", quarterPayment)
            intent.putExtra("totalAmount", totalAmount)
            intent.putExtra("discountedAmount", discountedAmount)
            intent.putExtra("paymentandfloorplan", paymentandfloorplan)
            intent.putExtra("projectName", projectName)
            intent.putExtra("projectCompany", projectCompany)
            intent.putExtra("status", status)

            context.startActivity(intent)


        }
    }

    override fun getItemCount(): Int {
        return getPropResponse!!.result!!.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var btnAddToCart: Button? = null
        var tv_prop_name: TextView? = null
        var tv_prop_price: TextView? = null
        var tv_prop_area: TextView? = null
        var tv_prop_discount: TextView? = null
        var tv_disc_price: TextView? = null
        var tv_sold: TextView? = null
        var iv_property: ImageView? = null
        var tv_descrip: TextView? = null


        init {

            tv_prop_name = itemView.findViewById(R.id.tv_prop_name)
            tv_prop_price = itemView.findViewById(R.id.tv_prop_price)
            tv_prop_area = itemView.findViewById(R.id.tv_prop_area)
            tv_prop_discount = itemView.findViewById(R.id.tv_prop_discount)
            tv_disc_price = itemView.findViewById(R.id.tv_disc_price)
            tv_sold = itemView.findViewById(R.id.tv_sold)
            iv_property = itemView.findViewById(R.id.iv_property)
            tv_descrip = itemView.findViewById(R.id.tv_descrip)

        }
    }
}
