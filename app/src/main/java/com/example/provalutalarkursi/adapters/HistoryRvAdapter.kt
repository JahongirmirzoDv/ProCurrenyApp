package com.example.provalutalarkursi.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.provalutalarkursi.databinding.HistoryItemBinding
import com.example.provalutalarkursi.models.Data

class HistoryRvAdapter(var list: List<Data>) : RecyclerView.Adapter<HistoryRvAdapter.Vh>() {
    inner class Vh(var itemview: HistoryItemBinding) : RecyclerView.ViewHolder(itemview.root) {
        fun bind(data: Data) {
            itemview.name2.text = data.datetime
            itemview.date2.text = data.dateday
            itemview.sell2.text =
                if (data.nbu_cell_price!!.length > 2) "${data.nbu_cell_price} UZS" else "${data.cb_price} UZS"
            itemview.buy2.text =
                if (data.nbu_buy_price!!.length > 2) "${data.nbu_buy_price} UZS" else "${data.cb_price} UZS"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(HistoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
}