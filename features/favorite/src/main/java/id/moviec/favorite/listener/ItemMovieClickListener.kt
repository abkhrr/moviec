package id.moviec.favorite.listener

class ItemMovieClickListener(private val onMovieClicked: () -> Unit){
    fun movieClicked() = onMovieClicked.invoke()
}