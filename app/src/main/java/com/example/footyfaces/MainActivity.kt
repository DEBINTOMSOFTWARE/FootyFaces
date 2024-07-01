package com.example.footyfaces

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.footyfaces.presentation.ui.PlayerDetailsScreen
import com.example.footyfaces.presentation.ui.PlayerListScreen
import com.example.footyfaces.presentation.viewmodel.PlayersViewModel
import com.example.footyfaces.ui.theme.FootyFacesTheme
import dagger.hilt.android.AndroidEntryPoint

sealed class Destination(val route: String) {
    data object PlayersScreen : Destination("playersScreen")
    data object PlayerDetailsScreen : Destination("playerDetailsScreen/{playerId}") {
        fun createRoute(playerId: Int) = "playerDetailsScreen/$playerId"
    }
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val playersViewModel: PlayersViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            FootyFacesTheme {
                AppNavigation(
                    playersViewModel = playersViewModel,
                    navController = rememberNavController()
                )
            }
        }
    }
}

@Composable
fun AppNavigation(
    playersViewModel: PlayersViewModel,
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = Destination.PlayersScreen.route) {
        composable(Destination.PlayersScreen.route) {
            PlayerListScreen(playersViewModel = playersViewModel, navController = navController)
        }
        composable(Destination.PlayerDetailsScreen.route) { navBackStackEntry ->
            val playerId = navBackStackEntry.arguments?.getString("playerId")
            if (playerId == null) {
                Toast.makeText(
                    LocalContext.current,
                    "Player id required",
                    Toast.LENGTH_SHORT
                ).show()
                navController.navigate(Destination.PlayersScreen.route) {
                    popUpTo(Destination.PlayersScreen.route) {
                        inclusive = true
                    }
                }
            } else {
                PlayerDetailsScreen(
                    playersViewModel = playersViewModel,
                    navController = navController
                )
            }
        }
    }
}

