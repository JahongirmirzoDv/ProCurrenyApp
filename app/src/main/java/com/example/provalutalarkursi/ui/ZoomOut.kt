package com.example.provalutalarkursi.ui

import android.view.View
import androidx.viewpager2.widget.ViewPager2

class ZoomOut : ViewPager2.PageTransformer {
    private val MIN_SCALE = 0.65f
    private val MIN_ALPHA = 0.3f
    override fun transformPage(page: View, position: Float) {
        if (position < -1) {  // [-Infinity,-1)
            // This page is way off-screen to the left.
            page.alpha = 0f;

        } else if (position <= 1) { // [-1,1]

            page.scaleX = Math.max(MIN_SCALE, 1 - Math.abs(position));
            page.scaleY = Math.max(MIN_SCALE, 1 - Math.abs(position));
            page.alpha = Math.max(MIN_ALPHA, 1 - Math.abs(position));

        } else {  // (1,+Infinity]
            // This page is way off-screen to the right.
            page.setAlpha(0f);

        }
    }
}