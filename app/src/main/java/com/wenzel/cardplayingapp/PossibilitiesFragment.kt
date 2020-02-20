package com.wenzel.cardplayingapp

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.ContextWrapper
import android.content.DialogInterface
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
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
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.add_card_dialog.view.*
import kotlinx.android.synthetic.main.fragment_possibilities.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [PossibilitiesFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [PossibilitiesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PossibilitiesFragment : Fragment() {
    private val addedCards = mutableListOf<Card>()


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

                val intent = Intent(activity, Combinations_res::class.java)
                intent.putExtra(COMBOS_KEY, XStream().toXML(combos))
                startActivity(intent)
            }else{
                Toast.makeText(activity, R.string.notEnoughCardsMsg, Toast.LENGTH_LONG).show()
            }



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
         * @return A new instance of fragment PossibilitiesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PossibilitiesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun showAddDialog(context: Context, activity: Activity, adapter: Add_card_adapter){

        val builder = AlertDialog.Builder(context)

        val layout = activity.layoutInflater.inflate(R.layout.add_card_dialog, null, false)

        builder.setView(layout)
            // Add action buttons
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

    tailrec fun Context.activity(): Activity? = when {
        this is Activity -> this
        else -> (this as? ContextWrapper)?.baseContext?.activity()
    }
}
