package id.moviec.utils.recyclerview

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class DecorationOverlappingItem(private vararg val positionToOffsetPair: Pair<Int,Int>) : RecyclerView.ItemDecoration() {

    private val defaultOffset = -80

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position    = parent.getChildAdapterPosition(view)

        when {
            positionToOffsetPair.isEmpty() -> outRect.top = defaultOffset
            else -> positionToOffsetPair.firstOrNull { it.first == position }?.let{ outRect.top = it.second }
        }
    }
}