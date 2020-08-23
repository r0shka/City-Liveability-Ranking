package com.example.cityranking.utilities.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.cityranking.R
import com.example.cityranking.mainscreen.ViewPagerAdapter
import com.example.cityranking.utilities.Utils
import kotlinx.android.synthetic.main.carousel_view.view.*

class CarouselView(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    init {
        inflate(context, R.layout.carousel_view, this)

        Utils.setupViewPagerAnimation(carousel_view_pager, context)
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CarouselView,
            0,
            0
        ).apply {
            try {
                if (getBoolean(R.styleable.CarouselView_showTitle, true)) {
                    carousel_title.text = getString(R.styleable.CarouselView_titleText)
                    carousel_title.visibility = View.VISIBLE
                } else {
                    carousel_title.visibility = View.GONE
                }

                if (getBoolean(R.styleable.CarouselView_showDescription, true)) {
                    carousel_description.text = getString(R.styleable.CarouselView_descriptionText)
                    carousel_description.visibility = View.VISIBLE
                } else {
                    carousel_description.visibility = View.GONE
                }
            } finally {
                recycle()
            }
        }
    }

    fun setCarouselTitle(text: String) {
        carousel_title.text = text
        invalidate()
        requestLayout()
    }

    fun setCarouselDescription(text: String) {
        carousel_description.text = text
        invalidate()
        requestLayout()
    }

    fun setAdapter(adapter: ViewPagerAdapter) {
        carousel_view_pager.adapter = adapter
        invalidate()
        requestLayout()
    }

    fun setClickListener(onClickListener: OnClickListener) {
        carousel_see_more_button.setOnClickListener(onClickListener)
        invalidate()
        requestLayout()
    }

}