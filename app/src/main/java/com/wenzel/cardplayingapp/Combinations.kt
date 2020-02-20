package com.wenzel.cardplayingapp

import android.content.Context
import java.util.*

const val PAIR_KEY = "pair"
const val TWO_PAIR_KEY = "two_pair"
const val THREE_KIND_KEY = "three_of_a_kind"
const val FOUR_KIND_KEY = "four_of_a_kind"
const val STRAIGHT_KEY = "straight"
const val FLUSH_KEY = "flush"
const val FULL_HOUSE_KEY = "full_house"
const val STRAIGHT_FLUSH_KEY = "straight_flush"
const val ROYAL_FLUSH_KEY = "royal_flush"

class Combinations {

    fun getPairs(context: Context, cards : List<Card>) : Combination{
        val output = mutableListOf<Card>()



        var timesOccurred = 0

        val inputNumbers = mutableListOf<Int>()
        cards.forEach{
            inputNumbers.add(it.number)
        }

        for(item in inputNumbers.distinct()){
            if(Collections.frequency(inputNumbers, item) == 2){

                output.addAll(cards.filter { it.number == item })

                timesOccurred++
            }
        }

        return Combination(output, context.resources.getString(R.string.pair), timesOccurred, PAIR_KEY)
    }

    fun getThreeKind(context: Context, cards : List<Card>) : Combination{
        val output = mutableListOf<Card>()



        var timesOccurred = 0

        val inputNumbers = mutableListOf<Int>()
        cards.forEach{
            inputNumbers.add(it.number)
        }

        for(item in inputNumbers.distinct()){
            if(Collections.frequency(inputNumbers, item) == 3){

                output.addAll(cards.filter { it.number == item })

                timesOccurred++
            }
        }

        return Combination(output, context.resources.getString(R.string.threeKind), timesOccurred, THREE_KIND_KEY)
    }

    fun getFourKind(context: Context, cards : List<Card>) : Combination{
        val output = mutableListOf<Card>()



        var timesOccurred = 0

        val inputNumbers = mutableListOf<Int>()
        cards.forEach{
            inputNumbers.add(it.number)
        }

        for(item in inputNumbers.distinct()){
            if(Collections.frequency(inputNumbers, item) == 4){

                output.addAll(cards.filter { it.number == item })

                timesOccurred++
            }
        }

        return Combination(output, context.resources.getString(R.string.fourKind), timesOccurred, FOUR_KIND_KEY)
    }

    fun getStraight(context: Context, cards : List<Card>) : Combination{
        val output = mutableListOf<Card>()

        var timesOccurred = 0

        val input = cards.sortedBy { it.number }

        val sequence = mutableListOf<Card>()

        for ((i, item) in input.distinctBy { it.number } .withIndex()){
            if(i >= 1){
                if((item.number - 1) == input[i - 1].number){
                    sequence.add(item)
                }
            }else{
                if((item.number + 1) == input[i + 1].number){
                    sequence.add(item)
                }
            }

        }

        val suits = mutableListOf<String>()
        sequence.forEach{
            suits.add(it.suit)
        }

        if(sequence.isNotEmpty()){
            if(Collections.frequency(suits, suits[0]) == suits.count() || sequence.count() != 5){
                sequence.clear()
            }
        }

        if(output.isNotEmpty()){
            timesOccurred = 1
        }

        output.clear()
        output.addAll(sequence)

        return Combination(output, context.resources.getString(R.string.straight), timesOccurred, STRAIGHT_KEY)
    }

    fun getFlush(context: Context, cards : List<Card>) : Combination{
        val output = mutableListOf<Card>()

        var timesOccurred = 0

        val input = cards.sortedBy { it.number }

        val sequence = mutableListOf<Card>()

        for ((i, item) in input.distinctBy { it.number } .withIndex()){
            if(i >= 1){
                if((item.number - 1) == input[i - 1].number){
                    sequence.add(item)
                }
            }else{
                if((item.number + 1) == input[i + 1].number){
                    sequence.add(item)
                }
            }

        }

        val suits = mutableListOf<String>()
        input.forEach{
            suits.add(it.suit)
        }

        for(item in suits.distinct()){
            if(Collections.frequency(suits, item) == 5){

                output.addAll(cards.filter { it.suit == item })

                timesOccurred++
            }
        }


        if(sequence.count() == 5){

            output.clear()

            timesOccurred = 0

        }


        return Combination(output, context.resources.getString(R.string.flush), timesOccurred, FLUSH_KEY)
    }

    fun getStraightFlush(context: Context, cards : List<Card>) : Combination{
        val output = mutableListOf<Card>()

        var timesOccurred = 0

        val input = cards.sortedBy { it.number }

        val sequence = mutableListOf<Card>()

        for ((i, item) in input.distinctBy { it.number } .withIndex()){
            if(i >= 1){
                if((item.number - 1) == input[i - 1].number){
                    sequence.add(item)
                }
            }else{
                if((item.number + 1) == input[i + 1].number){
                    sequence.add(item)
                }
            }

        }

        val suits = mutableListOf<String>()
        input.forEach{
            suits.add(it.suit)
        }

        for(item in suits.distinct()){
            if(Collections.frequency(suits, item) == 5){

                output.addAll(cards.filter { it.suit == item })

                timesOccurred++
            }
        }

        val inputNumbers = mutableListOf<Int>()
        cards.forEach{
            inputNumbers.add(it.number)
        }

        if(sequence.count() != 5 || inputNumbers == mutableListOf(10, 11, 12, 13, 14)){

            output.clear()

            timesOccurred = 0

        }


        return Combination(output, context.resources.getString(R.string.straightFlush), timesOccurred, STRAIGHT_FLUSH_KEY)
    }

    fun getRoyalFlush(context: Context, cards : List<Card>) : Combination{
        val output = mutableListOf<Card>()

        var timesOccurred = 0

        val input = cards.sortedBy { it.number }

        val sequence = mutableListOf<Card>()

        for ((i, item) in input.distinctBy { it.number } .withIndex()){
            if(i >= 1){
                if((item.number - 1) == input[i - 1].number){
                    sequence.add(item)
                }
            }else{
                if((item.number + 1) == input[i + 1].number){
                    sequence.add(item)
                }
            }

        }

        val suits = mutableListOf<String>()
        input.forEach{
            suits.add(it.suit)
        }

        for(item in suits.distinct()){
            if(Collections.frequency(suits, item) == 5){

                output.addAll(cards.filter { it.suit == item })

                timesOccurred++
            }
        }

        val inputNumbers = mutableListOf<Int>()
        cards.forEach{
            inputNumbers.add(it.number)
        }

        if(sequence.count() != 5 || inputNumbers != mutableListOf(10, 11, 12, 13, 14)){

            output.clear()

            timesOccurred = 0

        }


        return Combination(output, context.resources.getString(R.string.royalFlush), timesOccurred, ROYAL_FLUSH_KEY)
    }
}