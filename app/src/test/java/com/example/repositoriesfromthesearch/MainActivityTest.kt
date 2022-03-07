package com.example.repositoriesfromthesearch

import android.content.Context
import android.os.Build
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import view.search.MainActivity

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class MainActivityTest {
    private lateinit var scenario: ActivityScenario<MainActivity>
    private lateinit var context: Context

    @Rule
    @JvmField
    var activityRule = ActivityTestRule<MainActivity>(
        MainActivity::class.java
    )

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