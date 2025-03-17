package com.example.fetchrewardscodingexercise

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter(private var itemList: List<Item>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TYPE_HEADER = 0
    private val TYPE_ITEM = 1

    override fun getItemViewType(position: Int): Int {
        return if (position == 0 || itemList[position].listId != itemList[position - 1].listId) {
            TYPE_HEADER // First item in a group
        } else {
            TYPE_ITEM
        }
    }

    class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val headerText: TextView = view.findViewById(R.id.headerText)
    }

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameText: TextView = view.findViewById(R.id.nameText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_HEADER) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.header_row, parent, false)
            HeaderViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_row, parent, false)
            ItemViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = itemList[position]
        if (holder is HeaderViewHolder) {
            holder.headerText.text = "List ID: ${item.listId}"
        } else if (holder is ItemViewHolder) {
            holder.nameText.text = "Name: ${item.name}"
        }
    }

    override fun getItemCount() = itemList.size

    fun updateData(newList: List<Item>) {
        itemList = newList
        notifyDataSetChanged()
    }
}

