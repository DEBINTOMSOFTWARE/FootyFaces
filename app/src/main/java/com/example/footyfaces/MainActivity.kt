package com.example.footyfaces

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.footyfaces.presentation.ui.PlayerListScreen
import com.example.footyfaces.presentation.viewmodel.PlayersViewModel
import com.example.footyfaces.ui.theme.FootyFacesTheme
import dagger.hilt.android.AndroidEntryPoint

sealed class Destination(val route: String) {
   data object PlayersScreen: Destination("playersScreen")
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  private val playersViewModel by viewModels<PlayersViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            FootyFacesTheme {
              AppNavigation(playersViewModel = playersViewModel, navController = rememberNavController())
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
          PlayerListScreen(playersViewModel = playersViewModel)
     }
   }
}

