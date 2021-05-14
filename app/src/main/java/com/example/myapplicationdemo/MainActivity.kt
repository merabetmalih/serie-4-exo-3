package com.example.myapplicationdemo

import android.app.DatePickerDialog
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplicationdemo.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random
import kotlinx.android.parcel.Parcelize
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity(), ExampleAdapter.OnItemClickListener, AdapterView.OnItemSelectedListener {
    private var exampleList:MutableList<ExampleItem> = ArrayList<ExampleItem>()
    lateinit var  adapter: ExampleAdapter
    private var filter:Calendar? = Calendar.getInstance()


    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (position==0){
            filter= Calendar.getInstance()
        }else if(position==1){
            filter = Calendar.getInstance()
            filter?.set(filter!!.get(Calendar.YEAR), filter!!.get(Calendar.MONTH), filter!!.get(Calendar.DAY_OF_MONTH)+7)
        }else{
            filter = null
        }
        adapter = if(filter!=null) ExampleAdapter(exampleList.filterDate(filter!!), this)
        else ExampleAdapter(exampleList, this)
        recycler_view.adapter = adapter

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // if it is portrait mode configure spinner
        if (resources.configuration.orientation== Configuration.ORIENTATION_PORTRAIT){
            spinner_langs.onItemSelectedListener = this

        }
        adapter = if(filter!=null) ExampleAdapter(exampleList.filterDate(filter!!), this)
        else ExampleAdapter(exampleList, this)
        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL,false)
        recycler_view.setHasFixedSize(true)
    }

    fun insertItem(view: View) {

        val newFragment =TaskDateTimePicker(object:PickerListener{
            override fun onDateSet(year: Int, month: Int, day: Int) {
                val index = exampleList.size
                val cal =Calendar.getInstance()
                cal.set(year,month,day)
                val newItem = ExampleItem(
                    R.drawable.ic_android,
                    "New tache at position ${index+1}",
                    cal
                )
                exampleList = (exampleList + newItem).toMutableList()
                adapter = if(filter!=null) ExampleAdapter(exampleList.filterDate(filter!!), this@MainActivity)
                else ExampleAdapter(exampleList, this@MainActivity)
                recycler_view.adapter = adapter
            }
        })
        newFragment.show(supportFragmentManager, "datePicker")

    }

    override fun onItemClick(position: Int) {
        Toast.makeText(this, " ${exampleList[position].text1} terminé ", Toast.LENGTH_SHORT).show()
        exampleList.removeAt(position)
        adapter = if(filter!=null) ExampleAdapter(exampleList.filterDate(filter!!), this)
        else ExampleAdapter(exampleList, this)
        recycler_view.adapter = adapter
    }




}
fun MutableList<ExampleItem>.filterDate(filterParam:Calendar) :MutableList<ExampleItem>{
    val result = filter {
        Log.d("TAG", Calendar.getInstance().get(Calendar.DAY_OF_MONTH).toString())
        Log.d("TAG", it.date.get(Calendar.DAY_OF_MONTH).toString())
        Log.d("TAG", filterParam.get(Calendar.DAY_OF_MONTH).toString())

        (Calendar.getInstance().get(Calendar.DAY_OF_MONTH)<=it.date.get(Calendar.DAY_OF_MONTH) )and (it.date.get(Calendar.DAY_OF_MONTH)  <= filterParam.get(Calendar.DAY_OF_MONTH))
    }.toMutableList()
    Log.d("TAG", result.toString())
    return result
}