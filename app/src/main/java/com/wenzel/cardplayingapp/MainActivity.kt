package com.wenzel.cardplayingapp

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.transition.Fade
import android.transition.Slide
import android.view.Gravity
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import kotlinx.android.synthetic.main.activity_main.*


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

        changeFragment(defaultFragment)

        possibilitiesFragBtn.setOnClickListener{

            if(fragViewer.javaClass.simpleName != PossibilitiesFragment().javaClass.simpleName){
                changeFragment(PossibilitiesFragment())
            }

            slidingPanel.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
        }

        rCardFragBtn.setOnClickListener{

            if(fragViewer.javaClass.simpleName != RandomCard().javaClass.simpleName){
                changeFragment(RandomCard())
            }

            slidingPanel.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
        }

        ranksFragBtn.setOnClickListener{

            if(fragViewer.javaClass.simpleName != Ranks().javaClass.simpleName){
                changeFragment(Ranks())
            }

            slidingPanel.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
        }

        slidingPanel.addPanelSlideListener(object : SlidingUpPanelLayout.PanelSlideListener {
            override fun onPanelSlide(panel: View?, slideOffset: Float) {
                dragUpArrow.rotation = slideOffset * 180f
            }

            override fun onPanelStateChanged(panel: View?, previousState: SlidingUpPanelLayout.PanelState?, newState: SlidingUpPanelLayout.PanelState?) {

            }
        })



    }

    private fun changeFragment(fragment: Fragment, addToStack : Boolean = false){

        fragment.enterTransition = Fade(Fade.MODE_IN)
        fragment.exitTransition = Fade(Fade.MODE_OUT)

        val transaction = supportFragmentManager.beginTransaction().apply {

            replace(R.id.fragViewer, fragment)
            if(addToStack){
                addToBackStack(null)
            }

        }

        transaction.commit()
    }



}
