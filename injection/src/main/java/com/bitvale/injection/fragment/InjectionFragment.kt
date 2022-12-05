package com.bitvale.injection.fragment

import android.content.Context
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.bitvale.injection.component.ComponentManagerProvider
import com.bitvale.injection.component.FeatureComponentManager

abstract class InjectionFragment(
    @LayoutRes contentLayoutId: Int
) : Fragment(contentLayoutId) {

    private val componentManager: FeatureComponentManager by lazy(LazyThreadSafetyMode.NONE) {
        (requireActivity() as ComponentManagerProvider)
            .provideComponentManager() as FeatureComponentManager
    }

    override fun onAttach(context: Context) {
        inject(componentManager)
        super.onAttach(context)
    }

    override fun onDestroy() {
        release()
        super.onDestroy()
    }

    abstract fun inject(componentManager: FeatureComponentManager)
    abstract fun release()
}
