package id.moviec.home.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.moviec.domain.entities.MovieModel
import id.moviec.domain.repositories.MovieRepository
import id.moviec.home.databinding.ItemHomeHeaderBinding
import id.moviec.home.listener.ItemMovieClickListener
import id.moviec.home.listener.MovieClickListener
import id.moviec.utils.constant.Const

class HeaderViewHolder(private val binding: ItemHomeHeaderBinding, private val listener: MovieClickListener): RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: MovieModel){
        val url = movie.posterPath
        url?.let {
            val addedUrl =
                if (!url.contains("https://") || !url.contains("http://")) String.format(Const.MOVIE_THUMBNAIL_BASE_URL_DOUBLE_EXTRA_LARGE, url)
                else url

            Glide.with(binding.root.context).load(addedUrl).into(binding.backgroundImage)
        }
        binding.item = ItemMovieClickListener { listener.onMovieClick(movie) }
    }

}