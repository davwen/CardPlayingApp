package com.wenzel.cardplayingapp

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import kotlinx.android.synthetic.main.activity_main.*


//import kotlinx.android.synthetic.main.activity_main.toolbar

const val HEART = "heart"
const val SPADE = "spade"
const val DIAMOND = "diamond"
const val CLUB = "club"

const val COMBOS_KEY = "combos_key"

class MainActivity : AppCompatActivity() {

    private val defaultFragment = Opening()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        val fragment = defaultFragment
        val transaction = supportFragmentManager.beginTransaction().apply {

            replace(R.id.fragViewer, fragment)

        }

        transaction.commit()

        possibilitiesBtn.setOnClickListener{

            val fragment = PossibilitiesFragment()
            val transaction = supportFragmentManager.beginTransaction().apply {

                replace(R.id.fragViewer, fragment)
                addToBackStack(null)
            }

            transaction.commit()


            slidingPanel.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
        }

        rCardFragBtn.setOnClickListener{

            val fragment = RandomCard()
            val transaction = supportFragmentManager.beginTransaction().apply {

                replace(R.id.fragViewer, fragment)
                addToBackStack(null)
            }

            transaction.commit()


            slidingPanel.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
        }

        slidingPanel.addPanelSlideListener(object : SlidingUpPanelLayout.PanelSlideListener {
            override fun onPanelSlide(panel: View?, slideOffset: Float) {
                dragUpArrow.rotation = slideOffset * 180f
                Log.d("PanelLog", slideOffset.toString())
            }

            override fun onPanelStateChanged(panel: View?, previousState: SlidingUpPanelLayout.PanelState?, newState: SlidingUpPanelLayout.PanelState?) {

            }
        })



    }



}
