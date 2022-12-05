package com.bitvale.initial.injection

import com.bitvale.initial.presentation.view.fragment.InitialFragment
import com.bitvale.injection.FeatureScope
import com.bitvale.injection.component.CommonComponent
import com.bitvale.injection.holder.BaseComponentHolder
import dagger.BindsInstance
import dagger.Component

@FeatureScope
@Component(
    modules = [
        InitialModule::class
    ]
)
interface InitialComponent : CommonComponent {

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance
            fragment: InitialFragment
        ): InitialComponent
    }

    fun inject(fragment: InitialFragment)

    companion object : BaseComponentHolder<InitialComponent>() {
        override fun createComponent(creator: () -> InitialComponent): InitialComponent =
            creator.invoke()
    }
}
