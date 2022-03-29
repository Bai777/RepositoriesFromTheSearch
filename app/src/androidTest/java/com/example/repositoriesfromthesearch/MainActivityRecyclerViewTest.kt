package com.example.repositoriesfromthesearch

import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import view.search.MainActivity

@RunWith(AndroidJUnit4::class)
class MainActivityRecyclerViewTest {
    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setup(){
        scenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @After
    fun close(){
        scenario.close()
    }
}