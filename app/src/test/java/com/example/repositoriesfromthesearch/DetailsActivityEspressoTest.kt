package com.example.repositoriesfromthesearch

import android.content.Context
import android.os.Build
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import view.details.DetailsActivity

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class DetailsActivityEspressoTest {
    lateinit var scenario: ActivityScenario<DetailsActivity>
    private lateinit var context: Context

    @Before
    fun setup() {
        scenario = ActivityScenario.launch(DetailsActivity::class.java)
        context = ApplicationProvider.getApplicationContext()
    }

    // активити действительно создается
    @Test
    fun activity_AssertNotNull() {
        scenario.onActivity {
            TestCase.assertNotNull(it)
        }
    }

    // активити в нужном нам состоянии
    @Test
    fun activity_IsResumed() {
        TestCase.assertEquals(Lifecycle.State.RESUMED, scenario.state)
    }

    // проверим что TextView существует
    @Test
    fun activityTextView_NotNull() {
        scenario.onActivity {
            val totalCountTextView = it.binding.totalCountTextView
            TestCase.assertNotNull(totalCountTextView)
        }
    }

    // текстовое поле отображает ожидаемую информацию и видно на экране
    @Test
    fun activityTextView_HasText() {
        val assertion: ViewAssertion = matches(withText("Number of results: 0"))
        onView(withId(R.id.totalCountTextView)).check(assertion)
    }

    @Test
    fun activityTextView_IsVisible() {
        onView(withId(R.id.totalCountTextView)).check(matches(isDisplayed()))
    }

    // проверим что ButtonDecrement существует
    @Test
    fun activityButtonDecrement_NotNull() {
        scenario.onActivity {
            val decrementButton = it.binding.decrementButton
            TestCase.assertNotNull(decrementButton)
        }
    }

    // проверим что ButtonIncrement существует
    @Test
    fun activityButtonIncrement_NotNull() {
        scenario.onActivity {
            val incrementButton = it.binding.incrementButton
            TestCase.assertNotNull(incrementButton)
        }
    }

    @Test
    fun activityButtons_AreVisible() {
        onView(withId(R.id.decrementButton)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))

        onView(withId(R.id.incrementButton)).check(matches(withEffectiveVisibility(
            Visibility.VISIBLE)))
    }

    // проверим, как нажатие на кнопку (+) изменяет значение в TextView
    @Test
    fun activityButtonIncrement_IsWorking() {
        val assertion: ViewAssertion = matches(withText("Number of results: 1"))
        onView(withId(R.id.incrementButton)).perform(click())
        onView(withId(R.id.totalCountTextView)).check(assertion)
    }

    // проверим, как нажатие на кнопку (-) изменяет значение в TextView
    @Test
    fun activityButtonDecrement_IsWorking() {
        val assertion: ViewAssertion = matches(withText("Number of results: -1"))
        onView(withId(R.id.decrementButton)).perform(click())
        onView(withId(R.id.totalCountTextView)).check(assertion)
    }

    // создаём статический метод getIntent().
    // Убедимся, что он создается корректно и содержит правильные данные
    @Test
    fun activityCreateIntent_NotNull() {
        val intent = DetailsActivity.getIntent(context, 0)
        TestCase.assertNotNull(intent)
    }

    @Test
    fun activityCreateIntent_HasExtras() {
        val intent = DetailsActivity.getIntent(context, 0)
        val bundle = intent.extras
        TestCase.assertNotNull(bundle)
    }

    @Test
    fun activityCreateIntent_HasCount() {
        val count = 42
        val intent = DetailsActivity.getIntent(context, count)
        val bundle = intent.extras
        TestCase.assertEquals(count, bundle?.getInt(DetailsActivity.TOTAL_COUNT_EXTRA, 0))
    }

    @After
    fun close() {
        scenario.close()
    }
}