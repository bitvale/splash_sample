package com.bitvale.codinguru.main.injection.module

import android.content.Context
import com.bitvale.codinguru.main.activity.MainActivity
import com.bitvale.injection.ActivityLevel
import dagger.Binds
import dagger.Module

@Module
abstract class MainActivityModule {
    @Binds
    @ActivityLevel
    abstract fun context(activity: MainActivity): Context
}
