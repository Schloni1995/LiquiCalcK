package com.example.toni.liquidcalccompatible.objects

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ListView

/**
 * Created by Toni on 19.03.2018.
 */

class NonScrollListView : ListView {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    public override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val heightMeasureSpecCustom = View.MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE shr 2, View.MeasureSpec.AT_MOST)
        super.onMeasure(widthMeasureSpec, heightMeasureSpecCustom)
        val params = layoutParams
        params.height = measuredHeight
    }
}
