package com.wenzel.cardplayingapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.cominations_res_item.view.*
import kotlinx.android.synthetic.main.fragment_ranks.view.*
import kotlinx.android.synthetic.main.poker_hands_theory_item.view.*


class RanksAdapter(private val ranksList: List<Rank>) : RecyclerView.Adapter<RanksAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return ranksList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // create view holder to hold reference
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.poker_hands_theory_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        holder.itemView.titleTxt.text = ranksList[pos].name
        holder.itemView.descTxt.text = ranksList[pos].description
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }
}

class Rank(_name : String, _description : String){
    val name = _name
    val description = _description
}