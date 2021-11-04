package id.moviec.home.ui

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.FragmentScoped
import id.moviec.base.BaseFragment
import id.moviec.domain.entities.MovieModel
import id.moviec.home.databinding.FragmentHomeBinding
import id.moviec.home.viemodel.HomeViewModel
import id.moviec.home.view.HeaderView
import id.moviec.home.view.HomeGridView
import id.moviec.home.view.SectionView
import id.moviec.utils.navigation.NavigationCommand


@AndroidEntryPoint
@FragmentScoped
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(),
    SectionView.OnSectionMovieClicked, HeaderView.OnHeaderMovieClicked, SectionView.OnDoneLoading, HeaderView.OnDoneLoading, HomeGridView.OnGridMovieClicked{

    @FragmentScoped
    override val binding: FragmentHomeBinding by lazy { FragmentHomeBinding.inflate(layoutInflater) }
    override val viewModel: HomeViewModel by viewModels()

    override fun initViewCreated() {
        super.initViewCreated()
        setupListener()
        fetchData()
    }

    override fun setupListener() {
        super.setupListener()
        binding.headerView.homeViewModel             = viewModel
        binding.topRatedView.homeViewModel           = viewModel
        binding.popularView.homeViewModel            = viewModel
        binding.gridViewDiscover.homeViewModel       = viewModel

        binding.headerView.onHeaderClicked           = this
        binding.topRatedView.onSectionClickListener  = this
        binding.popularView.onSectionClickListener   = this
        binding.gridViewDiscover.onGridClickListener = this
    }

    private fun fetchData() = with(binding) {
        headerView.fetchData(viewLifecycleOwner)
        topRatedView.fetchData(viewLifecycleOwner)
        popularView.fetchData(viewLifecycleOwner)
        gridViewDiscover.fetchData(viewLifecycleOwner)
    }

    override fun sectionClicked(data: MovieModel) {
        navigate(NavigationCommand.To(HomeFragmentDirections.showBottomSheet(data)))
    }

    override fun headerClicked(data: MovieModel) {
        navigate(NavigationCommand.To(HomeFragmentDirections.showBottomSheet(data)))
    }

    override fun gridClicked(data: MovieModel) {
        navigate(NavigationCommand.To(HomeFragmentDirections.showBottomSheet(data)))
    }

    override fun doneLoading() {}

}