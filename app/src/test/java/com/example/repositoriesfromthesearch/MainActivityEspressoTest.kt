package com.example.repositoriesfromthesearch

import android.content.Context
import android.os.Build
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import view.search.MainActivity

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class MainActivityEspressoTest {
    private lateinit var scenario: ActivityScenario<MainActivity>
    private lateinit var context: Context


    @Before
    fun setup() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun activity_AssertNotNull() {
        scenario.onActivity {
            assertNotNull(it)
        }
    }

    @Test
    fun activity_IsResumed() {
        assertEquals(Lifecycle.State.RESUMED, scenario.state)
    }

    @Test
    fun activitySearch_IsWorking(){
        onView(withId(R.id.searchEditText)).perform(click())
        onView(withId(R.id.searchEditText)).perform(replaceText("algol"), closeSoftKeyboard())
        onView(withId(R.id.searchEditText)).perform(pressImeActionButton())
        if(BuildConfig.BUILD_TYPE == MainActivity.FAKE) {
            onView(withId(R.id.totalCountTextView)).check(matches(withText("Number of results: 42")))
        }else {
            onView(isRoot()).perform(delay())
            onView(withId(R.id.totalCountTextView)).check(matches(withText("Number of results: 2914")))
        }
    }

    private fun delay(): ViewAction? {
        return object: ViewAction{
            override fun getConstraints(): Matcher<View> = isRoot()

            override fun getDescription(): String= "wait for $2 seconds"

            override fun perform(uiController: UiController?, view: View?) {
                uiController?.loopMainThreadForAtLeast(2000)
            }
        }
    }

    @Test
    fun activityEditText_NotNull() {
        scenario.onActivity {
            val searchEditText = it.binding.searchEditText
            assertNotNull(searchEditText)
        }
    }

    @Test
    fun activityEditText_IsVisible() {
        scenario.onActivity {
            val searchEditText = it.binding.searchEditText
            assertEquals(View.VISIBLE, searchEditText.visibility)
        }
    }

    @Test
    fun activityEditText_HasText() {
        scenario.onActivity {
            val searchEditText = it.binding.searchEditText
            searchEditText.setText("Hi", TextView.BufferType.EDITABLE)
            assertNotNull(searchEditText.text)
            assertEquals("Hi", searchEditText.text.toString())
        }
    }

    @Test
    @Throws(Exception::class)
    fun activityToDetailsActivityButton() {
        onView(withId(R.id.toDetailsActivityButton))
    }

    @Test
    @Throws(Exception::class)
    fun activityToDetailsActivityButton_AreVisible() {
        onView(withId(R.id.toDetailsActivityButton)).check(matches(isDisplayed()))
    }

    @Test
    fun clickActivityToDetailsActivityButton() {
        onView(withId(R.id.toDetailsActivityButton)).perform(click())
    }

    @Test
    fun clickActivityToDetailsActivityButton_openDetailsActivity(){
        onView(withId(R.id.toDetailsActivityButton)).check(matches(isDisplayed()))
    }

    @After
    fun close() {
        scenario.close()
    }
}