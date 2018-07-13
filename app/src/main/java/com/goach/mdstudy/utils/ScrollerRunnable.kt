package com.goach.mdstudy.utils

import android.util.Log
import android.view.View
import android.widget.Scroller

/**
 * Goach All Rights Reserved
 *User: Goach
 *Email: goach0728@gmail.com
 *Des:
 */
open class ScrollerRunnable(private var scroller:Scroller,
                       private var childView:View,
                       private var height:Int):Runnable{
    open fun scrollToOpen(){
        val scrollY = childView.translationY.toInt()
        scroller.startScroll(0,scrollY,0,-scrollY)
        startScroll()
    }
    open fun scrollToClose(){
        val currY = childView.translationY.toInt()
        val scrollY = height - Math.abs(currY)
        scroller.startScroll(0,currY,0,-scrollY)
        startScroll()
    }
    private fun startScroll(){
        if(scroller.computeScrollOffset()){
            childView.postDelayed(this,16)
        }
    }
    override fun run() {
        if(scroller.computeScrollOffset()){
            childView.translationY = scroller.currY.toFloat()
            childView.postDelayed(this,16)
        }
    }
}