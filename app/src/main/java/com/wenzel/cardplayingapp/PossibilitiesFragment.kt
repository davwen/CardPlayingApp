package com.wenzel.cardplayingapp

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thoughtworks.xstream.XStream
import kotlinx.android.synthetic.main.add_card_dialog.view.*
import kotlinx.android.synthetic.main.fragment_possibilities.view.*


class PossibilitiesFragment : Fragment() {
    private val addedCards = mutableListOf<Card>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_possibilities, container, false)

        val addCardAdapter = Add_card_adapter(addedCards, true)

        view.add_card_list.layoutManager = LinearLayoutManager(activity)

        addCardAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver(){
            override fun onChanged() {
                if(addedCards.isNotEmpty()){
                    view.emptyListMsg.visibility = View.GONE
                }else{
                    view.emptyListMsg.visibility = View.VISIBLE
                }
                super.onChanged()
            }

            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                if(addedCards.isNotEmpty()){
                    view.emptyListMsg.visibility = View.GONE
                }else{
                    view.emptyListMsg.visibility = View.VISIBLE
                }

                view.add_btn.isEnabled = addedCards.count() < 5
                view.checkBtn.isEnabled = addedCards.count() >= 2


                super.onItemRangeInserted(positionStart, itemCount)
            }

            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                if(addedCards.isNotEmpty()){
                    view.emptyListMsg.visibility = View.GONE
                }else{
                    view.emptyListMsg.visibility = View.VISIBLE
                }

                view.add_btn.isEnabled = addedCards.count() < 5
                view.checkBtn.isEnabled = addedCards.count() >= 2

                super.onItemRangeRemoved(positionStart, itemCount)
            }
        })

        view.add_card_list.adapter = addCardAdapter

        view.add_btn.setOnClickListener{
            showAddDialog(view.context, activity as FragmentActivity, addCardAdapter)
        }

        view.checkBtn.setOnClickListener{

            if(addedCards.count() >= 2){
                val combos = mutableListOf<Combination>()

                val pairs = Combinations().getPairs(activity!!.applicationContext, addedCards)
                val threeKinds = Combinations().getThreeKind(activity!!.applicationContext, addedCards)
                var shouldAddFullHouse = false

                if(pairs.amount == 1 && threeKinds.amount == 1){
                    shouldAddFullHouse = true
                    combos.add(Combination(mutableListOf(), view.context.resources.getString(R.string.pair), 0, PAIR_KEY))
                    combos.add(Combination(mutableListOf(), view.context.resources.getString(R.string.twoPair), 0, TWO_PAIR_KEY))
                    combos.add(Combination(mutableListOf(), view.context.resources.getString(R.string.threeKind), 0, THREE_KIND_KEY))
                }else{
                    if(pairs.amount == 2){
                        combos.add(Combination(mutableListOf(), view.context.resources.getString(R.string.pair), 0, PAIR_KEY))
                        combos.add(Combination(pairs.cards, view.context.resources.getString(R.string.twoPair), pairs.amount / 2, TWO_PAIR_KEY))
                    }else{
                        combos.add(pairs)
                        combos.add(Combination(mutableListOf(), view.context.resources.getString(R.string.twoPair), 0, TWO_PAIR_KEY))
                    }
                }


                combos.add(Combinations().getStraight(activity!!.applicationContext, addedCards))

                if(!shouldAddFullHouse){
                    combos.add(Combinations().getFlush(activity!!.applicationContext, addedCards))
                }else{
                    combos.add(Combination(mutableListOf(), view.context.resources.getString(R.string.flush), 0, FLUSH_KEY))
                }

                if(shouldAddFullHouse){
                    combos.add(Combination(pairs.cards + threeKinds.cards, view.context.resources.getString(R.string.fullHouse), threeKinds.amount, FULL_HOUSE_KEY))
                }

                combos.add(Combinations().getFourKind(activity!!.applicationContext, addedCards))

                combos.add(Combinations().getStraightFlush(activity!!.applicationContext, addedCards))

                combos.add(Combinations().getRoyalFlush(activity!!.applicationContext, addedCards))

                val intent = Intent(activity, CombinationsRes::class.java)
                intent.putExtra(COMBOS_KEY, XStream().toXML(combos))
                startActivity(intent)
            }else{
                Toast.makeText(activity, R.string.notEnoughCardsMsg, Toast.LENGTH_LONG).show()
            }



        }

        return view
    }

    private fun showAddDialog(context: Context, activity: Activity, adapter: Add_card_adapter){

        val builder = AlertDialog.Builder(context)

        val layout = activity.layoutInflater.inflate(R.layout.add_card_dialog, null, false)

        builder.setView(layout)
            .setPositiveButton(R.string.add_card,
                DialogInterface.OnClickListener { _, _ ->

                    val suit = context.resources.getStringArray(R.array.suits)[layout.spinner.selectedItemPosition].toLowerCase()

                    onAddReturn(Card(suit, layout.spinner3.selectedItemPosition + 2), adapter)
                })
            .setNegativeButton(R.string.cancel,
                DialogInterface.OnClickListener { dialog, _ ->
                    dialog.cancel()
                })

        builder.show()
    }

    private fun onAddReturn(_card : Card, adapter: Add_card_adapter){
        adapter.addCard(_card)
    }
}
