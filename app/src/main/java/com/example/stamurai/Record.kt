package com.example.stamurai

import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.record.view.*
import java.util.Date
import java.sql.Timestamp

class Record {
    private var _id = 0
    private var _rating: Int? = null
    private var _ratingmin: Int? = null
    private var _ratingmax: Int? = null
    private var _time: String? = null


    constructor() {}
    constructor(rating: Int?, ratingMin: Int?, ratingMax: Int?, timestamp: String) {
        _rating = rating
        _ratingmax = ratingMax
        _ratingmin = ratingMin
        _time = timestamp
    }

    fun set_time(timestamp: String){
        this._time = timestamp
    }

    fun get_time(): String?{
        return _time
    }

    fun set_id(_id: Int) {
        this._id = _id
    }

    fun set_rating(rating: Int?) {
        this._rating = rating
    }

    fun get_rating(): Int? {
        return _rating
    }

    fun set_rating_max(rating: Int?) {
        this._ratingmax = rating
    }

    fun get_rating_max(): Int? {
        return _ratingmax
    }
    fun set_rating_min(rating: Int?) {
        this._ratingmin = rating
    }

    fun get_rating_min(): Int? {
        return _ratingmin
    }

    fun get_id(): Int {
        return _id
    }
}

class RecordsRecycler(var records: Record) : Item<ViewHolder>(){
    override fun getLayout(): Int {
        return R.layout.record
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.rating_recycler.text = records.get_rating().toString()
        viewHolder.itemView.range_recycler.text =
            "${records.get_rating_min().toString()}-${records.get_rating_max().toString()}"

        viewHolder.itemView.date_recycler.text = records.get_time()
    }

}

class RecordsLabel : Item<ViewHolder>(){
    override fun getLayout(): Int {
        return R.layout.record
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.rating_recycler.text = "Rating"
        viewHolder.itemView.range_recycler.text = "Rating Range"
        viewHolder.itemView.date_recycler.text = "Date & Time"
    }

}
