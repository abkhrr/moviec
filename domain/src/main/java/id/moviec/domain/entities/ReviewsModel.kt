package id.moviec.domain.entities

data class ReviewsModel(
    val id: Int?,
    val page: Int?,
    val results: List<ReviewModel>?,
    val totalPages: Int?,
    val totalResults: Int?
)