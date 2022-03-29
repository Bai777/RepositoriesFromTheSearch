package com.example.repositoriesfromthesearch

import androidx.core.os.bundleOf
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import view.details.DetailsFragment

@RunWith(AndroidJUnit4::class)
class DetailsFragmentEspressoTest {
    private lateinit var scenario: FragmentScenario<DetailsFragment>

    @Before
    fun setup() {
//        launchFragmentInContainer() нужен для запуска Фрагмента с UI;
//        launchFragment — для Фрагментов без UI
        //Запускаем Fragment в корне Activity
        scenario = launchFragmentInContainer()
    }

    @Test
    fun fragment_testBundle() {
        //Можно передавать аргументы во Фрагмент, но это необязательно
        val fragmentArgs = bundleOf("TOTAL_COUNT_EXTRA" to 10)
        //Запускаем Fragment с аргументами
        val scenario = launchFragmentInContainer<DetailsFragment>(fragmentArgs)
        //Возможность менять стейт Фрагмента
        scenario.moveToState(Lifecycle.State.RESUMED)
        val assertion = matches(withText(TEST_NUMBER_OF_RESULTS_TEN))
        onView(withText(R.id.totalCountTextView)).check(assertion)
    }

    @Test
    fun fragment_testSetCountMethod() {
        scenario.onFragment { fragment ->
            fragment.setCount(10)
        }
        val assertion = matches(withText(TEST_NUMBER_OF_RESULTS_TEN))
        onView(withText(R.id.totalCountTextViewDetails)).check(assertion)
    }

    @Test
    fun fragment_testIncrementButton(){
        onView(withId(R.id.incrementButton)).perform(click())
        onView(withId(R.id.totalCountTextViewDetails)).check(matches(withText(TEST_NUMBER_OF_RESULTS_PLUS_1)))
    }

    @Test
    fun fragment_testDecrementButton(){
        onView(withId(R.id.decrementButton)).perform(click())
        onView(withId(R.id.totalCountTextViewDetails)).check(matches(withText(
            TEST_NUMBER_OF_RESULTS_MINUS_1)))
    }
}