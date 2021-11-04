package id.moviec.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.moviec.detail.databinding.ItemSimilarMovieBinding
import id.moviec.domain.entities.MovieModel
import id.moviec.utils.constant.Const
import id.moviec.utils.recyclerview.DiffUtil
import kotlin.properties.Delegates

class SimilarMovieAdapter: RecyclerView.Adapter<SimilarMovieAdapter.ViewHolder>(), DiffUtil {

    var data: List<MovieModel> by Delegates.observable(emptyList()) { _, old, new ->
        notifyTheAdapter(old, new) { oldItems, newItems -> oldItems.id == newItems.id }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemSimilarMovieBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    inner class ViewHolder(private val binding: ItemSimilarMovieBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(movie: MovieModel){
            val url = movie.posterPath
            url?.let {
                val addedUrl =
                    if (!url.contains("https://") || !url.contains("http://")) String.format(Const.MOVIE_THUMBNAIL_BASE_URL_DOUBLE_EXTRA_LARGE, url)
                    else url

                Glide.with(binding.root.context).load(addedUrl).into(binding.posterImage)
            }
        }
    }

}