package id.moviec.favorite.listener

import id.moviec.domain.entities.MovieModel

interface MovieClickListener {
    fun onMovieClick(movie: MovieModel)
}