package hits.tsu

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import hits.tsu.presentation.Nav
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [33], manifest = Config.NONE)
class SignInTests {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `when I test,then it works`() {
        composeTestRule.setContent {
            Nav()
        }
        composeTestRule.onNodeWithTag("open_for_you").assertIsDisplayed()
        composeTestRule.onNodeWithTag("Электронная почта").performTextInput("nikita")
        composeTestRule.onNodeWithTag("Пароль").performTextInput("zex312ZEX")
        composeTestRule.onNodeWithTag("signInButton").performClick()
        composeTestRule.onNodeWithTag("libraryScreen").assertIsDisplayed()
    }

/*    @Test
    fun `second boring test`() {
        composeTestRule.setContent {
            Nav()
        }

        composeTestRule.onNodeWithTag("open_for_you").assertIsDisplayed()
        composeTestRule.onNodeWithTag("Электронная почта icon").assertDoesNotExist()
        composeTestRule.onNodeWithTag("Пароль icon").assertDoesNotExist()
        composeTestRule.onNodeWithTag("Электронная почта").performTextInput("nikita")
        composeTestRule.onNodeWithTag("Пароль").performTextInput("zex312ZEX")
        composeTestRule.onNodeWithTag("Электронная почта icon").assertIsDisplayed()
        composeTestRule.onNodeWithTag("Пароль icon").assertIsDisplayed()
        composeTestRule.onNodeWithTag("Электронная почта icon").performClick()
        composeTestRule.onNodeWithTag("Электронная почта icon").assertDoesNotExist()
        composeTestRule.onNodeWithTag("signInButton").assertDoesNotExist()
    }*/
}