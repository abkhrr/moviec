package id.moviec.favorite.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.FragmentScoped
import id.moviec.base.BaseFragment
import id.moviec.domain.entities.MovieModel
import id.moviec.domain.subscriber.ResultState
import id.moviec.favorite.adapter.FavoriteAdapter
import id.moviec.favorite.databinding.FragmentFavoriteBinding
import id.moviec.favorite.listener.MovieClickListener
import id.moviec.favorite.viemodel.FavoriteViewModel
import id.moviec.home.ui.HomeFragmentDirections
import id.moviec.utils.ext.hide
import id.moviec.utils.ext.show
import id.moviec.utils.navigation.NavigationCommand

@AndroidEntryPoint
@FragmentScoped
class FavoriteFragment : BaseFragment<FragmentFavoriteBinding, FavoriteViewModel>(), MovieClickListener {

    @FragmentScoped
    override val binding: FragmentFavoriteBinding by lazy { FragmentFavoriteBinding.inflate(layoutInflater) }
    override val viewModel: FavoriteViewModel by viewModels()
    private val adapter: FavoriteAdapter by lazy { FavoriteAdapter(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewRvCollectionFavorite.adapter = adapter
    }

    override fun initAPI() {
        super.initAPI()
        viewModel.fetchFavoriteMovies()
    }

    override fun setupObserver() {
        super.setupObserver()
        viewModel.favoriteMovies.observe(viewLifecycleOwner, {
            setDataResult(it)
        })
    }

    private fun setDataResult(it: ResultState<List<MovieModel>?>){
        when(it){
            is ResultState.Success -> {
                it.data?.let { movies ->
                    if(movies.isEmpty()){
                        binding.viewRvCollectionFavorite.hide()
                        binding.emptyText.show()
                    } else {
                        binding.viewRvCollectionFavorite.show()
                        binding.emptyText.hide()
                        adapter.data = movies
                    }
                }
            }
            is ResultState.Error -> {
                Toast.makeText(context, it.throwable.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onMovieClick(movie: MovieModel) {
        navigate(NavigationCommand.To(FavoriteFragmentDirections.showBottomSheet(movie)))
    }
}