package hits.tsu

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTouchInput
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.ext.junit.runners.AndroidJUnit4
import hits.tsu.presentation.Nav
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [33], manifest = Config.NONE)
class LibraryTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `displayed labels`() {
        composeTestRule.setContent {
            Nav()
        }
        composeTestRule.onNodeWithTag("open_for_you").assertIsDisplayed()
        composeTestRule.onNodeWithTag("Электронная почта").performTextInput("nikita")
        composeTestRule.onNodeWithTag("Пароль").performTextInput("zex312ZEX")
        composeTestRule.onNodeWithTag("signInButton").performClick()
        composeTestRule.onNodeWithTag("libraryScreen").assertIsDisplayed()

        composeTestRule.onNodeWithTag("библиотека").assertIsDisplayed()
        composeTestRule.onNodeWithTag("Новинки").assertIsDisplayed()
        composeTestRule.onNodeWithTag("Популярные книги").assertIsDisplayed()

    }

    @Test
    fun `displayed lists`() {
        composeTestRule.setContent {
            Nav()
        }
        composeTestRule.onNodeWithTag("open_for_you").assertIsDisplayed()
        composeTestRule.onNodeWithTag("Электронная почта").performTextInput("nikita")
        composeTestRule.onNodeWithTag("Пароль").performTextInput("zex312ZEX")
        composeTestRule.onNodeWithTag("signInButton").performClick()
        composeTestRule.onNodeWithTag("libraryScreen").assertIsDisplayed()
        composeTestRule.onNodeWithTag("horizontal pager").performTouchInput { swipeLeft() }

        composeTestRule.onAllNodesWithTag("carousel model")[0].assertIsDisplayed()
        /*composeTestRule.onAllNodesWithTag("description")[0].assertIsDisplayed()
        composeTestRule.onAllNodesWithTag("name")[0].assertIsDisplayed()*/

    }
}