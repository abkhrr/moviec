package id.moviec.ui.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import id.moviec.R
import id.moviec.base.BaseActivity
import id.moviec.databinding.ActivityMainBinding
import id.moviec.viewmodel.MainViewModel
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    companion object {
        @JvmStatic
        fun start(context: Activity){
            val mainIntent = Intent(context, MainActivity::class.java)
            context.startActivity(mainIntent)
            context.finish()
        }
    }

    override val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            val navController        = Navigation.findNavController(this@MainActivity, R.id.dashboard_nav_host)
            navView.setupWithNavController(navController)
        }
    }

    override fun onInitViews() {}


}