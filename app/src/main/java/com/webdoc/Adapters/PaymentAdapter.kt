package com.webdoc.Adapters

import android.app.Activity
import android.content.Intent
import android.database.DataSetObserver
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.webdoc.Essentials.Global
import com.webdoc.payments.BankAlfalahCreditDebit.BankAlfalahCreditDebitActivity
import com.webdoc.payments.BankAlfalahWallet.BankAlfalahWalletActivity
import com.webdoc.payments.PaymentMethods.BankAlfalahAccount.BankAlfalahAccountActivity
import com.webdoc.theforum.R
import com.webdoc.webviewlibaray.JsBankWallet.JSWalletActivities.JsBankActivity

class PaymentAdapter(var context: Activity) : RecyclerView.Adapter<PaymentAdapter.ViewHolder>(),
    Adapter {
    var checked = false
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.payment_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val myListData = Global.paymentTitle[position]
        val image: Int = myListData.image
        val title: String = myListData.title
        holder.iv_payment.setImageResource(image)
        holder.tv_title.text = title
        if (position % 2 == 1) {
            holder.MainLayout.setBackgroundResource(R.drawable.border_rectangle_white)
        } else {
            holder.MainLayout.setBackgroundResource(R.drawable.border_rectangle_gray)
        }
        holder.MainLayout.setOnClickListener {
            if (title == "Bank Alfalah Account") {
                val intent = Intent(context, BankAlfalahAccountActivity::class.java)
                context.startActivity(intent)
            } else if (title == "Bank Alfalah Wallet") {
                val intent = Intent(context, BankAlfalahWalletActivity::class.java)
                context.startActivity(intent)
            } else if (title == "Credit/Debit Card") {
                val intent = Intent(context, BankAlfalahCreditDebitActivity::class.java)
                context.startActivity(intent)
            } else if (title == "Js Wallet") {
                val intent = Intent(context, JsBankActivity::class.java) //webview
                context.startActivity(intent)
            }/* else if (title == "Jazz Cash Wallet") {
                val intent = Intent(context, JazzCashPaymentActivity::class.java)
                context.startActivity(intent)
            } else if (title == "Easy Paisa Wallet") {
                val intent = Intent(context, EasyPaisaActivity::class.java)
                context.startActivity(intent)
            } else if (title == "Js Wallet") {
                val intent = Intent(context, JsBankActivity::class.java) //webview
                context.startActivity(intent)
            }
            else if (title == "OTC Through EasyPaisa") {
                val intent = Intent(context, OtcPaymentActivity::class.java)
                context.startActivity(intent)
            }
            else if (title == "Easy Paisa Credit Debit") {
                val intent = Intent(context, EasyPaisaCreditDebitActivity::class.java) //webview
                context.startActivity(intent)
            }*/
            /*   else if (title == "Credit/Debit") {
                    val intent = Intent(context, EasyPaisaCreditDebitActivity::class.java) //webview
                    context.startActivity(intent)
                } else if (title == "temp") {
                    val intent = Intent(context, CallCourier_EQ::class.java)
                    intent.putExtra("appointment_method", "")
                    intent.putExtra("trx_id", "")
                    intent.putExtra("bank_name", "")
                    intent.putExtra("payment_status", "Pending")
                    context.finish()
                    context.startActivity(intent)
                }*/
        }
    }

    override fun getItemCount(): Int {
        return Global.paymentTitle.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var iv_payment: ImageView
        var MainLayout: ConstraintLayout
        var tv_title: TextView

        init {
            iv_payment = itemView.findViewById(R.id.iv_payment)
            MainLayout = itemView.findViewById(R.id.MainLayout)
            tv_title = itemView.findViewById(R.id.tv_title)
        }
    }

    override fun registerDataSetObserver(p0: DataSetObserver?) {
        TODO("Not yet implemented")
    }

    override fun unregisterDataSetObserver(p0: DataSetObserver?) {
        TODO("Not yet implemented")
    }

    override fun getCount(): Int {
        TODO("Not yet implemented")
    }

    override fun getItem(p0: Int): Any {
        TODO("Not yet implemented")
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        TODO("Not yet implemented")
    }

    override fun getViewTypeCount(): Int {
        TODO("Not yet implemented")
    }

    override fun isEmpty(): Boolean {
        TODO("Not yet implemented")
    }
}
