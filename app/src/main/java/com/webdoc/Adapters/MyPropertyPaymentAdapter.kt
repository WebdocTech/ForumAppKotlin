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
import java.text.DecimalFormat

class MyPropertyPaymentAdapter(var context: Context, var myPropertyResponse: MyPropertyResponse) :
    RecyclerView.Adapter<MyPropertyPaymentAdapter.MyViewHolder>() {
    var debit= ""
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
     //   val paytype = myPropertyResponse.result.myPropertyDetails.get(position).sellType


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

    val formatter = DecimalFormat("#,###,###")
    val debitformat: String =
        formatter.format(Global.propDetailList.get(position).debit.toInt())
    // val modePayformat: String = formatter.format(Global.propDetailList.get(position).modeOfPayment.to)
    //  val balanceformat: String = formatter.format(Global.propDetailList.get(position).balance.toInt())

    holder.tv_pay_debit!!.setText(debitformat)


    // val modePayformat: String = formatter.format(Global.propDetailList.get(position).modeOfPayment.to)
    //  val balanceformat: String = formatter.format(Global.propDetailList.get(position).balance.toInt())

 //   holder.tv_pay_debit!!.setText(debitformat)

    holder.tv_pay_mode!!.setText(Global.propDetailList.get(position).modeOfPayment)
    holder.tv_pay_date!!.setText(Global.propDetailList.get(position).date)
        if(position==0){
            holder.tv_ins_num!!.setText("-")
            holder.tv_pay_mode!!.setText("Down\nPayment")
        }else{
            holder.tv_pay_mode!!.setText(Global.propDetailList.get(position).modeOfPayment)
            holder.tv_ins_num!!.setText((position).toString())
        }

    //   holder.tv_pay_balance!!.setText(balanceformat)
    holder.tv_pay_transId!!.setText(Global.propDetailList.get(position).transectionId)
    holder.tv_payment_remarks!!.setText(Global.propDetailList.get(position).remarks)




    }

    override fun getItemCount(): Int {
        return Global.propDetailList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tv_pay_debit: TextView? = null
        var tv_pay_mode: TextView? = null
        var tv_pay_date: TextView? = null
        var tv_ins_num: TextView? = null

        // var tv_pay_balance: TextView? = null
        var tv_pay_transId: TextView? = null
        var tv_payment_remarks: TextView? = null

        init {
            tv_pay_debit = itemView.findViewById(R.id.tv_pay_debit)
            tv_pay_mode = itemView.findViewById(R.id.tv_pay_mode)
            tv_pay_date = itemView.findViewById(R.id.tv_pay_date)
            // tv_pay_balance = itemView.findViewById(R.id.tv_pay_balance)
            tv_pay_transId = itemView.findViewById(R.id.tv_pay_transId)
            tv_payment_remarks = itemView.findViewById(R.id.tv_payment_remarks)
            tv_ins_num = itemView.findViewById(R.id.tv_ins_num)

            //            btnAddToCart = itemView.findViewById(R.id.btnAddToCart);
        }
    }
}
