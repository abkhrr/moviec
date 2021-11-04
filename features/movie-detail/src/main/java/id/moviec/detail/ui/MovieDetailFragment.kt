package id.moviec.detail.ui

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.google.android.material.tabs.TabLayout
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.FragmentScoped
import id.moviec.base.BaseFragment
import id.moviec.detail.R
import id.moviec.detail.adapter.SimilarMovieAdapter
import id.moviec.detail.databinding.FragmentMovieDetailBinding
import id.moviec.detail.viemodel.MovieDetailViewModel
import id.moviec.domain.entities.DetailMovieModel
import id.moviec.domain.entities.MovieModel
import id.moviec.domain.entities.VideoModel
import id.moviec.domain.subscriber.ResultState
import id.moviec.utils.ext.hide
import id.moviec.utils.ext.show
import id.moviec.utils.navigation.NavigationCommand
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
@FragmentScoped
class MovieDetailFragment : BaseFragment<FragmentMovieDetailBinding, MovieDetailViewModel>() {

    @FragmentScoped
    override val binding: FragmentMovieDetailBinding by lazy { FragmentMovieDetailBinding.inflate(layoutInflater) }
    override val viewModel: MovieDetailViewModel by viewModels()

    var isVideoRestarted       = false
    var player: YouTubePlayer? = null
    var bannerVideoLoaded      = false
    private var isFavorite     = false
    private lateinit var similarMoviesItemsAdapter: SimilarMovieAdapter
    private val detailMovie: MovieModel? by lazy { arguments?.getParcelable("movie") }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        similarMoviesItemsAdapter = SimilarMovieAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupDetails()
        initAdapter()
    }

    private fun setupUI() {
        binding.toolbar.setNavigationOnClickListener { navigate(NavigationCommand.Back) }
        binding.content.show()
        binding.youtubePlayerView.show()
        binding.thumbnail.container.hide()
        binding.thumbnail.playContainer.setOnClickListener { replayVideo() }
        binding.header.favoriteIcons.setOnClickListener { setFavoriteMovie() }

        binding.header.overviewText.setOnClickListener {
            binding.header.overviewText.maxLines    = 10
            binding.header.overviewText.isClickable = false
        }

        binding.youtubePlayerView.addYouTubePlayerListener(youTubePlayerListener)
        binding.tabLayout.addOnTabSelectedListener(tabSelectedListener)
    }

    private fun initAdapter() {
        binding.similarMoviesList.adapter                  = similarMoviesItemsAdapter
        binding.similarMoviesList.isNestedScrollingEnabled = false
    }

    private fun setupDetails(){
        Glide.with(this).load(detailMovie?.backdropPath).transform(CenterCrop())
            .into(binding.thumbnail.backdropImage)
        binding.header.titleText.text    = detailMovie?.title
        binding.header.overviewText.text = detailMovie?.overview
        binding.header.yearText.text     = detailMovie?.releaseDate
        getDetailData()
    }

    private fun getDetailData(){
        detailMovie?.id?.let {
            viewModel.fetchReviews(it)
            viewModel.fetchVideos(it)
            viewModel.fetchFavoriteMovie(it)
            viewModel.fetchDetailMovie(it)
            viewModel.fetchSimilarMovie(it)
        }
        initObserver()
    }

    private fun initObserver() {
        viewModel.getVideosLiveData.observe(viewLifecycleOwner, {
            initVideo(it)
        })
        viewModel.getDetailMovieLiveData.observe(viewLifecycleOwner, {
            setDetail(it)
        })
        viewModel.getSimilarMovieLiveData.observe(viewLifecycleOwner, {
            setSimilarMovieResponse(it)
        })
        viewModel.getFavoriteMovieLiveData.observe(viewLifecycleOwner, {
            checkingMovieFavoriteStatus(it)
        })
        viewModel.insertMovieLiveData.observe(viewLifecycleOwner, {
            afterInsertFavoriteMovie(it)
        })
        viewModel.deleteMovieLiveData.observe(viewLifecycleOwner, {
            afterDeleteFavoriteMovie(it)
        })
    }

    private fun checkingMovieFavoriteStatus(it: ResultState<MovieModel?>?) {
        it?.let {
            when (it) {
                is ResultState.Success -> {
                    isFavorite = true
                }
            }
            setFavoriteIcon()
        }
    }

    private fun setFavoriteMovie() {
        detailMovie?.let {
            if (!isFavorite) {
                viewModel.insertFavoriteMovie(it)
            } else {
                viewModel.deleteFavoriteMovie(it)
            }
        }
    }

    private fun afterDeleteFavoriteMovie(it: ResultState<Int>?) {
        when (it) {
            is ResultState.Success -> {
                isFavorite = !isFavorite
                Toast.makeText(context, getString(R.string.deleted_favorite), Toast.LENGTH_SHORT)
                    .show()
            }
            is ResultState.Error -> {
                Toast.makeText(context, it.throwable.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        }
        setFavoriteIcon()
    }

    private fun afterInsertFavoriteMovie(it: ResultState<Long>?) {
        when (it) {
            is ResultState.Success -> {
                isFavorite = !isFavorite
                Toast.makeText(context, getString(R.string.added_favorite), Toast.LENGTH_SHORT)
                    .show()
            }
            is ResultState.Error -> {
                Toast.makeText(context, it.throwable.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        }
        setFavoriteIcon()
    }

    private fun setFavoriteIcon() {
        binding.header.favoriteIcons.setImageDrawable(getFavoriteIcon())
    }

    private fun getFavoriteIcon(): Drawable? {
        return if (isFavorite) ContextCompat.getDrawable(
            requireContext(),
            R.drawable.ic_svg_favorite_24
        ) else ContextCompat.getDrawable(requireContext(), R.drawable.ic_svg_favorite_border_24)
    }


    private fun setSimilarMovieResponse(it: ResultState<List<MovieModel>?>){
        when (it) {
            is ResultState.Success -> {
                setSimilarMovieItems(it.data)
            }
            is ResultState.Error -> {
                Toast.makeText(context, it.throwable.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setSimilarMovieItems(data: List<MovieModel>?){
        data?.let {
            similarMoviesItemsAdapter.data = it
        }
    }

    private fun setDetail(it: ResultState<DetailMovieModel>?) {
        when (it) {
            is ResultState.Success -> {
                setDetailData(it.data)
            }
            is ResultState.Error -> {
                Toast.makeText(context, it.throwable.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setDetailData(detail: DetailMovieModel){
        val minutes = detail.runtime?.rem(60)
        val hours   = detail.runtime?.div(60)

        val completeRunTime = "$hours hr $minutes m"

        binding.header.runtimeText.text  = completeRunTime
        binding.header.ratingText.text   = detail.voteAverage.toString()
    }

    private fun initVideo(it: ResultState<List<VideoModel>>?){
        when (it) {
            is ResultState.Success -> {
                checkAndLoadVideo(it.data)
            }
            is ResultState.Error -> {
                Toast.makeText(context, it.throwable.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkAndLoadVideo(videos: List<VideoModel>) {
        val firstVideo = videos.firstOrNull { video -> (video.type == "Trailer") && video.site == "YouTube" }
        if (firstVideo != null) {
            if (!bannerVideoLoaded) {
                binding.youtubePlayerView.getYouTubePlayerWhenReady(object : YouTubePlayerCallback {
                    override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {
                        player = youTubePlayer
                        youTubePlayer.loadVideo(firstVideo.key.toString(), 0f)
                        bannerVideoLoaded = true
                    }
                })
            }
        } else {
            binding.thumbnail.playContainer.visibility = View.INVISIBLE
        }
    }

    private fun replayVideo() {
        if (player != null) {
            player!!.seekTo(0f)
            lifecycleScope.launch {
                delay(500)
                binding.youtubePlayerView.show()
                binding.thumbnail.container.hide()
            }
        }
    }

    private val youTubePlayerListener = object : AbstractYouTubePlayerListener() {
        override fun onCurrentSecond(youTubePlayer: YouTubePlayer, second: Float) {
            if (!isVideoRestarted && second > 3.2) {
                isVideoRestarted = true
                lifecycleScope.launch {
                    youTubePlayer.seekTo(0f)
                    youTubePlayer.unMute()
                    binding.youtubePlayerView.getPlayerUiController().showUi(false)
                    delay(50)
                    binding.thumbnail.container.show()
                    binding.thumbnail.videoLoader.hide()
                    binding.thumbnail.container.hide()
                    binding.youtubePlayerView.show()
                    delay(1000)
                    binding.youtubePlayerView.getPlayerUiController().showUi(true)
                }
            }
        }

        override fun onStateChange(
            youTubePlayer: YouTubePlayer,
            state: PlayerConstants.PlayerState,
        ) {
            if ((state == PlayerConstants.PlayerState.UNSTARTED) && !isVideoRestarted) {
                youTubePlayer.mute()
            }

            if (state == PlayerConstants.PlayerState.ENDED) {
                binding.youtubePlayerView.hide()
                binding.thumbnail.container.show()
                binding.thumbnail.videoLoader.hide()
            }
        }
    }

    private val tabSelectedListener = object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab?) {
            binding.similarMoviesList.show()
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {
        }

        override fun onTabReselected(tab: TabLayout.Tab?) {
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.youtubePlayerView.removeYouTubePlayerListener(youTubePlayerListener)
        binding.tabLayout.removeOnTabSelectedListener(tabSelectedListener)
    }
}