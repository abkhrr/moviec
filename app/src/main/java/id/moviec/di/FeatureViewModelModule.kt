package id.moviec.di

import androidx.lifecycle.ViewModel
import com.ipro.utils.base.viewmodel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.multibindings.IntoMap
import id.moviec.detail.viemodel.MovieDetailViewModel
import id.moviec.dialog.bottom.sheet.MovieBottomSheetViewModel
import id.moviec.favorite.viemodel.FavoriteViewModel
import id.moviec.home.viemodel.HomeViewModel

@Module
@InstallIn(FragmentComponent::class)
abstract class FeatureViewModelModule {
    @Binds
    @IntoMap
    @ViewModelScoped
    @ViewModelKey(FavoriteViewModel::class)
    abstract fun provideFavoriteViewModel(viewModel: FavoriteViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelScoped
    @ViewModelKey(MovieDetailViewModel::class)
    abstract fun provideMovieDetailViewModel(viewModel: MovieDetailViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelScoped
    @ViewModelKey(HomeViewModel::class)
    abstract fun provideHomeViewModel(viewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelScoped
    @ViewModelKey(HomeViewModel::class)
    abstract fun provideMovieBottomSheetViewModel(viewModel: MovieBottomSheetViewModel): ViewModel
}