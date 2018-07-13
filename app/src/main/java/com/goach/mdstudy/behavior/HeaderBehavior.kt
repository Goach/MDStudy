package com.goach.mdstudy.behavior

import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Scroller
import com.goach.mdstudy.utils.ScrollerRunnable
import com.goach.mdstudy.utils.Utils

/**
 * Goach All Rights Reserved
 *User: Goach
 *Email: goach0728@gmail.com
 *Des:
 */
class HeaderBehavior: CoordinatorLayout.Behavior<View> {
    private var mChildView: View? =null

    private var axes:Int = ViewCompat.SCROLL_AXIS_VERTICAL
    private var type:Int = 0

    private var mScroller: Scroller
    private var mHeight:Float = 0f
    private var mScrollRunnable:ScrollerRunnable?= null
    private var mStartHeaderHidden:Boolean = true //是不是一开始头部就隐藏了
    private var velocityY:Float = 0f
    constructor(ctx: Context,attributeSet: AttributeSet):super(ctx,attributeSet){
        mScroller = Scroller(ctx)
        mHeight = Utils.getScrollHeight(ctx)
    }
    //什么方向可以滚动,并且到了头部高度就不滚动
    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout, child: View, directTargetChild: View, target: View, axes: Int, type: Int): Boolean {
        this.mChildView = child
        this.axes = axes
        this.type = type
        return (axes == ViewCompat.SCROLL_AXIS_VERTICAL) && !isClose()
    }
    //滚动监听
    override fun onNestedPreScroll(coordinatorLayout: CoordinatorLayout, child: View, target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)
        //降低移动距离，防止抖动,ScrollFriction太大还是会抖动。。。。
        val tempDy = dy.toFloat()/(Utils.getScrollFriction()*2)
        child.translationY = Utils.range(-mHeight,0f,child.translationY - tempDy)
        consumed[1] = dy

    }
    override fun onNestedPreFling(coordinatorLayout: CoordinatorLayout, child: View, target: View, velocityX: Float, velocityY: Float): Boolean {
        this.velocityY = velocityY
        return !isClose()
    }
    override fun onInterceptTouchEvent(parent: CoordinatorLayout, child: View, ev: MotionEvent): Boolean {
        when(ev.action){
            MotionEvent.ACTION_DOWN -> {
                mStartHeaderHidden = isClose()
            }
            MotionEvent.ACTION_MOVE -> {
                if(isClose()&&!mStartHeaderHidden) parent.onStartNestedScroll(parent,child,axes,type)
            }
            MotionEvent.ACTION_UP -> {
                handlerActionUp()
            }
        }
        return super.onInterceptTouchEvent(parent, child, ev)
    }

    private fun createRunnable(){
        if(mScrollRunnable==null) mScrollRunnable = ScrollerRunnable(mScroller,mChildView!!,(mHeight+0.5f).toInt())
    }
    private fun handlerActionUp(){
        createRunnable()
        if(velocityY > 10000){
            mScrollRunnable?.scrollToClose()
            return
        }
        if(Math.abs(mChildView!!.translationY) < (mHeight /2)){
            mScrollRunnable?.scrollToOpen()
        }else{
            mScrollRunnable?.scrollToClose()
        }
    }
    open fun isClose():Boolean{
        return mChildView!=null && mChildView!!.translationY.toInt() <= - mHeight.toInt()
    }
    open fun scrollToOpen(){
        createRunnable()
        mScrollRunnable?.scrollToOpen()
    }
}