package com.lebedevsd.earthquake.util

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class VerticalSpaceItemDecoration(@DimenRes verticalSpaceHeight: Int, context: Context) : ItemDecoration() {

    private var verticalSpaceHeightInDp: Int = context.resources.getDimension(verticalSpaceHeight).toInt()

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.bottom = verticalSpaceHeightInDp
    }
}
