package com.example.room

import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.toptoche.searchablespinnerlibrary.SearchableSpinner

// megoldja a legördülő sávban a keresést

class CustomSearchableSpinner : SearchableSpinner {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_UP) {
            if (!customSpinner) {
                customSpinner = true
                return super.onTouch(v, event)
            }
            customSpinner = false
        }
        Handler().postDelayed({ customSpinner = false }, 500)
        return true
    }

    companion object {
        var customSpinner = false
    }
}