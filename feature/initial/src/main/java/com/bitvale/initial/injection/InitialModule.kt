package com.bitvale.initial.injection

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.bitvale.initial.presentation.view.fragment.InitialFragment
import com.bitvale.initial.presentation.viewmodel.InitialViewModel
import com.bitvale.injection.ActivityLevel
import com.bitvale.injection.ApplicationLevel
import com.bitvale.injection.FeatureScope
import com.bitvale.injection.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
abstract class InitialModule {
    @Binds
    @IntoMap
    @FeatureScope
    @ViewModelKey(InitialViewModel::class)
    internal abstract fun viewModel(viewModel: InitialViewModel): ViewModel

    companion object {
        @Provides
        @ActivityLevel
        fun context(fragment: InitialFragment): Context =
            fragment.requireContext()

        @Provides
        @ApplicationLevel
        fun applicationContext(@ActivityLevel context: Context): Context =
            context.applicationContext

        @Provides
        fun localBroadcastManager(@ActivityLevel context: Context): LocalBroadcastManager =
            LocalBroadcastManager.getInstance(context)
    }
}
