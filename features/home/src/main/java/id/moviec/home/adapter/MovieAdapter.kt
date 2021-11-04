package id.moviec.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.moviec.domain.entities.MovieModel
import id.moviec.home.databinding.ItemHomeHeaderBinding
import id.moviec.home.databinding.ItemHomeSectionBinding
import id.moviec.home.listener.MovieClickListener
import id.moviec.home.viewholder.GridViewHolder
import id.moviec.home.viewholder.HeaderViewHolder
import id.moviec.home.viewholder.SectionViewHolder
import id.moviec.utils.recyclerview.DiffUtil
import kotlin.properties.Delegates

class MovieAdapter(private val listener: MovieClickListener): RecyclerView.Adapter<RecyclerView.ViewHolder>(), DiffUtil {

    var data: List<MovieModel> by Delegates.observable(emptyList()) { _, old, new ->
        notifyTheAdapter(old, new) { oldItems, newItems -> oldItems.id == newItems.id }
    }

    var isHeader: Boolean   = false
    var isGridView: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (isHeader)
            HeaderViewHolder(
                ItemHomeHeaderBinding.inflate(inflater, parent, false),
                listener
            )
        else if(isGridView)
            GridViewHolder(
                ItemHomeSectionBinding.inflate(inflater, parent, false),
                listener
            )
        else
            SectionViewHolder(
                ItemHomeSectionBinding.inflate(inflater, parent, false),
                listener
            )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> {
                setItemHeaderData(holder, position)
            }
            is SectionViewHolder -> {
                setItemSectionData(holder, position)
            }
            is GridViewHolder -> {
                setItemGridData(holder, position)
            }
        }
    }

    override fun getItemCount(): Int = data.size

    private fun setItemHeaderData(viewHolder: HeaderViewHolder, moviePosition: Int) {
        viewHolder.bind(data[moviePosition])
    }

    private fun setItemSectionData(viewHolder: SectionViewHolder, moviePosition: Int) {
        viewHolder.bind(data[moviePosition])
    }

    private fun setItemGridData(viewHolder: GridViewHolder, moviePosition: Int) {
        viewHolder.bind(data[moviePosition])
    }

}