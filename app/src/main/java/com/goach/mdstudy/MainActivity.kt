package com.goach.mdstudy

import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.ViewGroup
import android.widget.FrameLayout
import com.goach.mdstudy.behavior.HeaderBehavior
import com.goach.mdstudy.utils.Utils
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.bottomPadding
import org.jetbrains.anko.find
import org.jetbrains.anko.onClick

/**
 * Goach All Rights Reserved
 *User: Goach
 *Email: goach0728@gmail.com
 *Des:参考博客 https://blog.csdn.net/qq_32441151/article/details/74619796
 */
class MainActivity:AppCompatActivity() {
    private lateinit var mHeaderBehavior:HeaderBehavior
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mHeaderBehavior = (find<FrameLayout>(R.id.fl_head).layoutParams as CoordinatorLayout.LayoutParams).behavior as HeaderBehavior
        ivArrow.onClick {
            openHeader()
        }
    }
    private fun openHeader(){
        if(mHeaderBehavior.isClose()){
            nsv.scrollTo(0,0)
            mHeaderBehavior.scrollToOpen()
        }
    }

    override fun onBackPressed() {
        if(mHeaderBehavior.isClose()){
            nsv.scrollTo(0,0)
            mHeaderBehavior.scrollToOpen()
        }else{
            super.onBackPressed()
        }

    }
}