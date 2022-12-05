package com.bitvale.codinguru.app

import android.app.Application
import com.bitvale.codinguru.BuildConfig
import com.bitvale.codinguru.app.injection.component.AppComponent
import com.bitvale.codinguru.app.injection.component.AppComponentManagerImpl
import com.bitvale.codinguru.app.injection.component.DaggerAppComponent
import timber.log.Timber

class CodinguruApplication : Application() {

    private lateinit var appComponent: AppComponent
    private lateinit var componentManager: AppComponentManagerImpl

    override fun onCreate() {
        super.onCreate()

        application = this
        appComponent = DaggerAppComponent
            .factory()
            .create(this.applicationContext).also {
                it.inject(this)
            }

        componentManager = AppComponentManagerImpl(appComponent)

        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }

    companion object {

        private lateinit var application: CodinguruApplication

        fun appComponent(): AppComponent =
            application.appComponent
    }
}
