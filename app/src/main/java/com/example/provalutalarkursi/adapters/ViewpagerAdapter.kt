package com.example.provalutalarkursi.adapters

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.example.provalutalarkursi.viewmodels.ViewPagerViewmodel
import com.example.provalutalarkursi.databinding.CardBinding
import com.example.provalutalarkursi.models.Data
import java.util.*

class ViewpagerAdapter(var list: List<Data>,var activity: FragmentActivity) : RecyclerView.Adapter<ViewpagerAdapter.Vh>() {
    lateinit var color_list: IntArray

    inner class Vh(var itemview: CardBinding) : RecyclerView.ViewHolder(itemview.root) {
        fun bind(data: Data) {
            val viewmodel = ViewModelProviders.of(activity)[ViewPagerViewmodel::class.java]
            val randomColor = getRandomColor()
            var gd = GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, randomColor)
            itemview.ln.setBackgroundDrawable(gd)
            viewmodel.set(randomColor[0])

            itemview.cardDate.text = data.datetime
            itemview.cardSell.text =  if (data.nbu_cell_price!!.length > 2) "${data.nbu_cell_price} UZS" else "${data.cb_price} UZS"
            itemview.cardBuy.text =  if (data.nbu_buy_price!!.length > 2) "${data.nbu_buy_price} UZS" else "${data.cb_price} UZS"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(CardBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.bind(list[position])
    }

    fun getRandomColor(): IntArray {
        val rnd = Random()
        color_list = IntArray(2)
        for (i in 0..1) {
            color_list[i] = (Color.argb(255, rnd.nextInt(255), rnd.nextInt(255), rnd.nextInt(255)))
        }

        return color_list
    }

    override fun getItemCount(): Int = list.size
}