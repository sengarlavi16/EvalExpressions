package com.community.evalexpressions

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.community.evalexpressions.adapter.HistoryAdapter
import com.community.evalexpressions.adapter.ResultAdapter
import com.community.evalexpressions.api.viewmodel.EvaluateViewModel
import com.community.evalexpressions.databinding.ActivityMainBinding
import com.community.evalexpressions.utils.CustomProgressDialog

class MainActivity : AppCompatActivity() {

    //initializing all the required classes
    private lateinit var progressDialog: CustomProgressDialog
    private lateinit var binding: ActivityMainBinding
    private val viewModel: EvaluateViewModel by viewModels()
    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var resultAdapter: ResultAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_main)

        //defining the progress bar
        progressDialog = CustomProgressDialog(this)

        //defining the history and result adapter in recycler view layout
        historyAdapter = HistoryAdapter()
        binding.recyHistory.layoutManager = LinearLayoutManager(this)
        binding.recyHistory.adapter = historyAdapter

        resultAdapter = ResultAdapter()
        binding.recyresult.layoutManager = LinearLayoutManager(this)
        binding.recyresult.adapter = resultAdapter

        //handling the submit onclick
        binding.txtSubmit.setOnClickListener {
            if(binding.edtExpressions.text.isEmpty()){
                Toast.makeText(this, "Please enter expressions to evaluate", Toast.LENGTH_SHORT).show()
            }else {
                binding.consResult.visibility = View.VISIBLE
                ChangeTabColor(binding.txtResult, binding.recyresult)
            }

            //handling the result onclick
            binding.txtResult.setOnClickListener(View.OnClickListener {
                ChangeTabColor(binding.txtResult, binding.recyresult)
            })

            //handling the history onclick
            binding.txtHistory.setOnClickListener(View.OnClickListener {
                ChangeTabColor(binding.txtHistory, binding.recyHistory)
            })

            val expressions = binding.edtExpressions.text.toString().trim().split("\n")
            viewModel.evaluateExpressions(expressions, progressDialog)
        }

        //setting the response in history and result adapter
        viewModel.history.observe(this) { historyItems ->
            historyAdapter.submitList(historyItems)
        }

        viewModel.evaluate.observe(this) { evaluateResponse ->
            if (evaluateResponse.error != null) {
                Toast.makeText(this, "Error: Please enter valid expressions", Toast.LENGTH_SHORT).show()
            } else {
                val evaluateItems = evaluateResponse.results
                resultAdapter.setData(evaluateItems)
            }
        }
    }

    // function to change the tab color and its data onclick
    fun ChangeTabColor(textView: TextView, recyclerView: RecyclerView) {
        binding.txtResult.setTextColor(resources.getColor(R.color.black))
        binding.txtHistory.setTextColor(resources.getColor(R.color.black))
        binding.txtResult.setBackgroundColor(resources.getColor(R.color.white))
        binding.txtHistory.setBackgroundColor(resources.getColor(R.color.white))

        binding.recyHistory.visibility = View.GONE
        binding.recyresult.visibility = View.GONE

        textView.setTextColor(resources.getColor(R.color.white))
        textView.setBackgroundColor(resources.getColor(R.color.black))
        recyclerView.visibility = View.VISIBLE
    }
}
