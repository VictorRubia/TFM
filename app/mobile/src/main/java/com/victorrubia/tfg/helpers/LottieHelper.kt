package com.victorrubia.tfg.helpers

import android.animation.Animator
import android.animation.ValueAnimator
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import com.airbnb.lottie.LottieAnimationView

/**
 * Helper class to manage the animation of the lottie animation
 */
class LottieHelper(animationView: LottieAnimationView) {

    var animation = animationView

    /**
     * Method to start the animation
     */
    fun showLoading(){
        animation.visibility = View.VISIBLE
        animation.setMinAndMaxFrame(0, 59)
        animation.playAnimation()
        animation.repeatCount = ValueAnimator.INFINITE
    }

    /**
     * Method to show the animation when the user is logged in successfully
     */
    fun showLoadingSuccessful(context : Context?, intent : Intent?){
        completeAnimationBeforeNewActivity(context, intent)
        animation.setMinAndMaxFrame(59, 89)
        animation.repeatCount = 0
    }

    /**
     * Method to show the animation when the login fails
     */
    fun showLoadingError(){
        animation.setMinAndMaxFrame(90, 138)
        animation.repeatCount = 0
    }

    /**
     * Method to show the animation entirely before starting a new activity
     */
    private fun completeAnimationBeforeNewActivity(context : Context?, intent : Intent?){

        animation.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
            }

            override fun onAnimationEnd(animation: Animator) {
                Log.e("Animation:", "end")
                //Your code for remove the fragment
                if(intent != null && context != null) {
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    ContextCompat.startActivity(context, intent, null)
                    (context as Activity).finish()
                }
            }

            override fun onAnimationCancel(animation: Animator) {
            }

            override fun onAnimationRepeat(animation: Animator) {
            }
        })
    }

}