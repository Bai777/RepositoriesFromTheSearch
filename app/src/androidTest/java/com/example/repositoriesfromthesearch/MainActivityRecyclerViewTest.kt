package com.example.repositoriesfromthesearch

import android.view.View
import android.widget.EditText
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import view.search.MainActivity
import view.search.SearchResultAdapter

@RunWith(AndroidJUnit4::class)
class MainActivityRecyclerViewTest {
    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setup(){
        scenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun activitySearch_ScrollTo(){
        if (BuildConfig.TYPE == MainActivity.FAKE){
            loadList()
            onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.scrollTo<
                    SearchResultAdapter.SearchResultViewHolder>(
                hasDescendant(withText("FullName: 42"))
                    ))
        }
    }

   @Test
   fun activitySearch_PerformClickPosition(){
       if (BuildConfig.TYPE == MainActivity.FAKE){
           loadList()
           onView(withId(R.id.recyclerView)).perform(
               RecyclerViewActions.actionOnItemAtPosition<SearchResultAdapter.SearchResultViewHolder>(
                   0, click()
               )
           )
       }
   }

    @Test
    fun activitySearch_PerformClickOnItem(){
        if(BuildConfig.TYPE == MainActivity.FAKE){
            loadList()
            onView(withId(R.id.recyclerView)).perform(
                RecyclerViewActions.scrollTo<SearchResultAdapter.SearchResultViewHolder>(
                    hasDescendant(withText("FullName: 50"))
                )
            )
            onView(withId(R.id.recyclerView)).perform(
                RecyclerViewActions.actionOnItem<SearchResultAdapter.SearchResultViewHolder>(
                    hasDescendant(withText("FullName: 42")), click()
                )
            )
        }
    }

    @Test
    fun activitySearch_PerformCustomClick(){
        if (BuildConfig.TYPE == MainActivity.FAKE){
            loadList()
            onView(withId(R.id.recyclerView)).perform(
                RecyclerViewActions.actionOnItemAtPosition<SearchResultAdapter.SearchResultViewHolder>(
                    0, tapOnItemWithId(R.id.checkbox)
                )
            )
        }
    }

    @Test
    fun activityInsertTextFromEditText(){
        if (BuildConfig.TYPE == MainActivity.FAKE){
            loadList()
            onView(withId(R.id.searchEditText)).perform(typeTextInChildViewWithId(0, "algol"))
        }
    }

    private fun typeTextInChildViewWithId(id: Int, textToBeTyped: String): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View>? {
                return null
            }
            override fun getDescription(): String {
                return "Описание действия"
            }
            override fun perform(uiController: UiController, view: View) {
                val v = view.findViewById<View>(id) as EditText
                v.setText(textToBeTyped)
            }
        }
    }


    private fun loadList() {
        onView(withId(R.id.searchEditText)).perform(click())
        onView(withId(R.id.searchEditText)).perform(
            replaceText("algol"),
            closeSoftKeyboard()
        )
        onView(withId(R.id.recyclerView)).perform(pressImeActionButton())
    }

    private fun tapOnItemWithId(id: Int) = object : ViewAction{
        override fun getDescription(): String {
            return "Нажимаем на view с указанным id"
        }

        override fun getConstraints(): Matcher<View>? {
            return null
        }

        override fun perform(uiController: UiController?, view: View?) {
            val clickCheckBox = view?.findViewById(id) as View
            clickCheckBox.performClick()
        }
    }

    @After
    fun close(){
        scenario.close()
    }
}