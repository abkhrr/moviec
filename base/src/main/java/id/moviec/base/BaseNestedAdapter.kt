package id.moviec.base

import androidx.recyclerview.widget.RecyclerView

abstract class BaseNestedAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var nestedItemPosition : ArrayList<Pair<String,Int>>     = arrayListOf()
    var realNestedItemPosition : ArrayList<Pair<String,Int>> = arrayListOf()
        set(value) {
            field.apply {
                clear()
                addAll(value)
            }
            nestedItemPosition.clear()
            nestedItemPosition.addAll(value)
        }

    override fun getItemCount(): Int {
        return nestedItemPosition.size
    }

}