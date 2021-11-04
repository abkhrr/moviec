package id.moviec.home.view

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import id.moviec.domain.entities.MovieModel
import id.moviec.domain.subscriber.ResultState
import id.moviec.home.R
import id.moviec.home.adapter.MovieAdapter
import id.moviec.home.databinding.ViewHomeGridBinding
import id.moviec.home.listener.MovieClickListener
import id.moviec.home.viemodel.HomeViewModel
import id.moviec.utils.constant.Const
import id.moviec.utils.ext.hide
import id.moviec.utils.ext.show

class HomeGridView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), MovieClickListener {

    private var type: Int      = 0
    private val adapter: MovieAdapter by lazy { MovieAdapter(this) }
    var onGridClickListener: OnGridMovieClicked? = null
    private var binding: ViewHomeGridBinding
    var homeViewModel: HomeViewModel? = null
    lateinit var lifecycle: LifecycleOwner

    init {
        adapter.isGridView = true
        val array: TypedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.HomeGridView
        )

        val type  = array.getInt(R.styleable.HomeGridView_gvModule, Const.GV_DISCOVER)
        val label = array.getString(R.styleable.HeaderView_hvLabel)
        array.recycle()
        binding   = ViewHomeGridBinding.inflate(LayoutInflater.from(context), this, true)

        binding.tvLabelGrid.text = label
        this.type                = type



        binding.errorView.viewButtonRetry.setOnClickListener {
            fetchData(lifecycle)
        }

        setAdapter()
    }

    fun fetchData(lifecycleOwner: LifecycleOwner) {
        lifecycle = lifecycleOwner
        showLoading()
        hideError()
        binding.rvMovieList.show()
        homeViewModel?.fetchDiscoverMovies()
        homeViewModel?.discoverMovies?.observe(lifecycle, {
            setView(it)
        })
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
        helper.attachToRecyclerView(rvMovieList)
    }

    private fun showError(throwable: Throwable) = with(binding) {
        rvMovieList.hide()
        errorView.root.show()
        binding.errorView.textViewErrorMessage.text = throwable.localizedMessage
    }

    private fun hideError() = with(binding) {
        errorView.root.hide()
    }

    private fun showLoading() = with(binding) {
        progressBar.show()
    }

    private fun hideLoading() = with(binding) {
        progressBar.hide()
    }

    interface OnGridMovieClicked {
        fun gridClicked(data: MovieModel)
    }

    override fun onMovieClick(movie: MovieModel) {
        onGridClickListener?.gridClicked(movie)
    }
}