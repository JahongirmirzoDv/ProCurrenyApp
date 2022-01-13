package com.example.provalutalarkursi.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.provalutalarkursi.R
import com.example.provalutalarkursi.models.Data

class SpinnerAdapter(var list: List<Data>) : BaseAdapter() {
    override fun getCount(): Int = list.size

    override fun getItem(position: Int): Any {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var item: View =
            convertView ?: LayoutInflater.from(parent!!.context)
                .inflate(R.layout.spinner_item, parent, false)
        item.findViewById<TextView>(R.id.name_spinner).text = list[position].code
        return item
    }
}