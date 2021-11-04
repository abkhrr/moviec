package id.moviec.domain.interactors

import id.moviec.domain.FlowableUseCase
import id.moviec.domain.PostExecutionThread
import id.moviec.domain.entities.DetailMovieModel
import id.moviec.domain.repositories.MovieRepository
import io.reactivex.Flowable

class GetDetailMovie constructor(
    private val repository: MovieRepository,
    postExecutionThread: PostExecutionThread
) : FlowableUseCase<DetailMovieModel, GetDetailMovie.Params>(postExecutionThread) {
    override fun build(params: Params): Flowable<DetailMovieModel> {
        return repository.getMovieDetails(params.apiKey, params.movieId)
    }

    data class Params(val apiKey: String, val movieId: Int)
}