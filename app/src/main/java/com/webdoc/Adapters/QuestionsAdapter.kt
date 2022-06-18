package com.webdoc.Adapters

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.webdoc.ApiResponseModels.GetPropertiesResponse.GetPropertiesResponse
import com.webdoc.ApiResponseModels.GetQuestionsResponse.GetQuestionsResponse
import com.webdoc.Essentials.Global
import com.webdoc.Fragments.home.PropertyDetailActivity
import com.webdoc.theforum.R


class QuestionsAdapter(var context: Context, var getQAResponse: GetQuestionsResponse) :
    RecyclerView.Adapter<QuestionsAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.model_questions, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val question = getQAResponse!!.result!!.questionDetials.get(position).question
        val answer = getQAResponse!!.result!!.questionDetials.get(position).answer
        val status = getQAResponse!!.result!!.questionDetials.get(position).status
        val questionDate = getQAResponse!!.result!!.questionDetials.get(position).dateofQuestion
        val answerDate = getQAResponse!!.result!!.questionDetials.get(position).dateofAnswer

        holder.tv_question!!.setText(question + "?")
        holder.tv_answer!!.setText(answer)
        holder.tv_status!!.setText(status)
        holder.tv_date_question!!.setText(questionDate)
        if (status.equals("Pending")) {
            holder.tv_date_answer!!.visibility = View.GONE
            holder.tv_answer!!.visibility = View.GONE
            holder.tv_status!!.setTextColor(Color.GRAY)
        } else if (status.equals("Answered")) {
            holder.tv_date_answer!!.visibility = View.VISIBLE
            holder.tv_answer!!.visibility = View.VISIBLE
            holder.tv_status!!.setTextColor(Color.GREEN)
            holder.tv_date_answer!!.setText(answerDate)
        }
        Global.answerStatus = status
    }

    override fun getItemCount(): Int {
        return getQAResponse!!.result!!.questionDetials.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        var tv_question: TextView? = null
        var tv_status: TextView? = null
        var tv_date_question: TextView? = null
        var tv_answer: TextView? = null
        var tv_date_answer: TextView? = null

        init {
            tv_question = itemView.findViewById(R.id.tv_question)
            tv_status = itemView.findViewById(R.id.tv_status)
            tv_date_question = itemView.findViewById(R.id.tv_date_question)
            tv_answer = itemView.findViewById(R.id.tv_answer)
            tv_date_answer = itemView.findViewById(R.id.tv_date_answer)
        }
    }
}
