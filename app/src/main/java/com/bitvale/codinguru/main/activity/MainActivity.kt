package com.bitvale.codinguru.main.activity

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.annotation.NavigationRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.bitvale.codinguru.R
import com.bitvale.codinguru.app.CodinguruApplication
import com.bitvale.codinguru.main.injection.component.DaggerMainActivityComponent
import com.bitvale.codinguru.main.injection.component.FeatureComponentManagerImpl
import com.bitvale.codinguru.main.injection.component.MainActivityComponent
import com.bitvale.injection.component.ComponentManager
import com.bitvale.injection.component.ComponentManagerProvider
import com.bitvale.injection.component.FeatureComponentManager

class MainActivity : AppCompatActivity(), ComponentManagerProvider {

    private lateinit var component: MainActivityComponent
    private lateinit var componentManager: FeatureComponentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        inject()
        // For debug only!
        // Set delay to see that light status bar not working
        Thread.sleep(2000)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav_host)

        val startDestination = R.id.initialization_fragment
        val navGraph = R.navigation.initialization_nav_graph
        initNavGraph(startDestination, navGraph)
    }

    private fun getNavController(): NavController {
        val navHost = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        return navHost.navController
    }

    private fun initNavGraph(@IdRes startDestinationId: Int, @NavigationRes navGraphId: Int) {
        val navController = getNavController()
        val navInflater = navController.navInflater
        val mainGraph = navInflater.inflate(R.navigation.main_nav_graph)

        val featureNavGraph = navInflater.inflate(navGraphId)
        featureNavGraph.setStartDestination(startDestinationId)

        mainGraph.remove(featureNavGraph)
        mainGraph.addDestination(featureNavGraph)
        mainGraph.setStartDestination(featureNavGraph.id)
        navController.graph = mainGraph
    }

    private fun inject() {
        component = DaggerMainActivityComponent
            .factory()
            .create(this, CodinguruApplication.appComponent())
            .apply {
                inject(this@MainActivity)
            }

        componentManager = FeatureComponentManagerImpl(component)
    }

    override fun provideComponentManager(): ComponentManager =
        componentManager
}
