package id.moviec.home.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import id.moviec.domain.entities.MovieModel
import id.moviec.home.databinding.ItemHomeSectionBinding
import id.moviec.home.listener.ItemMovieClickListener
import id.moviec.home.listener.MovieClickListener
import id.moviec.utils.constant.Const

class GridViewHolder(private val binding: ItemHomeSectionBinding, private val listener: MovieClickListener): RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: MovieModel){
        val url = movie.posterPath
        url?.let {
            val addedUrl =
                if (!url.contains("https://") || !url.contains("http://")) String.format(Const.MOVIE_THUMBNAIL_BASE_URL_LARGE, url)
                else url

            Glide.with(binding.root.context).load(addedUrl).transform(CenterCrop()).into(binding.posterImage)
        }
        binding.item = ItemMovieClickListener { listener.onMovieClick(movie) }
    }

}