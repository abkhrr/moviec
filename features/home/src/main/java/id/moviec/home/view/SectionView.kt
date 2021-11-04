package id.moviec.home.view

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import id.moviec.domain.entities.MovieModel
import id.moviec.domain.subscriber.ResultState
import id.moviec.home.R
import id.moviec.home.adapter.MovieAdapter
import id.moviec.home.databinding.ViewSectionHomeBinding
import id.moviec.home.listener.MovieClickListener
import id.moviec.home.viemodel.HomeViewModel
import id.moviec.utils.constant.Const


class SectionView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), MovieClickListener {
    private var type: Int = 0
    private val adapter: MovieAdapter by lazy { MovieAdapter(this) }

    var doneLoadingListener: OnDoneLoading?            = null
    var onSectionClickListener: OnSectionMovieClicked? = null
    private var binding: ViewSectionHomeBinding

    var homeViewModel: HomeViewModel? = null
    lateinit var lifecycle: LifecycleOwner

    init {
        val array: TypedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.SectionView
        )

        val type  = array.getInt(R.styleable.SectionView_svModule, Const.POPULAR)
        val label = array.getString(R.styleable.SectionView_svLabel)
        array.recycle()
        binding   = ViewSectionHomeBinding.inflate(LayoutInflater.from(context), this, true)

        binding.tvLabelSection.text = label
        this.type                   = type
        Log.d(TAG, "Type: $type")

        binding.errorView.viewButtonRetry.setOnClickListener {
            fetchData(lifecycle)
        }

        setAdapter()
    }

    fun fetchData(lifecycleOwner: LifecycleOwner) {
        lifecycle = lifecycleOwner
        showLoading()
        hideError()
        binding.rvMovieList.visibility = View.VISIBLE

        when (type) {
            Const.POPULAR -> {
                Log.d(TAG, "fetched Popular")
                homeViewModel?.fetchPopularMovie()
                homeViewModel?.popularMovies?.observe(lifecycle, Observer {
                    setView(it)
                })
            }
            Const.TOP_RATED -> {
                Log.d(TAG, "fetched Top Rated")
                homeViewModel?.fetchTopRatedMovie()
                homeViewModel?.topRatedMovies?.observe(lifecycle, Observer {
                    setView(it)
                })
            }
            Const.NOW_PLAYING -> {
                Log.d(TAG, "fetched Now Playing")
                homeViewModel?.fetchNowPlayingMovie()
                homeViewModel?.nowPlayingMovies?.observe(lifecycle, Observer {
                    setView(it)
                })
            }
        }
    }

    private fun setView(resultState: ResultState<List<MovieModel>?>?) {
        when (resultState) {
            is ResultState.Success -> {
                hideLoading()
                setData(resultState.data)
            }
            is ResultState.Error -> {
                hideLoading()
                showError(resultState.throwable)
            }
            is ResultState.Loading -> {
                setData(resultState.data)
            }
        }
    }


    private fun setData(data: List<MovieModel>?) {
        if (data.isNullOrEmpty()) {
            visibility = GONE
            return
        }
        adapter.data = data
    }


    private fun setAdapter() = with(binding) {
        rvMovieList.adapter             = adapter
        val helper: SnapHelper          = PagerSnapHelper()
        val linearLayoutManager         = LinearLayoutManager(context)
        helper.attachToRecyclerView(rvMovieList)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        rvMovieList.layoutManager       = linearLayoutManager
    }

    private fun showError(throwable: Throwable) = with(binding) {
        rvMovieList.visibility                      = View.GONE
        errorView.root.visibility                   = View.VISIBLE
        binding.errorView.textViewErrorMessage.text = throwable.localizedMessage
    }

    private fun hideError() = with(binding) {
        errorView.root.visibility = View.GONE
    }

    private fun showLoading() = with(binding) {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() = with(binding) {
        doneLoadingListener?.doneLoading()
        progressBar.visibility = View.GONE
    }

    interface OnDoneLoading {
        fun doneLoading()
    }

    interface OnSectionMovieClicked {
        fun sectionClicked(data: MovieModel)
    }

    companion object {
        private const val TAG = "SectionView"
    }

    override fun onMovieClick(movie: MovieModel) {
        onSectionClickListener?.sectionClicked(movie)
    }
}