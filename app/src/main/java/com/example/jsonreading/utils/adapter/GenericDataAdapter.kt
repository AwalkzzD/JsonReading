package com.example.jsonreading.utils.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.jsonreading.BR
import com.example.jsonreading.data.person.Person

class GenericDataAdapter<T : Any>(
    private var dataList: List<T>,
    @LayoutRes val layoutID: Int,
    private val onItemClick: (T) -> Unit,
) : RecyclerView.Adapter<GenericDataAdapter<T>.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ViewDataBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), layoutID, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            onItemClick(item)
        }

    }

    override fun getItemCount(): Int = dataList.size

    inner class ViewHolder(private val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: T) {
            when (item) {
                is Person -> {
                    binding.setVariable(BR.person, item)
                }
            }
            binding.executePendingBindings()
        }
    }
}