package com.example.repositoriesfromthesearch

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice


//DetailsActivityEspressoTest variables
internal const val TEST_NUMBER = 42
internal const val TEST_NUMBER_OF_RESULTS_ZERO = "Number of results: 0"
internal const val TEST_NUMBER_OF_RESULTS_TEN = "Number of results: 10"
internal const val TEST_NUMBER_OF_RESULTS_PLUS_1 = "Number of results: 1"
internal const val TEST_NUMBER_OF_RESULTS_MINUS_1 = "Number of results: -1"

//BehaviorTestMainActivity variables
internal val uiDevice: UiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
internal  val context = ApplicationProvider.getApplicationContext<Context>()
internal  val packageName = context.packageName


