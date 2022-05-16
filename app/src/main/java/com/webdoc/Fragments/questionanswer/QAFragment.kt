package com.webdoc.Fragments.questionanswer

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.webdoc.Activities.MainActivity
import com.webdoc.Adapters.QuestionsAdapter
import com.webdoc.theforum.R
import com.webdoc.theforum.databinding.FragmentQaBinding

class QAFragment : Fragment() {
    private lateinit var binding: FragmentQaBinding

    var dialog: Dialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQaBinding.inflate(inflater, container, false)

        initViews()
        clickListeners()
        return binding.root
    }

    private fun clickListeners() {
        binding.clAskQues.setOnClickListener {
            dialog = Dialog(activity as MainActivity)
            dialog!!.setCancelable(false)
            dialog!!.setContentView(R.layout.dialog_ask_ques_layout)
            val btn_done: Button

            btn_done = dialog!!.findViewById<Button>(R.id.btn_done)

            btn_done.setOnClickListener { view: View? ->
                dialog!!.dismiss()
            }
            val window = dialog!!.window
            window!!.setLayout(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
            )
            dialog!!.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            if (dialog != null) {
                dialog!!.show()
            }

        }
    }

    private fun initViews() {
        questionsPopulateRecycler()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Questions/Answers"
    }

    private fun questionsPopulateRecycler() {
        binding.rvQues.setLayoutManager(GridLayoutManager(activity as AppCompatActivity, 1))
        val adapter = QuestionsAdapter(activity as AppCompatActivity)
        binding.rvQues.setAdapter(adapter)
    }

}