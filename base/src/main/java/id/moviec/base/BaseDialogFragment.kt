package id.moviec.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import id.moviec.base.viewmodel.BaseViewModel
import id.moviec.utils.navigation.NavigationCommand

abstract class BaseDialogFragment <VM : BaseViewModel>: BottomSheetDialogFragment() {

    abstract val viewModel: VM

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

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
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