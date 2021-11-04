package com.ipro.utils.ext.scroll

import androidx.recyclerview.widget.RecyclerView

class ParallaxScroll: RecyclerView.OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val firstChild           = recyclerView.getChildAt(0)
        val absoluteShiftValue   = -(firstChild?.top ?: 0)
        if (firstChild!= null && recyclerView.getChildAdapterPosition(firstChild) == 0){
            firstChild.translationY = absoluteShiftValue / 1.2f
        }

        val secondChild = recyclerView.getChildAt(1)
        if (secondChild!= null && recyclerView.getChildAdapterPosition(secondChild) == 1){
            secondChild.translationY =  absoluteShiftValue / 4.5f
        }
    }
}