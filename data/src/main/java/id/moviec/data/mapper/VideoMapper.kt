package id.moviec.data.mapper

import id.moviec.domain.dto.VideoDto
import id.moviec.domain.entities.VideoModel

class VideoMapper : Mapper<VideoDto?, List<VideoModel>?>() {
    override fun apply(from: VideoDto?): List<VideoModel>? {
        return from?.results?.map { video ->
            VideoModel(
                video.id,
                video.iso31661,
                video.iso6391,
                video.key,
                video.name,
                video.site,
                video.size,
                video.type
            )
        }
    }
}