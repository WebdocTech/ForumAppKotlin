package com.webdoc.Fragments.questionanswer

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.webdoc.Activities.MainActivity
import com.webdoc.Adapters.QuestionsAdapter
import com.webdoc.Essentials.Global
import com.webdoc.Essentials.PreferencesNew
import com.webdoc.Fragments.questionanswer.ViewModel.AddQuestionViewModel
import com.webdoc.theforum.R
import com.webdoc.theforum.databinding.FragmentQaBinding

class QAFragment : Fragment() {
    private lateinit var binding: FragmentQaBinding
    var addQuestion: String? = null
    var questionsAdapters: QuestionsAdapter? = null
    var dialog: Dialog? = null
    private var userid: String = ""
    private lateinit var prefs: SharedPreferences
    private lateinit var edit: SharedPreferences.Editor
    var addQuestionViewModel: AddQuestionViewModel? = null
    private var contxt: Context? = null
    private lateinit var qaFragmentManager: FragmentManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQaBinding.inflate(inflater, container, false)
        contxt = container!!.getContext()
        initViews()
        clickListeners()
        observer()
        return binding.root
    }

    private fun observer() {
        addQuestionViewModel!!.LDAQA().observe(activity as MainActivity) { response ->
            if (response != null) {
                if (response.result!!.responseCode.equals("0000")) {
                    Toast.makeText(
                        activity as MainActivity,
                        "Question Added",
                        Toast.LENGTH_SHORT
                    ).show()
                    addQuestionViewModel!!.callGetQuestionApi(userid)
                    dialog!!.dismiss()
                    Global.utils!!.hideCustomLoadingDialog()
                } else {
                    Toast.makeText(
                        activity as MainActivity,
                        "Question not Added!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }
        }

        addQuestionViewModel!!.LDGQA().observe(activity as MainActivity) { response ->
            if (response != null) {
                if (response.result!!.responseCode.equals("0000")) {
                    val layoutManager = LinearLayoutManager(contxt)
                    layoutManager.setReverseLayout(true);
                    layoutManager.setStackFromEnd(true);
                    questionsAdapters = QuestionsAdapter(contxt!!, response)
                    binding.rvQues.setLayoutManager(layoutManager)
                    binding.rvQues.setAdapter(questionsAdapters)
                    questionsAdapters!!.notifyDataSetChanged()
                    binding.tvNoQA.visibility = View.GONE
                    Global.utils!!.hideCustomLoadingDialog()

                } else {
                    Toast.makeText(
                        contxt, "Please Ask Questions!", Toast.LENGTH_SHORT
                    ).show()
                    binding.tvNoQA.visibility = View.VISIBLE
                    Global.utils!!.hideCustomLoadingDialog()
                }

            }
        }
    }

    private fun clickListeners() {
        binding.clAskQues.setOnClickListener {
            dialog = Dialog(contxt!!)
            dialog!!.setCancelable(true)
            dialog!!.setContentView(R.layout.dialog_ask_ques_layout)
            val btn_done: Button
            val edt_QA: EditText

            btn_done = dialog!!.findViewById<Button>(R.id.btn_done)
            edt_QA = dialog!!.findViewById<EditText>(R.id.edt_QA)


            btn_done.setOnClickListener { view: View? ->

                addQuestion = edt_QA.getText().toString()
                if (addQuestion!!.isEmpty()) {
                    edt_QA.setError("Please Write Question")
                } else {
                    Global.utils!!.showCustomLoadingDialog(activity as MainActivity)
                    addQuestionViewModel!!.calAddQuestionApi(addQuestion, userid)
                    //   (activity as MainActivity).recreate()

                    //loadFragment(activity as MainActivity)
                }
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
        qaFragmentManager = (activity as MainActivity).supportFragmentManager
        prefs = (activity as MainActivity).getSharedPreferences("abc", Context.MODE_PRIVATE)
        edit = prefs.edit()
        userid = prefs.getString(PreferencesNew.KEY_ApplicationUserId, "").toString()
        // Global.id = userid

        addQuestionViewModel = ViewModelProvider(this).get(AddQuestionViewModel::class.java)
        if ((activity as MainActivity).isOnline(contxt!!)) {
            Global.utils!!.showCustomLoadingDialog(activity as MainActivity)
            addQuestionViewModel!!.callGetQuestionApi(userid)
        } else {
            Toast.makeText(
                activity as MainActivity, "Check Internet",
                Toast.LENGTH_SHORT
            ).show()
        }


        //  questionsPopulateRecycler()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Questions/Answers"
    }

    //    private fun questionsPopulateRecycler() {
//        binding.rvQues.setLayoutManager(GridLayoutManager(activity as AppCompatActivity, 1))
//        val adapter = QuestionsAdapter(activity as AppCompatActivity)
//        binding.rvQues.setAdapter(adapter)
//    }
    private fun loadFragment(context: Context?) {
        context?.let {
            val fragmentManager = (context as? AppCompatActivity)?.supportFragmentManager
            fragmentManager?.let {
                val currentFragment = fragmentManager.findFragmentById(R.id.nav_fragment)
                currentFragment?.let {
                    val fragmentTransaction = fragmentManager.beginTransaction()
                    fragmentTransaction.detach(it)
                    fragmentTransaction.attach(it)
                    fragmentTransaction.commit()
                }
            }
        }
        // load fragment
//    qaFragmentManager.beginTransaction()
//        .replace(R.id.nav_fragment, fragment, fragment.javaClass.name)
//        .addToBackStack(fragment.javaClass.name).detach(fragment).commit()

    }
}