package id.moviec.domain.interactors

import id.moviec.domain.FlowableUseCase
import id.moviec.domain.PostExecutionThread
import id.moviec.domain.entities.MovieModel
import id.moviec.domain.repositories.MovieRepository
import io.reactivex.Flowable

class InsertFavoriteMovie constructor(
    private val repository: MovieRepository,
    postExecutionThread: PostExecutionThread
) : FlowableUseCase<Long, InsertFavoriteMovie.Params>(postExecutionThread) {
    override fun build(params: Params): Flowable<Long> {
        return Flowable.just(repository.insertFavoriteMovie(params.movieModel))
    }

    data class Params(val movieModel: MovieModel)
}

