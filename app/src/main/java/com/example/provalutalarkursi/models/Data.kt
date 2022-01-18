package com.example.provalutalarkursi.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Data {
    @PrimaryKey(autoGenerate = true)
    var id:Int? = null
    var code: String? = null
    var cb_price: String? = null
    var date: String? = null
    var nbu_buy_price: String? = null
    var nbu_cell_price: String? = null
    var title: String? = null
    var datetime: String? = null
    var dateday: String? = null

    constructor(
        cb_price: String?,
        code: String?,
        date: String?,
        nbu_buy_price: String?,
        nbu_cell_price: String?,
        title: String?
    ) {
        this.cb_price = cb_price
        this.code = code
        this.date = date
        this.nbu_buy_price = nbu_buy_price
        this.nbu_cell_price = nbu_cell_price
        this.title = title
    }

    constructor(
        cb_price: String?,
        code: String?,
        nbu_buy_price: String?,
        nbu_cell_price: String?,
        title: String?,
        datetime: String?,
        dateday: String?
    ) {
        this.cb_price = cb_price
        this.code = code
        this.nbu_buy_price = nbu_buy_price
        this.nbu_cell_price = nbu_cell_price
        this.title = title
        this.datetime = datetime
        this.dateday = dateday
    }


    constructor()
    constructor(
        code: String?,
        cb_price: String?,
        date: String?,
        nbu_buy_price: String?,
        nbu_cell_price: String?,
        title: String?,
        datetime: String?,
        dateday: String?
    ) {
        this.code = code
        this.cb_price = cb_price
        this.date = date
        this.nbu_buy_price = nbu_buy_price
        this.nbu_cell_price = nbu_cell_price
        this.title = title
        this.datetime = datetime
        this.dateday = dateday
    }

    override fun toString(): String {
        return super.toString()
    }
}