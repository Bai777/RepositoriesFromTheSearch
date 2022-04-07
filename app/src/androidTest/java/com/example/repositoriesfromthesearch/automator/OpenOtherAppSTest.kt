package com.example.repositoriesfromthesearch.automator

import android.widget.TextView
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SdkSuppress
import androidx.test.uiautomator.UiScrollable
import androidx.test.uiautomator.UiSelector
import com.example.repositoriesfromthesearch.uiDevice
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SdkSuppress(minSdkVersion = 18)
class OpenOtherAppSTest {

    @Test
    fun test_OpenSettings(){
        uiDevice.pressHome()
        uiDevice.swipe(500, 1500, 500, 0, 5)
        val appViews = UiScrollable(UiSelector().scrollable(false))
        // горизонтальная прокрутка (по умолчанию она вертикальная)
//        appViews.setAsHorizontalList()
        val settingsApp = appViews.getChildByText(
                UiSelector()
                    .className(TextView::class.java.name), "Settings"
            )
        settingsApp.clickAndWaitForNewWindow()

        val settingsValidation = uiDevice.findObject(UiSelector().packageName("com.android.settings"))
        assertTrue(settingsValidation.exists())
    }
}