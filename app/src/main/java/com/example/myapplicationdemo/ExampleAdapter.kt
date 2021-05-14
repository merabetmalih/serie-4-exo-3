package com.example.myapplicationdemo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kotlinx.android.synthetic.main.example_item.view.*
import java.util.*


class ExampleAdapter(
        private val exampleList: List<ExampleItem>,
        private val listener: OnItemClickListener
) :
    RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
                R.layout.example_item,
                parent, false)

        return ExampleViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        val currentItem = exampleList[position]
        holder.update(currentItem, position,listener)
    }

    override fun getItemCount() = exampleList.size

    inner class ExampleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val imageView: ImageView = itemView.image_view
        val textView1: TextView = itemView.text_view_1
        val textView2: TextView = itemView.text_view_2

        fun update(item: ExampleItem, position: Int, listener: OnItemClickListener){
            imageView.setImageResource(item.imageResource)
            textView1.text = item.text1
            textView2.text = "Date : ${item.date.get(Calendar.YEAR)}-${item.date.get(Calendar.MONTH)}-${item.date.get(Calendar.DAY_OF_MONTH)}"
            itemView.setOnClickListener{
                if (position != RecyclerView.NO_POSITION) {
                    textView1.text = "tache terminé"
                    listener.onItemClick(position)
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}