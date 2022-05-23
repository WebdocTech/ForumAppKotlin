package com.webdoc.Adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.webdoc.ApiResponseModels.MyPropertyResponse.MyPropertyResponse
import com.webdoc.ApiResponseModels.MyPropertyResponse.PaymentDetail
import com.webdoc.Essentials.Global
import com.webdoc.theforum.R

class MyPropertyPaymentAdapter(var context: Context, var myPropertyResponse: MyPropertyResponse) :
    RecyclerView.Adapter<MyPropertyPaymentAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.model_my_property_payment, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        //   val debit = myPropertyResponse.result.myPropertyDetails.

        val idlist: ArrayList<Int> = ArrayList()
        val debitlist: ArrayList<String> = ArrayList()
        val creditlist: ArrayList<String> = ArrayList()
        val modeOfPaymentlist: ArrayList<String> = ArrayList()
        val transectionIdlist: ArrayList<String> = ArrayList()
        val addedBylist: ArrayList<String> = ArrayList()
        val datelist: ArrayList<String> = ArrayList()
        val remarkslist: ArrayList<String> = ArrayList()
        val balancelist: ArrayList<String> = ArrayList()


        for (i in myPropertyResponse.result.myPropertyDetails) {

            // debitlistt.add(i.paymentDetails)
            for (j in i.paymentDetails) {
                val id = j.id
                val debit = j.debit
                val credit = j.credit
                val modeOfPayment = j.modeOfPayment
                val transectionId = j.transectionId
                val addedBy = j.addedBy
                val date = j.date
                val remarks = j.remarks
                val balance = j.balance
                idlist.add(id)
                debitlist.add(debit)
                creditlist.add(credit)
                modeOfPaymentlist.add(modeOfPayment)
                transectionIdlist.add(transectionId)
                addedBylist.add(addedBy)
                datelist.add(date)
                remarkslist.add(remarks)
                balancelist.add(balance)
            }
        }




        holder.tv_pay_debit!!.setText(Global.propDetailList.get(position).debit)
        holder.tv_pay_credit!!.setText(Global.propDetailList.get(position).credit)
        holder.tv_pay_date!!.setText(Global.propDetailList.get(position).date)
        holder.tv_pay_balance!!.setText(Global.propDetailList.get(position).balance)
        holder.tv_pay_transId!!.setText(Global.propDetailList.get(position).transectionId)
        holder.tv_payment_remarks!!.setText(Global.propDetailList.get(position).remarks)


    }

    override fun getItemCount(): Int {
        return Global.propDetailList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tv_pay_debit: TextView? = null
        var tv_pay_credit: TextView? = null
        var tv_pay_date: TextView? = null
        var tv_pay_balance: TextView? = null
        var tv_pay_transId: TextView? = null
        var tv_payment_remarks: TextView? = null

        init {
            tv_pay_debit = itemView.findViewById(R.id.tv_pay_debit)
            tv_pay_credit = itemView.findViewById(R.id.tv_pay_credit)
            tv_pay_date = itemView.findViewById(R.id.tv_pay_date)
            tv_pay_balance = itemView.findViewById(R.id.tv_pay_balance)
            tv_pay_transId = itemView.findViewById(R.id.tv_pay_transId)
            tv_payment_remarks = itemView.findViewById(R.id.tv_payment_remarks)

            //            btnAddToCart = itemView.findViewById(R.id.btnAddToCart);
        }
    }
}