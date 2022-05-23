package com.webdoc.Adapters

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.webdoc.ApiResponseModels.MyPropertyResponse.MyPropertyResponse
import com.webdoc.ApiResponseModels.MyPropertyResponse.PaymentDetail
import com.webdoc.Essentials.Global
import com.webdoc.Fragments.myproperty.MyPropDetailActivity
import com.webdoc.theforum.R
import java.text.DecimalFormat

class MyPropertyAdapter(var context: Context, var myPropertyResponse: MyPropertyResponse) :
    RecyclerView.Adapter<MyPropertyAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.model_my_property, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val id = myPropertyResponse.result!!.myPropertyDetails.get(position).id
        val name = myPropertyResponse.result!!.myPropertyDetails.get(position).name
        val description = myPropertyResponse.result!!.myPropertyDetails.get(position).discription
        val pricePerSquareFoot =
            myPropertyResponse.result!!.myPropertyDetails.get(position).pricePerSquareFoot
        val pricePerSquareFootDiscount =
            myPropertyResponse.result!!.myPropertyDetails.get(position).pricePerSquareFootDiscount
        val areainSquareFoot =
            myPropertyResponse.result!!.myPropertyDetails.get(position).areainSquareFoot
        val areainlength =
            myPropertyResponse.result!!.myPropertyDetails.get(position).areainlength
        val paymentandfloorplan =
            myPropertyResponse.result!!.myPropertyDetails.get(position).paymentandfloorplan
        val projectName = myPropertyResponse.result!!.myPropertyDetails.get(position).projectName
        val projectCompany =
            myPropertyResponse.result!!.myPropertyDetails.get(position).projectCompany
        val purchaseDate =
            myPropertyResponse.result!!.myPropertyDetails.get(position).purchaseDate
        val nextPaymentDate =
            myPropertyResponse.result!!.myPropertyDetails.get(position).nextPaymentDate
        val sellType = myPropertyResponse.result!!.myPropertyDetails.get(position).sellType
        val status = myPropertyResponse.result!!.myPropertyDetails.get(position).status
        val paymentStatus =
            myPropertyResponse.result!!.myPropertyDetails.get(position).paymentStatus
        val totalPayment =
            myPropertyResponse.result!!.myPropertyDetails.get(position).totalPayment
        val totalInstallment =
            myPropertyResponse.result!!.myPropertyDetails.get(position).totalInstallment
        val paidInstallment =
            myPropertyResponse.result!!.myPropertyDetails.get(position).paidInstallment
        val remainingInstallment =
            myPropertyResponse.result!!.myPropertyDetails.get(position).remainingInstallment

        if (sellType.equals("Installment")) {
            holder.cl_installment!!.visibility = View.VISIBLE
            holder.cl_status!!.visibility = View.GONE
        } else if (sellType.equals("Full Payment")) {
            holder.cl_installment!!.visibility = View.GONE
            holder.cl_status!!.visibility = View.VISIBLE
            holder.tv_myProp_nextPDate!!.visibility = View.GONE
            holder.tv_myProp_nextPayDate!!.visibility = View.GONE
            holder.tv_status_myProp!!.setTextColor(Color.GREEN)
        }



        holder.tv_myProp_name!!.setText(name)
        holder.tv_myProp_description!!.setText(description)
        holder.tv_myProp_purchDate!!.setText(purchaseDate)
        holder.tv_myProp_nextPDate!!.setText(nextPaymentDate)
        holder.tv_myprop_seltype!!.setText(sellType)
        holder.tv_myProp_status!!.setText("Status:\t" + status)
        holder.tv_status_myProp!!.setText("Status:\t" + status)
        holder.tv_total_installment!!.setText("Installment:\t" + totalInstallment)
        holder.tv_paid_installment!!.setText("Paid:\t" + paidInstallment)
        holder.tv_remaining_installment!!.setText("Remaining:\t" + remainingInstallment)


        //   Global.paymentDetail = myPropertyResponse
        var list: ArrayList<PaymentDetail> = ArrayList()
        for (i in myPropertyResponse.result.myPropertyDetails.get(position).paymentDetails) {

            list.add(i)

        }

        holder.itemView.setOnClickListener {

            var arraylist: ArrayList<PaymentDetail> = ArrayList()

            val intent = Intent(context, MyPropDetailActivity::class.java)

            //    arraylist.add()
            intent.putExtra("id", id)
            intent.putExtra("name", name)
            intent.putExtra("description", description)
            intent.putExtra("pricePerSquareFoot", pricePerSquareFoot)
            intent.putExtra("pricePerSquareFootDiscount", pricePerSquareFootDiscount)
            intent.putExtra("areainSquareFoot", areainSquareFoot)
            intent.putExtra("areainlength", areainlength)
            intent.putExtra("paymentandfloorplan", paymentandfloorplan)
            intent.putExtra("projectName", projectName)
            intent.putExtra("projectCompany", projectCompany)
            intent.putExtra("purchaseDate", purchaseDate)
            intent.putExtra("nextPaymentDate", nextPaymentDate)
            intent.putExtra("sellType", sellType)
            intent.putExtra("status", status)
            intent.putExtra("paymentStatus", paymentStatus)
            intent.putExtra("totalPayment", totalPayment)
            intent.putExtra("totalInstallment", totalInstallment)
            intent.putExtra("paidInstallment", paidInstallment)
            intent.putExtra("remainingInstallment", remainingInstallment)
            context.startActivity(intent)
            Global.position = position
            Global.propDetailList = list

        }

    }

    override fun getItemCount(): Int {
        return myPropertyResponse!!.result!!.myPropertyDetails.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tv_myProp_name: TextView? = null
        var tv_myProp_description: TextView? = null
        var tv_myProp_purchDate: TextView? = null
        var tv_myProp_nextPDate: TextView? = null
        var tv_myprop_seltype: TextView? = null
        var tv_myProp_status: TextView? = null
        var tv_status_myProp: TextView? = null
        var tv_total_installment: TextView? = null
        var tv_paid_installment: TextView? = null
        var tv_remaining_installment: TextView? = null
        var tv_myProp_nextPayDate: TextView? = null
        var cl_status: ConstraintLayout? = null
        var cl_installment: ConstraintLayout? = null

        init {
            tv_myProp_name = itemView.findViewById(R.id.tv_myProp_name)
            tv_myProp_description = itemView.findViewById(R.id.tv_myProp_description)
            tv_myProp_purchDate = itemView.findViewById(R.id.tv_myProp_purchDate)
            tv_myProp_nextPDate = itemView.findViewById(R.id.tv_myProp_nextPDate)
            tv_myprop_seltype = itemView.findViewById(R.id.tv_myprop_seltype)
            tv_myProp_status = itemView.findViewById(R.id.tv_myProp_status)
            tv_total_installment = itemView.findViewById(R.id.tv_total_installment)
            tv_paid_installment = itemView.findViewById(R.id.tv_paid_installment)
            tv_remaining_installment = itemView.findViewById(R.id.tv_remaining_installment)
            tv_status_myProp = itemView.findViewById(R.id.tv_status_myProp)
            cl_status = itemView.findViewById(R.id.cl_status)
            cl_installment = itemView.findViewById(R.id.cl_installment)
            tv_myProp_nextPayDate = itemView.findViewById(R.id.tv_myProp_nextPayDate)
            //            btnAddToCart = itemView.findViewById(R.id.btnAddToCart);
        }
    }
}
