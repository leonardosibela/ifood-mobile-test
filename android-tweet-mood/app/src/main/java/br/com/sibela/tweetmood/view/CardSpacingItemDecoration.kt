package br.com.sibela.tweetmood.view

import android.content.Context
import android.graphics.Rect
import android.support.annotation.DimenRes
import android.support.v7.widget.RecyclerView
import android.view.View

class CardSpacingItemDecoration : RecyclerView.ItemDecoration {

    private var mItemOffset: Int

    constructor(itemOffset: Int) {
        mItemOffset = itemOffset
    }

    constructor(context: Context, @DimenRes itemOffsetId: Int) :
            this(context.resources.getDimensionPixelSize(itemOffsetId)
    )

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        if (position == parent.adapter!!.itemCount - 1) {
            super.getItemOffsets(outRect, view, parent, state)
            outRect.set(mItemOffset, mItemOffset, mItemOffset, mItemOffset)
        } else {
            super.getItemOffsets(outRect, view, parent, state)
            outRect.set(mItemOffset, mItemOffset, mItemOffset, 0)
        }
    }
}