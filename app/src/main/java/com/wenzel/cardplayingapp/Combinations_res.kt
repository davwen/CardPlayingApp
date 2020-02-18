package com.wenzel.cardplayingapp

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.thoughtworks.xstream.XStream
import kotlinx.android.synthetic.main.activity_combinations_res.*

class Combinations_res : AppCompatActivity() {

    private val combos = mutableListOf<Combination>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_combinations_res)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        combos.addAll(XStream().fromXML(intent.getStringExtra(COMBOS_KEY)) as List<Combination>)

        Log.d("ComboCount", combos[0].cards.count().toString())

        val combosAdapter = Combinations_adapter(combos)

        comb_list.layoutManager = LinearLayoutManager(this)
        comb_list.adapter = combosAdapter
    }
/*
    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)

        super.onBackPressed()
    }*/
}
