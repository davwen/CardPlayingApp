package com.wenzel.cardplayingapp

class Card (_suit : String, _number : Int) {
    val suit = _suit
    val number = _number
}

class Combination(_cards : List<Card>, _name : String, _amount: Int, _key : String){
    val cards = _cards
    val name = _name
    val amount = _amount
    val key = _key
}