package id.moviec.dialog.bottom.sheet

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.FragmentScoped
import id.moviec.base.BaseDialogFragment
import id.moviec.dialog.R
import id.moviec.dialog.databinding.MovieBottomSheetBinding
import id.moviec.domain.entities.DetailMovieModel
import id.moviec.domain.entities.MovieModel
import id.moviec.domain.subscriber.ResultState
import id.moviec.utils.constant.Const
import id.moviec.utils.date.DateFormatter

@AndroidEntryPoint
@FragmentScoped
class MovieBottomSheet: BaseDialogFragment<MovieBottomSheetViewModel>() {

    @FragmentScoped
    override val viewModel: MovieBottomSheetViewModel by viewModels()
    private val movie: MovieModel? by lazy { arguments?.getParcelable("movie") }
    private lateinit var binding: MovieBottomSheetBinding
    private var isFavorite = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MovieBottomSheetBinding.inflate(inflater, container, false)
        setupUI()
        return binding.root
    }

    private fun setupUI(){
        binding.closeIcon.setOnClickListener { dismiss() }
        binding.detailsButton.setOnClickListener {
            findNavController().navigate(MovieBottomSheetDirections.toDetails(movie!!))
        }
        binding.favoriteIcons.setOnClickListener { setFavoriteMovie() }
        getData()
    }

    private fun getData(){
        movie?.id?.let {
            viewModel.fetchFavoriteMovie(it)
            viewModel.fetchDetailMovie(it)
        }
        initObservers()
    }

    private fun initObservers(){
        viewModel.getDetailMovieLiveData.observe(viewLifecycleOwner, {
            setDetail(it)
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
        val url = detail.posterPath
        url?.let {
            val addedUrl =
                if (!url.contains("https://") || !url.contains("http://")) String.format(Const.MOVIE_THUMBNAIL_BASE_URL_LARGE, url)
                else url

            Glide.with(requireContext()).load(addedUrl).transform(CenterCrop()).into(binding.posterImage)
        }

        binding.titleText.text         = detail.title
        binding.yearText.text          = DateFormatter.changeDateFormat(detail.releaseDate)
        binding.runtimeText.visibility = View.GONE
        binding.overviewText.text      = detail.overview
    }

    private fun setFavoriteMovie() {
        movie?.let {
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
        binding.favoriteIcons.setImageDrawable(getFavoriteIcon())
    }

    private fun getFavoriteIcon(): Drawable? {
        return if (isFavorite) ContextCompat.getDrawable(
            requireContext(),
            R.drawable.ic_svg_favorite_24
        ) else ContextCompat.getDrawable(requireContext(), R.drawable.ic_svg_favorite_border_24)
    }

}

