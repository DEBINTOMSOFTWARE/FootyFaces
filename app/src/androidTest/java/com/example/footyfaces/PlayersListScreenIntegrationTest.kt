package com.example.footyfaces

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performScrollToIndex
import androidx.compose.ui.test.performScrollToNode
import androidx.compose.ui.test.performSemanticsAction
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@ExperimentalComposeUiApi
@RunWith(AndroidJUnit4::class)
class PlayersListScreenIntegrationTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testPlayersListScreen() {
      val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        composeTestRule.waitForIdle()
        composeTestRule.waitUntil(10000) {
            composeTestRule.onNodeWithContentDescription("Players List").isDisplayed()
        }

        val players = listOf(
            "Daniel Agger",
            "Liam Miller",
            "Anthony Stokes",
            "Jermain Defoe",
            "C. Gordon",
            "Ross Wallace",
            "Nicklas Bendtner",
            "Kolo Touré",
            "Bernard Mendy",
            "Daryl Murphy",
            "Roman Bednar",
            "Johan Djourou",
            "R. Keane",
            "Carlton Cole",
            "Abdoulaye Meite",
            "Roy Carroll",
            "Steven Mouyokolo",
            "Anton Ferdinand",
            "Jonathan Spector",
            "Julien Faubert",
            "Fredrik Ljungberg",
            "Amdy Faye",
            "S. Sinclair",
            "Jason Scotland",
        )

        val newPlayers = listOf(
            "Carlos Cuéllar",
            "Stiliyan Petrov",
            "Andrew Davies",
            "Narcisse-Olivier Kapo Obou",
            "El-Hadji Diouf",
            "Junior Hoilett",
            "Lars Jacobsen",
            "Joe Hart",
            "S. Kuqi",
            "Pedro Mendes",
            "Johan Elmander",
            "Lee McCulloch",
            "Gary Teale",
            "James McEveley",
            "Aaron Hughes",
            "Brede Hangeland",
            "Winston Reid",
            "Stephen Pearson",
            "Niko Kranjčar",
            "James McFadden",
            "Ricardo Vaz Té",
            "Glenn Whelan",
            "Chris Eagles",
            "Steven Thompson",
            "Christian Kalvenes",
        )

        players.forEachIndexed { index, playerName ->
            composeTestRule.onNodeWithContentDescription("Players List")
                .performScrollToNode(hasText(playerName))
            composeTestRule.onNodeWithText(playerName).assertIsDisplayed()
        }

        composeTestRule.onNodeWithContentDescription("Players List")
            .performScrollToIndex(players.size - 1)

        println("Scrolled to bottom of initial players list")

        composeTestRule.waitUntil(30000) {
            val isLoaded = composeTestRule.onAllNodesWithText("Carlos Cuéllar").fetchSemanticsNodes().isNotEmpty()
            if (isLoaded) println("Additional players loaded successfully")
            isLoaded
        }

        newPlayers.forEach { playerName ->
            composeTestRule.onNodeWithContentDescription("Players List")
                .performScrollToNode(hasText(playerName))
            composeTestRule.onNodeWithText(playerName).assertIsDisplayed()
        }
        activityScenario.close()
    }


    @Test
    fun testPlayerListScreenDisplayAndPerformClickToLaunchDetailsScreen() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)

        composeTestRule.waitForIdle()
        composeTestRule.waitUntil(5000) {
            composeTestRule.onNodeWithContentDescription("Players List").isDisplayed()
        }
        composeTestRule.onNodeWithContentDescription("Players List").onChildAt(0)
            .assertTextContains("Daniel Agger")
        composeTestRule.onNodeWithContentDescription("Players List").onChildAt(0)
            .performSemanticsAction(SemanticsActions.OnClick)

        composeTestRule.onNodeWithContentDescription("Player Details Screen").isDisplayed()
        composeTestRule.onNodeWithContentDescription("AppBar").isDisplayed()
        composeTestRule.onNodeWithContentDescription("Player Information").isDisplayed()
        activityScenario.close()
    }
}

