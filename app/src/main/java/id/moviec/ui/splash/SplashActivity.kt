package id.moviec.ui.splash

import android.annotation.SuppressLint
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import id.moviec.base.BaseActivity
import id.moviec.databinding.ActivitySplashBinding
import id.moviec.ui.main.MainActivity
import id.moviec.utils.ext.launchDelayedFunction
import id.moviec.viewmodel.SplashViewModel

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>() {

    override val binding: ActivitySplashBinding by lazy { ActivitySplashBinding.inflate(layoutInflater) }
    override val viewModel: SplashViewModel by viewModels()

    override fun onInitViews() {
        launchDelayedFunction {
            sendToMain()
        }
    }

    private fun sendToMain(){
        MainActivity.start(this)
    }
}