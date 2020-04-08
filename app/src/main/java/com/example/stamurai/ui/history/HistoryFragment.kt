package com.example.stamurai.ui.history


import android.database.Cursor
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xwray.groupie.GroupAdapter

import com.example.stamurai.*

import com.xwray.groupie.ViewHolder

class HistoryFragment : Fragment() {

    private lateinit var historyViewModel: HistoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        historyViewModel =
            ViewModelProviders.of(this).get(HistoryViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_history, container, false)
        historyViewModel.text.observe(this, Observer {
            var adapter = GroupAdapter<ViewHolder>()

            var recyclerView = root.findViewById<RecyclerView>(R.id.recycler_view_records)
            recyclerView.layoutManager = LinearLayoutManager(this.context)
            recyclerView!!.adapter = adapter
            adapter.clear()

            writeRecyclerData(adapter)

        })
        return root
    }

    private fun writeRecyclerData(adapter: GroupAdapter<ViewHolder>) {
        var arrayListRecords = MainActivity.arrayListRecords
        for ( i in 0..arrayListRecords.size-1){
            adapter.add(RecordsRecycler(arrayListRecords.get(i)))
            Log.wtf("djjd","${arrayListRecords.get(i).get_rating()}")
        }
    }
}