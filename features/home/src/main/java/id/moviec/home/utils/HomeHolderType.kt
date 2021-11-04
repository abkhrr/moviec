package id.moviec.home.utils

object HomeHolderType {

    const val ITEM_POPULAR_MOVIE        = 0
    const val ITEM_TOP_RATED_MOVIE      = 1
    const val ITEM_NOW_PLAYING_MOVIE    = 2
    const val ITEM_MOVIE_DISCOVER       = 3
    const val ITEM_EMPTY                = 4


    //HOLDER ITEM NAME
    const val HOLDER_NAME_NOW_PLAYING   = "NOW_PLAYING"
    const val HOLDER_NAME_TOP_RATED     = "TOP_RATED"
    const val HOLDER_NAME_POPULAR       = "POPULAR"
    const val HOLDER_NAME_DISCOVER      = "DISCOVER"

    fun getHomeHolderViewType(holderName : Any) = when(holderName){
        HOLDER_NAME_POPULAR            -> ITEM_POPULAR_MOVIE
        HOLDER_NAME_TOP_RATED          -> ITEM_TOP_RATED_MOVIE
        HOLDER_NAME_NOW_PLAYING        -> ITEM_NOW_PLAYING_MOVIE
        HOLDER_NAME_DISCOVER           -> ITEM_MOVIE_DISCOVER
        else                           -> ITEM_EMPTY
    }

}