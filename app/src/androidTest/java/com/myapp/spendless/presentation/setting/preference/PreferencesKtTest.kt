package com.myapp.spendless.presentation.setting.preference

import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.myapp.spendless.feature.Setting.preference.ui.SpendLessPreferenceScreen
import org.junit.Rule
import org.junit.Test

class PreferencesKtTest{

    @get: Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testCurrencySelectionUI(){
        //Launch

        composeTestRule.setContent {
            SpendLessPreferenceScreen(onSave = {}) {  }
        }
        //check if currency is displayed
        composeTestRule.onNodeWithText("Currency").isDisplayed()

        //click on currency dropdown
        composeTestRule.onNodeWithText("$ US Dollar(USD)").performClick()

        composeTestRule.onNodeWithText("$ US Dollar(USD)").assertExists()

    }
}