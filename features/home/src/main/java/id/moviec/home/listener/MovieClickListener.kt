package id.moviec.home.listener

import id.moviec.domain.entities.MovieModel

interface MovieClickListener {
    fun onMovieClick(movie: MovieModel)
}