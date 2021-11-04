package id.moviec.home.listener

class ItemMovieClickListener(private val onMovieClicked: () -> Unit){
    fun movieClicked() = onMovieClicked.invoke()
}