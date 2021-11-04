package id.moviec.domain.interactors

import id.moviec.domain.FlowableUseCase
import id.moviec.domain.PostExecutionThread
import id.moviec.domain.entities.MovieModel
import id.moviec.domain.repositories.MovieRepository
import io.reactivex.Flowable

class GetFavoriteMovie constructor(
    private val repository: MovieRepository,
    postExecutionThread: PostExecutionThread
): FlowableUseCase<MovieModel?, GetFavoriteMovie.Params>(postExecutionThread) {
    override fun build(params: Params): Flowable<MovieModel?> {
        return repository.getFavoriteMovie(params.movieId)
    }

    data class Params(val movieId: Int)
}
