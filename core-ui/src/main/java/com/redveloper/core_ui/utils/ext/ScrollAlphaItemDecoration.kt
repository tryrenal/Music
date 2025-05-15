package com.redveloper.core_ui.utils.ext

import android.graphics.Canvas
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ScrollAlphaItemDecoration : RecyclerView.ItemDecoration {

    companion object{
        const val DEFAULT_MIN_ALPHA = 0.5F
        const val DEFAULT_MAX_ALPHA = 1f
        const val DEFAULT_FADE_START_POSITION = 0.75f
    }

    private var minAlpha: Float = DEFAULT_MIN_ALPHA
    private var maxAlpha: Float = DEFAULT_MAX_ALPHA
    private var fadeStartPosition: Float = DEFAULT_FADE_START_POSITION
    private var fadeFromBottom: Boolean = true

    constructor(
        minAlpha: Float = DEFAULT_MIN_ALPHA ,
        maxAlpha: Float = DEFAULT_MAX_ALPHA,
        fadeStartPosition: Float = DEFAULT_FADE_START_POSITION,
        fadeFromBottom: Boolean = true
    ){
        this.minAlpha = minAlpha
        this.maxAlpha = maxAlpha
        this.fadeStartPosition = fadeStartPosition
        this.fadeFromBottom = fadeFromBottom
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)

        var layoutManager: RecyclerView.LayoutManager? = parent.layoutManager
        if (layoutManager == null || layoutManager.childCount == 0)
            return

        var firstVisiblePosition = if (layoutManager is GridLayoutManager) layoutManager.findFirstVisibleItemPosition() else (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
        var lastVisiblePosition = if (layoutManager is GridLayoutManager) layoutManager.findLastVisibleItemPosition() else (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()

        for (index in firstVisiblePosition..lastVisiblePosition){
            val view = layoutManager.findViewByPosition(index)
            if(view != null){
                val alpha = calculateAlpha(view, parent)
                view.alpha = alpha
            }
        }
    }

    private fun calculateAlpha(view: View, parent: RecyclerView): Float {
        val viewLocation = IntArray(2)
        view.getLocationInWindow(viewLocation)
        val recyclerLocation = IntArray(2)
        parent.getLocationInWindow(recyclerLocation)
        val viewCenterY = viewLocation[1] + view.height / 2f - recyclerLocation[1]
        val recyclerHeight = parent.height.toFloat()
        var normalizedPosition = viewCenterY / recyclerHeight
        if (!fadeFromBottom) {
            normalizedPosition = 1f - normalizedPosition
        }
        val alpha = if (normalizedPosition <= fadeStartPosition) {
            maxAlpha // Fully visible above fade start position
        } else {
            val fadeRange = 1f - fadeStartPosition
            val positionInRange = (normalizedPosition - fadeStartPosition) / fadeRange
            maxAlpha - (maxAlpha - minAlpha) * positionInRange
        }
        val interpolatedAlpha = AccelerateDecelerateInterpolator().getInterpolation(alpha)
        return interpolatedAlpha.coerceIn(minAlpha, maxAlpha)
    }
}