package id.moviec.domain.interactors

import id.moviec.domain.FlowableUseCase
import id.moviec.domain.PostExecutionThread
import id.moviec.domain.entities.ReviewModel
import id.moviec.domain.repositories.MovieRepository
import io.reactivex.Flowable

class GetReviews constructor(
    private val repository: MovieRepository,
    postExecutionThread: PostExecutionThread
) : FlowableUseCase<List<ReviewModel>, GetReviews.Params>(postExecutionThread) {
    override fun build(params: Params): Flowable<List<ReviewModel>> {
        return repository.getReviews(params.apiKey, params.movieId)
    }

    data class Params(val apiKey: String, val movieId: Int)
}