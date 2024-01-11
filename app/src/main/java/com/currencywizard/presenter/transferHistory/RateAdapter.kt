package com.currencywizard.presenter.transferHistory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.currencywizard.data.modules.Rate
import com.currencywizard.databinding.TransferHistoryItemBinding

class RateAdapter(): RecyclerView.Adapter<RateAdapter.RateViewHolder>(){
    private val list = mutableListOf<Rate>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RateViewHolder {
        val context = parent.context
        val layoutInflater = LayoutInflater.from(context)
        val binding = TransferHistoryItemBinding.inflate(layoutInflater, parent, false)
        return RateViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: RateViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun submitList(list: List<Rate>) = with(this.list){
        clear()
        addAll(list)
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int = list.size
    class RateViewHolder(
        val binding: TransferHistoryItemBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(rate: Rate) = with(binding) {

            fromCurrency.text = rate.base
            toCount.text = rate.coefficient.toString()
            toCurrency.text = rate.target
            date.text = rate.date
        }

    }



}