package com.example.myapplicationdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplicationdemo.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random
import kotlinx.android.parcel.Parcelize


class MainActivity : AppCompatActivity(), ExampleAdapter.OnItemClickListener {
    private val exampleList = generateDummyList(500)
    private val adapter = ExampleAdapter(exampleList, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)
    }

    fun insertItem(view: View) {

        val newItem = ExampleItem(
                R.drawable.ic_android,
                "New tache at position ${exampleList.size.inc()}",
                "description "
        )

        exampleList.add(newItem)
        adapter.notifyItemInserted(exampleList.size)
    }



    override fun onItemClick(position: Int) {
        Toast.makeText(this, " ${exampleList[position].text1} terminé ", Toast.LENGTH_SHORT).show()
        val clickedItem = exampleList[position]
        val index = position
        clickedItem.text1 = "tache terminé"
        exampleList.removeAt(index)
        adapter.notifyItemRemoved(index)

    }

    private fun generateDummyList(size: Int): ArrayList<ExampleItem> {

        val list = ArrayList<ExampleItem>()

        for (i in 0 until 10) {

            val item = ExampleItem(R.drawable.ic_android, "tache $i", "description tache")
            list += item
        }

        return list
    }
}