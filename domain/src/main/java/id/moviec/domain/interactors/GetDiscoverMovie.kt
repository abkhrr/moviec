package id.moviec.domain.interactors

import id.moviec.domain.FlowableUseCase
import id.moviec.domain.PostExecutionThread
import id.moviec.domain.entities.MovieModel
import id.moviec.domain.repositories.MovieRepository
import io.reactivex.Flowable

class GetDiscoverMovie constructor(
    private val repository: MovieRepository,
    postExecutionThread: PostExecutionThread
) : FlowableUseCase<List<MovieModel>?, GetDiscoverMovie.Params>(postExecutionThread) {

    override fun build(params: Params): Flowable<List<MovieModel>?> =
        repository.getDiscoverMovie(params.apiKey)

    data class Params(val apiKey: String)
}