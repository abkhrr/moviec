package id.moviec.domain.interactors

import id.moviec.domain.FlowableUseCase
import id.moviec.domain.PostExecutionThread
import id.moviec.domain.entities.MovieModel
import id.moviec.domain.repositories.MovieRepository
import io.reactivex.Flowable

class DeleteFavoriteMovie constructor(
    private val repository: MovieRepository,
    postExecutionThread: PostExecutionThread
) : FlowableUseCase<Int, DeleteFavoriteMovie.Params>(postExecutionThread) {
    override fun build(params: Params): Flowable<Int> {
        return Flowable.just(
            repository.deleteFavoriteMovie(params.movieModel)
        )
    }

    data class Params(val movieModel: MovieModel)
}

