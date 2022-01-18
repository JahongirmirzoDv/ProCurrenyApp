package com.example.provalutalarkursi.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.provalutalarkursi.databinding.CurrencyItemBinding
import com.example.provalutalarkursi.models.Data
import com.squareup.picasso.Picasso

class CurrencyAdapter(var list: List<Data>, var onpress: onPress) :
    RecyclerView.Adapter<CurrencyAdapter.Vh>() {
    inner class Vh(var itemview: CurrencyItemBinding) : RecyclerView.ViewHolder(itemview.root) {
        fun bind(data: Data, position: Int) {
            Picasso.get()
                .load("https://flagcdn.com/80x60/${data.code?.substring(0, 2)?.toLowerCase()}.png")
                .into(itemview.flag)
            itemview.code.text = data.code
            itemview.sellPrice.text =
                if (data.nbu_cell_price!!.length > 2) "${data.nbu_cell_price} UZS" else "${data.cb_price} UZS"
            itemview.buyPrice.text =
                if (data.nbu_buy_price!!.length > 2) "${data.nbu_buy_price} UZS" else "${data.cb_price} UZS"
            itemview.calc.setOnClickListener {
                onpress.onclick(data, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyAdapter.Vh {
        return Vh(CurrencyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: CurrencyAdapter.Vh, position: Int) {
        holder.bind(list[position], position)
    }

    override fun getItemCount(): Int = list.size
    interface onPress {
        fun onclick(data: Data, position: Int)
    }
}