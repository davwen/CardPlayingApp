package com.wenzel.cardplayingapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_ranks_theory.view.*

class Ranks : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_ranks_theory, container, false)

        val ranks = mutableListOf<Rank>()

        ranks.add(stringToRank(view.context.resources.getString(R.string.highCardsDesc)))
        ranks.add(stringToRank(view.context.resources.getString(R.string.pairDesc)))
        ranks.add(stringToRank(view.context.resources.getString(R.string.twoPairDesc)))
        ranks.add(stringToRank(view.context.resources.getString(R.string.threeKindDesc)))
        ranks.add(stringToRank(view.context.resources.getString(R.string.straightDesc)))
        ranks.add(stringToRank(view.context.resources.getString(R.string.flushDesc)))
        ranks.add(stringToRank(view.context.resources.getString(R.string.fullHouseDesc)))
        ranks.add(stringToRank(view.context.resources.getString(R.string.fourKindDesc)))
        ranks.add(stringToRank(view.context.resources.getString(R.string.straightFlushDesc)))
        ranks.add(stringToRank(view.context.resources.getString(R.string.royalFlushDesc)))

        val ranksAdapter = RanksAdapter(ranks)

        view.ranksList.layoutManager = LinearLayoutManager(view.context)
        view.ranksList.adapter = ranksAdapter

        return view
    }

}

fun stringToRank(input : String) : Rank{
    return Rank(input.split(":")[0].trim(), input.split(":")[1].trim())
}
