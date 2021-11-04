package id.moviec.data.local.db

import androidx.room.*
import id.moviec.domain.entities.MovieModel
import io.reactivex.Flowable

@Dao
interface MovieDao {

    @Query("SELECT * FROM MovieModel")
    fun selectFavoriteMovie(): Flowable<List<MovieModel>?>

    @Query("SELECT * FROM MovieModel WHERE id = :id")
    fun select(id: Int): Flowable<MovieModel?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movieModel: MovieModel): Long

    @Delete
    fun delete(t: MovieModel): Int

    @Query("DELETE FROM MovieModel")
    fun deleteAll()

}