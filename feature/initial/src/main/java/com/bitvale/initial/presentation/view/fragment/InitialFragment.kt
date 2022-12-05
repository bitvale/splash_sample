package com.bitvale.initial.presentation.view.fragment

import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.bitvale.core.presentation.view.fragment.BaseFragment
import com.bitvale.core.presentation.view.viewbinding.viewBinding
import com.bitvale.initial.R
import com.bitvale.initial.databinding.InitialFragmentBinding
import com.bitvale.initial.injection.DaggerInitialComponent
import com.bitvale.initial.presentation.viewmodel.InitialViewModel
import com.bitvale.injection.component.FeatureComponentManager
import javax.inject.Inject

class InitialFragment : BaseFragment<InitialViewModel>(R.layout.initial_fragment) {

    private val binding: InitialFragmentBinding? by viewBinding()

    @Inject
    lateinit var broadcastManager: LocalBroadcastManager

    override fun inject(componentManager: FeatureComponentManager) {
        DaggerInitialComponent
            .factory()
            .create(this)
            .apply {
                inject(this@InitialFragment)
            }
    }

    override fun release() {
        // no need to release
    }
}
