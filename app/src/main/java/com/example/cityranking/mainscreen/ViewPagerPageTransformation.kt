package com.example.cityranking.mainscreen

import android.view.View
import androidx.core.view.ViewCompat
import androidx.viewpager2.widget.ViewPager2
import java.lang.Math.abs
import java.lang.Math.max

class ViewPagerPageTransformation(val offsetPx: Int) : ViewPager2.PageTransformer {

    override fun transformPage(page: View, position: Float) {
        val viewPager = page.parent.parent as ViewPager2
        val offset = position * -(2 * offsetPx)
        val minScale = 0.90f

        if (viewPager.orientation == ViewPager2.ORIENTATION_HORIZONTAL) {
            if (ViewCompat.getLayoutDirection(viewPager) == ViewCompat.LAYOUT_DIRECTION_RTL) {
                page.translationX = -offset
            } else {
                page.translationX = offset
            }
        } else {
            page.translationY = offset
        }

        page.apply {
            when {
                position < -1 -> {
                    // Left page
                    scaleX = minScale
                    scaleY = minScale
                }
                position <= 1 -> {
                    // Current page
                    val scaleFactor = max(minScale, 1 - abs(position/3))
                    // Scale the page down (between MIN_SCALE and 1)
                    scaleX = scaleFactor
                    scaleY = scaleFactor
                }
                else -> {
                    // Right page
                    scaleX = minScale
                    scaleY = minScale
                }
            }
        }
    }
}