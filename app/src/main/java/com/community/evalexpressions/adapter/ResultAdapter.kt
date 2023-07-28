package com.community.evalexpressions.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.community.evalexpressions.databinding.ChildHistoryBinding

class ResultAdapter : RecyclerView.Adapter<ResultAdapter.ResultViewHolder>() {

    private var data: List<String> = ArrayList()

    fun setData(data: List<String>) {
        this.data = data
        notifyDataSetChanged()
    }

    //Binding the child layout to recycler view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        val binding = ChildHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ResultViewHolder(binding)
    }

    //binding the data
    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    //returning the data size or count
    override fun getItemCount(): Int {
        return data.size
    }

    //binding the result into respective views
    class ResultViewHolder(private val binding: ChildHistoryBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: String) {
            binding.txtExpression.text = item
            binding.txtResult.visibility = View.GONE
            binding.txtTime.visibility = View.GONE
        }
    }
}
