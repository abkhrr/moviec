package id.moviec.data.mapper

import id.moviec.domain.dto.ReviewDto
import id.moviec.domain.entities.ReviewModel

class ReviewMapper : Mapper<ReviewDto?, List<ReviewModel>?>() {
    override fun apply(from: ReviewDto?): List<ReviewModel>? {
        return from?.results?.map { resultReview ->
            ReviewModel(
                resultReview.author,
                resultReview.content,
                resultReview.id,
                resultReview.url
            )
        }
    }
}