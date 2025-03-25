package hits.tsu

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
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
class HappyPathTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `happy path`(){
        composeTestRule.setContent {
            Nav()
        }

        composeTestRule.onNodeWithTag("open_for_you").assertIsDisplayed()
        composeTestRule.onNodeWithTag("Электронная почта").performTextInput("nikita")
        composeTestRule.onNodeWithTag("Пароль").performTextInput("zex312ZEX")
        composeTestRule.onNodeWithTag("signInButton").performClick()
        composeTestRule.onNodeWithTag("libraryScreen").assertIsDisplayed()
        composeTestRule.onAllNodesWithTag("carousel model")[0].performClick()
        composeTestRule.onNodeWithTag("Book details").assertIsDisplayed()
        composeTestRule.onNodeWithTag("back from details").performClick()
        composeTestRule.onNodeWithTag("libraryScreen").assertIsDisplayed()
        composeTestRule.onAllNodesWithTag("popular book")[0].performClick()
        composeTestRule.onNodeWithTag("Book details").assertIsDisplayed()
        composeTestRule.onNodeWithTag("back from details").performClick()
        composeTestRule.onNodeWithTag("libraryScreen").assertIsDisplayed()
        composeTestRule.onNodeWithTag("search").performClick()
        composeTestRule.onNodeWithTag("search screen").assertIsDisplayed()
        composeTestRule.onAllNodesWithTag("genre")[0].performClick()
        composeTestRule.onAllNodesWithTag("book search item search")[0].performClick()
        composeTestRule.onNodeWithTag("Book details").assertIsDisplayed()
        composeTestRule.onNodeWithTag("back from details").performClick()
        composeTestRule.onNodeWithTag("search screen").assertIsDisplayed()
        composeTestRule.onNodeWithTag("markup").performClick()
        composeTestRule.onNodeWithTag("bookmarks").assertIsDisplayed()
        composeTestRule.onAllNodesWithTag("read now book")[0].performClick()
        composeTestRule.onNodeWithTag("Book details").assertIsDisplayed()
        composeTestRule.onNodeWithTag("back from details").performClick()
        composeTestRule.onNodeWithTag("bookmarks").assertIsDisplayed()
        composeTestRule.onNodeWithTag("bookmarks").performScrollToIndex(5)
        composeTestRule.onAllNodesWithTag("book search item markup")[0].performClick()
        composeTestRule.onNodeWithTag("Book details").assertIsDisplayed()
        composeTestRule.onNodeWithTag("read button").performClick()
        composeTestRule.onNodeWithTag("chapter screen").assertIsDisplayed()
        composeTestRule.onNodeWithTag("back from chapter").performClick()
        composeTestRule.onNodeWithTag("back from details").performClick()
        composeTestRule.onNodeWithTag("bookmarks").assertIsDisplayed()
        composeTestRule.onNodeWithTag("play").performClick()
        composeTestRule.onNodeWithTag("chapter screen").assertIsDisplayed()
        composeTestRule.onNodeWithTag("back from chapter").performClick()
        composeTestRule.onNodeWithTag("out").performClick()
        composeTestRule.onNodeWithTag("open_for_you").assertIsDisplayed()

    }

}