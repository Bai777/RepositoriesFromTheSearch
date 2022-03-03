package com.example.repositoriesfromthesearch

import android.content.Context
import android.os.Build
import android.widget.TextView
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
import view.search.MainActivity

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class MainActivityTest {
    lateinit var scenario: ActivityScenario<MainActivity>
    private lateinit var context: Context
    @Before
    fun setup() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun activity_AssertNotNull(){
        scenario.onActivity {
           assertNotNull(it)
        }
    }

    @Test
    fun activity_IsResumed(){
        assertEquals(Lifecycle.State.RESUMED, scenario.state)
    }

    @Test
    fun activityEditText_NotNull(){
        scenario.onActivity {
            val searchEditText = it.binding.searchEditText
            assertNotNull(searchEditText)
        }
    }

    @Test
    fun activityEditText_HasText(){
        scenario.onActivity {
            val searchEditText = it.binding.searchEditText
            searchEditText.setText("Hi", TextView.BufferType.EDITABLE)
            assertNotNull(searchEditText.text)
            assertEquals("Hi", searchEditText.text.toString())
        }
    }


    @After
    fun close() {
        scenario.close()
    }
}