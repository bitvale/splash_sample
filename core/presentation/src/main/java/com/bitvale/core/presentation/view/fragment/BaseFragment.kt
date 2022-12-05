package com.bitvale.core.presentation.view.fragment

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.bitvale.core.presentation.view.viewbinding.ViewBindingCallback
import com.bitvale.core.presentation.viewmodel.ViewModelFactory
import com.bitvale.injection.fragment.InjectionFragment
import java.lang.reflect.ParameterizedType
import javax.inject.Inject

abstract class BaseFragment<VM : ViewModel>(
    @LayoutRes contentLayoutId: Int
) : InjectionFragment(contentLayoutId), ViewBindingCallback {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    protected lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = obtainViewModel(getGenericType())
    }

    @Suppress("UNCHECKED_CAST")
    protected open fun getGenericType(): Class<VM> =
        (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<VM>

    private fun <T : ViewModel> obtainViewModel(viewModelClass: Class<T>): T =
        ViewModelProvider(getViewModelStoreOwner(), viewModelFactory)[viewModelClass]

    protected open fun getViewModelStoreOwner(): ViewModelStoreOwner =
        this
}
