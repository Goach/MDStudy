package com.goach.mdstudy.behavior

import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.goach.mdstudy.R
import com.goach.mdstudy.utils.Utils

/**
 * Goach All Rights Reserved
 *User: Goach
 *Email: goach0728@gmail.com
 *Des:
 */
class ContentBehavior:HeaderScrollingViewBehavior {
    constructor(ctx: Context, attrs: AttributeSet): super(ctx,attrs)

    override fun layoutDependsOn(parent: CoordinatorLayout?, child: View?, dependency: View): Boolean {
        return dependency.id == R.id.fl_head
    }

    override fun findFirstDependency(views: MutableList<View>): View? {
        for (i in views.indices) {

            val view = views[i]
            if (view.id == R.id.fl_head) {
                return view
            }
        }
        return null
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout?, child: View?, dependency: View?): Boolean {
        offsetChild(child!!, dependency!!)
        return super.onDependentViewChanged(parent, child, dependency)
    }

    private fun offsetChild(child: View, dependency: View) {
        //-0.5f滑动慢，最后一下没监听到
        child.translationY = (dependency.translationY - 0.5f)  * Utils.getScrollFriction()
    }
}