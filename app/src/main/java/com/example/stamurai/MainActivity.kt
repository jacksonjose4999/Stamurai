package com.example.stamurai

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.NumberPicker
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.fragment_home.*

class MainActivity : AppCompatActivity() {
    companion object{
         var arrayListRecords: ArrayList<Record> = ArrayList()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_history
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        storeRecord()
    }

    override fun onResume() {
        super.onResume()
        storeRecord()
    }


    fun setRange(view: View) {
        val pickerMin = findViewById<NumberPicker>(R.id.min_range_picker)
        val pickerMax = findViewById<NumberPicker>(R.id.max_range_picker)

        val min = pickerMin.value
        val max = pickerMax.value

        val rangeButton = range_button

        if  (max<=min){
            Toast.makeText(this, "Max range should be greater than min range",
                Toast.LENGTH_SHORT).show()
        }
        else {
            rangeButton.text = "Range $min-$max"
            Toast.makeText(this,
                "Rating range set successfully, you may now submit your ratings",
                Toast.LENGTH_SHORT).show()
            val sharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return
            with (sharedPref.edit()) {
                putInt("Min", min)
                putInt("Max",max)
                commit()
            }
        }

    }

    fun submitRating(view: View) {
        val sharedPref = this.getPreferences(Context.MODE_PRIVATE)
        val minRange = sharedPref?.getInt("Min", -1)

        if (minRange == -1) {
            Toast.makeText(this, "Please set range first", Toast.LENGTH_SHORT).show()
            return
        }
        val maxRange = sharedPref?.getInt("Max", -1)
        if (maxRange == -1){
            return
        }
        var maxMin = ArrayList<Int>()
        if (minRange != null) {
            maxMin.add(minRange)
            if (maxRange != null) {
                maxMin.add(maxRange)
            }
        }
        startActivity(Intent(this, RatingSubmission::class.java).putExtra("maxMin", maxMin))
    }

    private fun storeRecord() {

        val myDB = openOrCreateDatabase("my.db", Context.MODE_PRIVATE, null)
        myDB.execSQL(
            "CREATE TABLE IF NOT EXISTS RECORDS (ID INTEGER PRIMARY KEY AUTOINCREMENT, rating INT, rating_min INT, rating_max INT, date TIMESTAMP)"
        )

        val myCursor: Cursor = myDB.rawQuery("select ID, rating, rating_min, rating_max, date from RECORDS", null)
        while (myCursor.moveToNext()) {
            val id = myCursor.getString(0)
            val rating = myCursor.getInt(1)
            val ratingMin = myCursor.getInt(2)
            val ratingMax = myCursor.getInt(3)
            val date = myCursor.getString(4)

            arrayListRecords.add(Record(rating, ratingMin, ratingMax, date))
        }
        myCursor.close()

        myDB.close()

    }
}
