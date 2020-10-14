package com.bharath.agri.ui.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SpacesItemDecorationBottom(private val space: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val isLast = position == state.itemCount - 1
        val isLastButOne = position == state.itemCount - 2
        if (isLastButOne || isLast) {
            outRect.bottom = space
        }
    }
}