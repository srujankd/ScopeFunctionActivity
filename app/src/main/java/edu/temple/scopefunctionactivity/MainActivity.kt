package edu.temple.scopefunctionactivity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // You can test your helper functions by  calling them from onCreate() and
        // printing their output to the Log, which is visible in the LogCat:
        // eg. Log.d("function output", getTestDataArray().toString())

        // getTestDataArray Test
        val nums = getTestDataArray()
        Log.d("Testing", "getTestDataArray(): $nums")

        // averageLessThanMedian Test
        val evenTotNumsCase = listOf(1.0, 2.0, 3.0, 4.0)          // should be false, avg equals median case
        val evenTotNumsAndGreaterThanCase = listOf(1.0, 2.0, 3.0, 200.0)        // should be false, avg is greater than median case
        val oddTotNumsCase = listOf(1.0, 2.0, 2.0, 2.0, 200.0) // shoulf be false, odd total elems so median should avg middle 2 elems
        val trueCase = listOf(1.0, 2.0, 3.0, 4.0, 4.0)  // should be true 2.8 < 3.0
        Log.d("Testing", "avg < median (evenTotNumsCase): ${averageLessThanMedian(evenTotNumsCase)}")
        Log.d("Testing", "avg < median (evenTotNumsAndGreaterThanCase): ${averageLessThanMedian(evenTotNumsAndGreaterThanCase)}")
        Log.d("Testing", "avg<median (oddTotNumsCase): ${averageLessThanMedian(oddTotNumsCase)}")
        Log.d("Testing", "avg<median (trueCase): ${averageLessThanMedian(trueCase)}")


        // getView Test
        val sample = listOf(10, 20, 30)
        // case of view not being recycled
        val view1 = getView(0, null, sample, this) as TextView
        Log.d("Testing", "getView new -> text='${view1.text}'")
        // case of view being recycled (using v1)
        val view2 = getView(2, view1, sample, this) as TextView // recycle v1
        Log.d("Testing", "getView recycled -> text='${view2.text}' (verify same instance? ${view1 === view2})")

    }


    /* Convert all the helper functions below to Single-Expression Functions using Scope Functions */
    // eg. private fun getTestDataArray() = ...

    // HINT when constructing elaborate scope functions:
    // Look at the final/return value and build the function "working backwards"

    // Return a list of random, sorted integers
    private fun getTestDataArray(): List <Int> = MutableList(10) { Random.nextInt() }.apply { sort() }
    /*
    private fun getTestDataArray() : List<Int> {
        val testArray = MutableList(10){ Random.nextInt()}
        testArray.sort()
        return testArray
    }
    */

    // Return true if average value in list is greater than median value, false otherwise
    private fun averageLessThanMedian(listOfNumbers: List<Double>) = listOfNumbers.sorted().run {
        // addresses even number of elements case - average out the middle 2 elems
        val med = if(size % 2 == 0) (this[size/2] + this[(size-1)/2]) / 2
        else
            this[size/2]    // case of odd number of elems total
        listOfNumbers.average() < med   // compare if avg < med
    }
    /*
    private fun averageLessThanMedian(listOfNumbers: List<Double>): Boolean {
        val avg = listOfNumbers.average()
        val sortedList = listOfNumbers.sorted()
        val median = if (sortedList.size % 2 == 0)
            (sortedList[sortedList.size / 2] + sortedList[(sortedList.size - 1) / 2]) / 2
        else
            sortedList[sortedList.size / 2]

        return avg < median
    }
    */


    // Create a view from an item in a collection, but recycle if possible (similar to an AdapterView's adapter)
    private fun getView(position: Int, recycledView: View?, collection: List<Int>, context: Context):
            View = (recycledView as? TextView ?: TextView(context).apply {
                setPadding(5, 10, 10, 0)
                textSize = 22f
            }).also { it.text = collection[position].toString() }
    /*
    private fun getView(position: Int, recycledView: View?, collection: List<Int>, context: Context): View {
        val textView: TextView

        if (recycledView != null) {
            textView = recycledView as TextView
        } else {
            textView = TextView(context)
            textView.setPadding(5, 10, 10, 0)
            textView.textSize = 22f
        }

        textView.text = collection[position].toString()

        return textView
    }
    */
}