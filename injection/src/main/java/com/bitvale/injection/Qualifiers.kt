package com.bitvale.injection

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ApplicationLevel

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ActivityLevel
