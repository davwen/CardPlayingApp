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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [RandomCard.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [RandomCard.newInstance] factory method to
 * create an instance of this fragment.
 */
class RandomCard : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

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

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RandomCard.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RandomCard().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


    fun generateDeck(context: Context) : MutableList<Card>{
        val suits = context.resources.getStringArray(R.array.suits)
        val output = mutableListOf<Card>()

        for(ii in 0..12){
            for(iii in 0..3){
                output.add(Card(suits[iii], ii + 2))
            }
        }



        return output
    }

    fun suitToDrawable(input : String) : Drawable{
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
