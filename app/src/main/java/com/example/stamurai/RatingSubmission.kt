package com.example.stamurai

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_rating_submission.*
import java.sql.Timestamp


class RatingSubmission : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rating_submission)
        val noteTextView = noteTextView
        val extras = intent.extras
        val minMax = extras!!.getIntegerArrayList("maxMin")
        val textViewSlider = value_seekbar

        if (minMax == null){
            finish()
        }
        if (minMax != null) {
            noteTextView.text = "Note: The range is from ${minMax.get(0)!!} to ${minMax.get(1)!!}"
            val slider = seekBar
            slider.max = minMax.get(1) - minMax.get(0)
            textViewSlider.text = "${minMax.get(0)}"
            slider?.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    textViewSlider.text = "${progress+ minMax.get(0)}"
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                }
            })
        }
    }

    fun onSubmitRatingClick(view: View) {
        val extras = intent.extras
        val minMax = extras!!.getIntegerArrayList("maxMin")

        val record = Record(value_seekbar.text.toString().toInt(), minMax?.get(0), minMax?.get(1), Timestamp(System.currentTimeMillis()).toString())
        Toast.makeText(this, "Rating Submitted", Toast.LENGTH_SHORT).show()
        writeRecord(record)
    }

    private fun writeRecord(record: Record) {

        val myDB = openOrCreateDatabase("my.db", Context.MODE_PRIVATE, null)
        myDB.execSQL(
            "CREATE TABLE IF NOT EXISTS RECORDS (ID INTEGER PRIMARY KEY AUTOINCREMENT, rating INT, rating_min INT, rating_max INT, date TIMESTAMP)"
        )

        val row1 = ContentValues()
        row1.put("rating", record.get_rating())
        row1.put("rating_min", record.get_rating_min())
        row1.put("rating_max", record.get_rating_max())
        row1.put("date", record.get_time().toString())

        myDB.insert("RECORDS", null, row1)

        val myCursor: Cursor = myDB.rawQuery("select ID, rating, rating_min, rating_max, date from RECORDS", null)
        while (myCursor.moveToNext()) {
            val id = myCursor.getString(0)
            val rating = myCursor.getInt(1)
            val ratingMin = myCursor.getInt(2)
            val ratingMax = myCursor.getInt(3)
            val date = myCursor.getString(4)


            Log.wtf("LOL", "$id $rating ${date}")
        }
        myCursor.close()

        myDB.close()

    }


}
