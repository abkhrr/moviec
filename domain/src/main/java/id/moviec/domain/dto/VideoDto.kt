package id.moviec.domain.dto

import com.google.gson.annotations.SerializedName

data class VideoDto(
    @SerializedName("id")
    val id: Int?,

    @SerializedName("results")
    val results: List<ResultVideo>?
)