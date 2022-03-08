package com.example.repositoriesfromthesearch

import android.content.Context
import android.os.Build
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
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
            assertNotNull(it)
        }
    }

    // активити в нужном нам состоянии
    @Test
    fun activity_IsResumed() {
        assertEquals(Lifecycle.State.RESUMED, scenario.state)
    }

    // проверим что TextView существует
    @Test
    fun activityTextView_NotNull() {
        scenario.onActivity {
            val totalCountTextView = it.binding.totalCountTextView
            assertNotNull(totalCountTextView)
        }
    }

    // текстовое поле отображает ожидаемую информацию и видно на экране
    @Test
    fun activityTextView_HasText() {
        scenario.onActivity {
            val totalCountTextView = it.binding.totalCountTextView
            assertEquals("Number of results: 0", totalCountTextView.text)
        }
    }

    @Test
    fun activityTextView_IsVisible() {
        scenario.onActivity {
            val totalCountTextView = it.binding.totalCountTextView
            assertEquals(View.VISIBLE, totalCountTextView.visibility)
        }
    }

    // проверим что ButtonDecrement существует
    @Test
    fun activityButtonDecrement_NotNull() {
        scenario.onActivity {
            val decrementButton = it.binding.decrementButton
            assertNotNull(decrementButton)
        }
    }

    // проверим что ButtonIncrement существует
    @Test
    fun activityButtonIncrement_NotNull() {
        scenario.onActivity {
            val incrementButton = it.binding.incrementButton
            assertNotNull(incrementButton)
        }
    }

    @Test
    fun activityButtons_AreVisible() {
        scenario.onActivity {
            val decrementButton = it.binding.decrementButton
            assertEquals(View.VISIBLE, decrementButton.visibility)

            val incrementButton = it.binding.incrementButton
            assertEquals(View.VISIBLE, incrementButton.visibility)
        }
    }

    // проверим, как нажатие на кнопку (+) изменяет значение в TextView
    @Test
    fun activityButtonIncrement_IsWorking() {
        scenario.onActivity {
            val incrementButton = it.binding.incrementButton
            val totalCountTextView = it.binding.totalCountTextView
            incrementButton.performClick()
            assertEquals("Number of results: 1", totalCountTextView.text)
        }
    }

    // проверим, как нажатие на кнопку (-) изменяет значение в TextView
    @Test
    fun activityButtonDecrement_IsWorking() {
        scenario.onActivity {
            val decrementButton = it.binding.decrementButton
            val totalCountTextView = it.binding.totalCountTextView
            decrementButton.performClick()
            assertEquals("Number of results: -1", totalCountTextView.text)
        }
    }

    // создаём статический метод getIntent().
    // Убедимся, что он создается корректно и содержит правильные данные
    @Test
    fun activityCreateIntent_NotNull() {
        val context: Context = ApplicationProvider.getApplicationContext()
        val intent = DetailsActivity.getIntent(context, 0)
        assertNotNull(intent)
    }

    @Test
    fun activityCreateIntent_HasExtras() {
        val intent = DetailsActivity.getIntent(context, 0)
        val bundle = intent.extras
        assertNotNull(bundle)
    }

    @Test
    fun activityCreateIntent_HasCount() {
        val count = 42
        val intent = DetailsActivity.getIntent(context, count)
        val bundle = intent.extras
        assertEquals(count, bundle?.getInt(DetailsActivity.TOTAL_COUNT_EXTRA, 0))
    }

    @After
    fun close() {
        scenario.close()
    }
}