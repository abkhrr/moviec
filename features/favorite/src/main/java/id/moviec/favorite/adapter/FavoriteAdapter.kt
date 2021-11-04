package id.moviec.favorite.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import id.moviec.domain.entities.MovieModel
import id.moviec.favorite.databinding.ItemFavoriteBinding
import id.moviec.favorite.listener.ItemMovieClickListener
import id.moviec.favorite.listener.MovieClickListener
import id.moviec.utils.constant.Const
import id.moviec.utils.recyclerview.DiffUtil
import kotlin.properties.Delegates

class FavoriteAdapter(private val listener: MovieClickListener): RecyclerView.Adapter<FavoriteAdapter.ViewHolder>(), DiffUtil {

    var data: List<MovieModel> by Delegates.observable(emptyList()) { _, old, new ->
        notifyTheAdapter(old, new) { oldItems, newItems -> oldItems.id == newItems.id }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemFavoriteBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    inner class ViewHolder(private val binding: ItemFavoriteBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(favoriteMovie: MovieModel){
            val url = favoriteMovie.posterPath
            url?.let {
                val addedUrl =
                    if (!url.contains("https://") || !url.contains("http://")) String.format(Const.MOVIE_THUMBNAIL_BASE_URL_LARGE, url)
                    else url

                Glide.with(binding.root.context).load(addedUrl).transform(CenterCrop()).into(binding.posterImage)
            }
            binding.item = ItemMovieClickListener { listener.onMovieClick(favoriteMovie) }
        }
    }
}