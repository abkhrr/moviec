package id.moviec.domain.entities

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class MoviesWithPageModel(
    var page: Int,
    var movies: List<MovieModel>
): Parcelable