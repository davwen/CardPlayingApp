package com.wenzel.cardplayingapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.add_card_item.view.*

class Add_card_adapter(private val cards: MutableList<Card>, private val enableUIRemove: Boolean) : RecyclerView.Adapter<Add_card_adapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return cards.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // create view holder to hold reference
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.add_card_item, parent, false))
    }

    override fun onBindViewHolder(holder: Add_card_adapter.ViewHolder, pos: Int) {
        holder.itemView.textView.text = convertNumberToName(holder.itemView.context, cards[pos].number)

        if(cards[pos].suit == DIAMOND){
            holder.itemView.imageView.setImageDrawable(holder.itemView.context.resources.getDrawable(R.drawable.diamond))
        }

        if(cards[pos].suit == HEART){
            holder.itemView.imageView.setImageDrawable(holder.itemView.context.resources.getDrawable(R.drawable.heart))
        }

        if(cards[pos].suit == SPADE){
            holder.itemView.imageView.setImageDrawable(holder.itemView.context.resources.getDrawable(R.drawable.spade))
        }

        if(cards[pos].suit == CLUB){
            holder.itemView.imageView.setImageDrawable(holder.itemView.context.resources.getDrawable(R.drawable.club))
        }

        if(!enableUIRemove){
            holder.itemView.removeBtn.visibility = View.GONE
        }
    }

    fun addCard(card_to_add : Card){
        cards.add(card_to_add)
        notifyItemInserted(cards.count() - 1)
    }

    fun removeCard(index_to_remove : Int){
        cards.removeAt(index_to_remove)
        notifyItemRemoved(index_to_remove)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        init {
            view.removeBtn.setOnClickListener{
                removeCard(adapterPosition)
            }
        }
    }
}

fun convertNumberToName(context: Context, input : Int) : String{

    var output = ""

    if(input > 10){
        if(input == 11){
            output = context.resources.getString(R.string.jackShort)
        }
        if(input == 12){
            output = context.resources.getString(R.string.queenShort)
        }
        if(input == 13){
            output = context.resources.getString(R.string.kingShort)
        }
        if(input == 14){
            output = context.resources.getString(R.string.aceShort)
        }
    }else{
        output = input.toString()
    }

    return output
}