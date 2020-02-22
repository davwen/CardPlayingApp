package com.wenzel.cardplayingapp

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
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

        val possibleNumbers = activity!!.resources.getStringArray(R.array.numbersAdapterShort)

        var randomCard = deck[Random.nextInt(0, deck.count())]
        view.rCardTxt.text = possibleNumbers[randomCard.number - 2]
        view.rCardImg.setImageDrawable(suitToDrawable(randomCard.suit))

        view!!.rCardBtn.setOnClickListener{
            if(deck.isNotEmpty()){
                randomCard = deck[Random.nextInt(0, deck.count())]
                view.rCardTxt.text = possibleNumbers[randomCard.number - 2]
                val suit = randomCard.suit

                view.rCardImg.setImageDrawable(suitToDrawable(suit))

                deck.removeAt(deck.indexOf(randomCard))
                cardsLeftTxt.text = deck.count().toString() + " " + view.context.resources.getString(R.string.cardsLeft).decapitalize()
            }else{
                view.rCardBtn.isEnabled = false
                view.rCardBtn.backgroundTintList = ContextCompat.getColorStateList(view.context, R.color.disabledBtn)
            }

        }

        view.newDeckBtn.setOnClickListener{
            deck = generateDeck(view.context)
            cardsLeftTxt.text = deck.count().toString() + " " + view.context.resources.getString(R.string.cardsLeft).decapitalize()

            randomCard = deck[Random.nextInt(0, deck.count())]
            view.rCardTxt.text = possibleNumbers[randomCard.number - 2]
            val suit = randomCard.suit

            view.rCardBtn.isEnabled = true
            view.rCardBtn.backgroundTintList = ContextCompat.getColorStateList(view.context, R.color.colorAccent)

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
            output = activity!!.getMyDrawable(R.drawable.heart)
        }

        if(input == DIAMOND){
            output = activity!!.getMyDrawable(R.drawable.diamond)
        }

        if(input == SPADE){
            output = activity!!.getMyDrawable(R.drawable.spade)
        }

        if(input == CLUB){
            output = activity!!.getMyDrawable(R.drawable.club)
        }

        return output!!
    }

    private fun Context.getMyDrawable(id : Int) : Drawable?{

        return  ContextCompat.getDrawable(this, id)
    }
}
