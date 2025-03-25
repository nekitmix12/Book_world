package hits.tsu

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import hits.tsu.presentation.Nav
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [33], manifest = Config.NONE)
class BookDetailsTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `displayed top`() {
        composeTestRule.setContent {
            Nav()
        }
        composeTestRule.onNodeWithTag("open_for_you").assertIsDisplayed()
        composeTestRule.onNodeWithTag("Электронная почта").performTextInput("nikita")
        composeTestRule.onNodeWithTag("Пароль").performTextInput("zex312ZEX")
        composeTestRule.onNodeWithTag("signInButton").performClick()
        composeTestRule.onNodeWithTag("libraryScreen").assertIsDisplayed()
        composeTestRule.onAllNodesWithTag("carousel model")[0].performClick()

        composeTestRule.onNodeWithTag("detail image").assertIsDisplayed()
        composeTestRule.onNodeWithTag("back from details").assertIsDisplayed()
        composeTestRule.onNodeWithTag("read button").assertIsDisplayed()
        composeTestRule.onNodeWithTag("toFavorite button").assertIsDisplayed()
    }



    @Test
    fun `displayed bottom`() {
        composeTestRule.setContent {
            Nav()
        }
        composeTestRule.onNodeWithTag("open_for_you").assertIsDisplayed()
        composeTestRule.onNodeWithTag("Электронная почта").performTextInput("nikita")
        composeTestRule.onNodeWithTag("Пароль").performTextInput("zex312ZEX")
        composeTestRule.onNodeWithTag("signInButton").performClick()
        composeTestRule.onNodeWithTag("libraryScreen").assertIsDisplayed()
        composeTestRule.onAllNodesWithTag("carousel model")[0].performClick()


        composeTestRule.onNodeWithTag("details name").assertIsDisplayed()
        composeTestRule.onAllNodesWithTag("CommonDetailsText")[0].assertIsDisplayed()
        composeTestRule.onNodeWithTag("Book details").performScrollToIndex(2)
        composeTestRule.onAllNodesWithTag("CommonDetailsText")[1].assertIsDisplayed()
        composeTestRule.onNodeWithTag("Book details").performScrollToIndex(3)
        composeTestRule.onAllNodesWithTag("H2")[0].assertIsDisplayed()
        composeTestRule.onNodeWithTag("Book details").performScrollToIndex(6)
        composeTestRule.onNodeWithTag("progress bar").assertIsDisplayed()
        composeTestRule.onAllNodesWithTag("H2")[1].assertIsDisplayed()
        composeTestRule.onNodeWithTag("Book details").performScrollToIndex(8)

        composeTestRule.onAllNodesWithTag("chapters")[0].assertIsDisplayed()



    }
}