package com.bitvale.injection.holder

import com.bitvale.injection.component.CommonComponent

interface ComponentHolder<Component : CommonComponent> {
    fun provideComponent(creator: () -> Component): Component
    fun releaseComponent()
}
