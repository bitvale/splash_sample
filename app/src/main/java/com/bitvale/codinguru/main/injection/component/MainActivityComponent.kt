package com.bitvale.codinguru.main.injection.component

import android.content.Context
import com.bitvale.codinguru.app.injection.component.AppComponent
import com.bitvale.codinguru.main.activity.MainActivity
import com.bitvale.codinguru.main.injection.module.MainActivityModule
import com.bitvale.injection.ActivityLevel
import com.bitvale.injection.ActivityScope
import com.bitvale.injection.component.CommonComponent
import dagger.BindsInstance
import dagger.Component

@ActivityScope
@Component(
    modules = [
        MainActivityModule::class
    ],
    dependencies = [AppComponent::class]
)
interface MainActivityComponent : CommonComponent {
    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance
            activity: MainActivity,
            appComponent: AppComponent
        ): MainActivityComponent
    }

    fun inject(instance: MainActivity)

    @ActivityLevel
    fun context(): Context
}
