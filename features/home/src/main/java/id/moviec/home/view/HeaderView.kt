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
import id.moviec.home.databinding.ViewHomeHeaderBinding
import id.moviec.home.listener.MovieClickListener
import id.moviec.home.viemodel.HomeViewModel
import id.moviec.utils.constant.Const

class HeaderView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), MovieClickListener {

    private var type: Int = 0

    private var binding: ViewHomeHeaderBinding

    var homeViewModel: HomeViewModel? = null
    lateinit var lifecycle: LifecycleOwner
    private val adapter: MovieAdapter by lazy { MovieAdapter(this) }

    var doneLoadingListener: OnDoneLoading?     = null
    var onHeaderClicked: OnHeaderMovieClicked?  = null

    init {
        adapter.isHeader = true
        val array: TypedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.HeaderView
        )

        val type  = array.getInt(R.styleable.HeaderView_hvModule, Const.POPULAR)
        val label = array.getString(R.styleable.HeaderView_hvLabel)
        array.recycle()
        binding   = ViewHomeHeaderBinding.inflate(LayoutInflater.from(context), this, true)

        this.type = type

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
        binding.viewHeaderMovieList.visibility = View.VISIBLE
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
        hideLoading()
        when (resultState) {
            is ResultState.Success -> {
                Log.d(TAG, "data ${resultState.data}")
                setData(resultState.data)
            }
            is ResultState.Error -> {
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
        binding.viewHeaderMovieList.visibility = View.VISIBLE
    }

    private fun setAdapter() = with(binding) {
        viewHeaderMovieList.adapter         = adapter
        val helper: SnapHelper              = PagerSnapHelper()
        val linearLayoutManager             = LinearLayoutManager(context)
        helper.attachToRecyclerView(viewHeaderMovieList)

        linearLayoutManager.orientation     = LinearLayoutManager.HORIZONTAL
        viewHeaderMovieList.layoutManager   = linearLayoutManager
    }

    private fun showError(throwable: Throwable) = with(binding) {
        viewHeaderMovieList.visibility      = View.GONE
        errorView.root.visibility           = View.VISIBLE
        errorView.textViewErrorMessage.text = throwable.localizedMessage
        Log.d("MainFragmentSV", "showError + ${throwable.localizedMessage}")
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

    interface OnHeaderMovieClicked {
        fun headerClicked(data: MovieModel)
    }

    companion object {
        private const val TAG = "HeaderView"
    }

    override fun onMovieClick(movie: MovieModel) {
        onHeaderClicked?.headerClicked(movie)
    }
}