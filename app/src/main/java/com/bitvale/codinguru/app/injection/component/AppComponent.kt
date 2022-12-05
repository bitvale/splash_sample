package com.bitvale.codinguru.app.injection.component

import android.content.Context
import com.bitvale.codinguru.app.CodinguruApplication
import com.bitvale.injection.ApplicationLevel
import com.bitvale.injection.ApplicationScope
import com.bitvale.injection.component.CommonComponent
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component
interface AppComponent : CommonComponent {
    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance
            @ApplicationLevel
            context: Context
        ): AppComponent
    }

    fun inject(app: CodinguruApplication)

    @ApplicationLevel
    fun context(): Context
}
