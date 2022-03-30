package com.example.repositoriesfromthesearch.automator

import android.content.Intent
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SdkSuppress
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Until
import com.example.repositoriesfromthesearch.context
import com.example.repositoriesfromthesearch.packageName
import com.example.repositoriesfromthesearch.uiDevice
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SdkSuppress(minSdkVersion = 18)
class BehaviorTestDetailsActivity {

    @Before
    fun setup() {
        uiDevice.pressHome()

        val intent = context.packageManager.getLaunchIntentForPackage(packageName)
        intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        context.startActivity(intent)
        uiDevice.wait(Until.hasObject(By.pkg(packageName).depth(0)),
            BehaviorTestMainActivity.TIMEOUT
        )
    }
    @Test
    fun test_OpenDetailsScreen() {
        val toDetails = uiDevice.findObject(By.res(packageName, "toDetailsActivityButton"))
        toDetails.click()
    }

    @Test
    fun test_IncrementButton() {
        val toDetails = uiDevice.findObject(By.res(packageName, "toDetailsActivityButton"))
        toDetails.click()
        val changedText = uiDevice.wait(
            Until.findObject(By.res(packageName, "totalCountTextViewDetails")),
            BehaviorTestMainActivity.TIMEOUT
        )
        val toIncrement = uiDevice.findObject(By.res(packageName, "incrementButton"))
        toIncrement.click()
        Assert.assertEquals(changedText.text, "Number of results: 1")
    }

    @Test
    fun test_DecrementButton() {
        val toDetails = uiDevice.findObject(By.res(packageName, "toDetailsActivityButton"))
        toDetails.click()
        val changedText = uiDevice.wait(
            Until.findObject(By.res(packageName, "totalCountTextViewDetails")),
            BehaviorTestMainActivity.TIMEOUT
        )
        val toIncrement = uiDevice.findObject(By.res(packageName, "decrementButton"))
        toIncrement.click()
        Assert.assertEquals(changedText.text, "Number of results: -1")
    }
}