package com.community.evalexpressions.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.community.evalexpressions.database.HistoryData
import com.community.evalexpressions.databinding.ChildHistoryBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HistoryAdapter : ListAdapter<HistoryData, HistoryAdapter.HistoryViewHolder>(
    HistoryDiffCallback()
) {

    //binding the child layout to adapter
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = ChildHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class HistoryViewHolder(private val binding: ChildHistoryBinding) : RecyclerView.ViewHolder(binding.root) {

        //bind the result in the respected views
        fun bind(item: HistoryData) {
            binding.txtExpression.text = item.expression + " ="
            binding.txtResult.text = item.result
            binding.txtTime.text = formatDate(item.timestamp)
        }

        //Date and Time format
        private fun formatDate(timestamp: Long): String {
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val date = Date(timestamp)
            return sdf.format(date)
        }
    }

    class HistoryDiffCallback : DiffUtil.ItemCallback<HistoryData>() {
        override fun areItemsTheSame(oldItem: HistoryData, newItem: HistoryData): Boolean {
            return oldItem.timestamp == newItem.timestamp
        }

        override fun areContentsTheSame(oldItem: HistoryData, newItem: HistoryData): Boolean {
            return oldItem == newItem
        }
    }
}
