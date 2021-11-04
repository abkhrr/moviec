package id.moviec.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import id.moviec.base.viewmodel.BaseViewModel
import id.moviec.utils.navigation.NavigationCommand

abstract class BaseFragment<VB : ViewDataBinding, VM : BaseViewModel> : Fragment() {

    abstract val binding: VB
    abstract val viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupOnCreate()
    }

    override fun onStart() {
        super.onStart()
        viewModel.showToast.observe(this, { Toast.makeText(activity, it, Toast.LENGTH_LONG).show() })
        viewModel.showSnack.observe(this, { Snackbar.make(this.requireView(), it, Snackbar.LENGTH_LONG).show() })
    }

    fun navigate(command: NavigationCommand) {
        when (command) {
            is NavigationCommand.To     -> findNavController().navigate(command.directions)
            is NavigationCommand.Back   -> findNavController().popBackStack()
            is NavigationCommand.BackTo -> findNavController().popBackStack(command.destinationId, false)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        performDataBinding()
        initViewCreated()
        setupAdapter()
        setupArguments()
        setupListener()
        setupListener()
        initAPI()
        setupRv()
        setupObserver()
        initOnClick()
    }

    private fun performDataBinding(){
        binding.lifecycleOwner = this
        binding.executePendingBindings()
    }

    fun showError(throwable: Throwable){
        viewModel.showSnack.value = throwable.localizedMessage
    }

    protected open fun setupOnCreate(){}
    protected open fun initViewCreated(){}
    protected open fun setupSwipeRefresh(){}
    protected open fun setupArguments(){}
    protected open fun setupAdapter(){}
    protected open fun setupListener(){}
    protected open fun initAPI(){}
    protected open fun setupRv(){}
    protected open fun setupObserver(){}
    protected open fun initOnClick(){}

}