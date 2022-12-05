package com.bitvale.injection.holder

import com.bitvale.injection.component.CommonComponent

abstract class BaseComponentHolder<Component : CommonComponent> : ComponentHolder<Component> {

    @Volatile
    private var pair: Pair<Component, Int>? = null

    protected abstract fun createComponent(creator: () -> Component): Component

    @Synchronized
    override fun provideComponent(creator: () -> Component): Component {
        val localPair = pair
        val component: Component
        if (localPair == null) {
            component = createComponent(creator)
            pair = Pair(component, 1)
        } else {
            component = localPair.first
            pair = Pair(component, localPair.second + 1)
        }
        return component
    }

    @Synchronized
    override fun releaseComponent() {
        val localPair = pair
        localPair?.let {
            if (localPair.second == 1) {
                destroyComponent()
            } else {
                pair = Pair(localPair.first, localPair.second - 1)
            }
        }
    }

    @Synchronized
    private fun destroyComponent() {
        pair = null
    }
}
