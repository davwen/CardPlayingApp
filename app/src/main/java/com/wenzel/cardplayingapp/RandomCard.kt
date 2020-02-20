package com.wenzel.cardplayingapp

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_random_card.*
import kotlinx.android.synthetic.main.fragment_random_card.view.*
import kotlin.random.Random


class RandomCard : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_random_card, container, false)

        var deck = generateDeck(view.context)
        view.cardsLeftTxt.text = deck.count().toString() + " " + view.context.resources.getString(R.string.cardsLeft).decapitalize()

        val possibleNumbers = activity!!.resources.getStringArray(R.array.numbersAdapter)

        val randomCard = deck[Random.nextInt(0, deck.count())]
        view.rCardTxt.text = possibleNumbers[randomCard.number - 2]
        view.rCardImg.setImageDrawable(suitToDrawable(randomCard.suit))

        view!!.rCardBtn.setOnClickListener{
            if(deck.isNotEmpty()){
                val randomCard = deck[Random.nextInt(0, deck.count())]
                view.rCardTxt.text = possibleNumbers[randomCard.number - 2]
                val suit = randomCard.suit

                view.rCardImg.setImageDrawable(suitToDrawable(suit))

                deck.removeAt(deck.indexOf(randomCard))
                cardsLeftTxt.text = deck.count().toString() + " " + view.context.resources.getString(R.string.cardsLeft).decapitalize()
            }else{
                view.rCardBtn.isEnabled = false
                view.rCardBtn.backgroundTintList = view.context.resources.getColorStateList(R.color.disabledBtn)
            }

        }

        view.newDeckBtn.setOnClickListener{
            deck = generateDeck(view.context)
            cardsLeftTxt.text = deck.count().toString() + " " + view.context.resources.getString(R.string.cardsLeft).decapitalize()

            val randomCard = deck[Random.nextInt(0, deck.count())]
            view.rCardTxt.text = possibleNumbers[randomCard.number - 2]
            val suit = randomCard.suit

            view.rCardBtn.isEnabled = true
            view.rCardBtn.backgroundTintList = view.context.resources.getColorStateList(R.color.colorAccent)

            view.rCardImg.setImageDrawable(suitToDrawable(suit))
        }

        return view
    }



    private fun generateDeck(context: Context) : MutableList<Card>{
        val suits = context.resources.getStringArray(R.array.suits)
        val output = mutableListOf<Card>()

        for(ii in 0..12){
            for(iii in 0..3){
                output.add(Card(suits[iii], ii + 2))
            }
        }



        return output
    }

    private fun suitToDrawable(input : String) : Drawable{
        var output : Drawable? = null

        if(input == HEART){
            output = activity!!.resources.getDrawable(R.drawable.heart)
        }

        if(input == DIAMOND){
            output = activity!!.resources.getDrawable(R.drawable.diamond)
        }

        if(input == SPADE){
            output = activity!!.resources.getDrawable(R.drawable.spade)
        }

        if(input == CLUB){
            output = activity!!.resources.getDrawable(R.drawable.club)
        }

        return output!!
    }
}
