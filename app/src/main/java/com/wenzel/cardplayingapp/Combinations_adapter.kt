package com.wenzel.cardplayingapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.cominations_res_item.view.*

class Combinations_adapter(private val combos: List<Combination>) : RecyclerView.Adapter<Combinations_adapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return combos.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // create view holder to hold reference
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.cominations_res_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        holder.itemView.textView2.text = combos[pos].name.capitalize().replace("_", " ") + " (" + combos[pos].amount + ")"

        holder.itemView.card_list.layoutManager = LinearLayoutManager(holder.itemView.context)
        holder.itemView.card_list.adapter = Add_card_adapter(combos[pos].cards.toMutableList(), false)


    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }
}
