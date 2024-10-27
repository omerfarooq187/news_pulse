package com.example.newspulse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.newspulse.ui.screens.EntertainmentNewsScreen
import com.example.newspulse.ui.screens.MainScreen
import com.example.newspulse.ui.screens.NewsDetailsScreen
import com.example.newspulse.ui.screens.PoliticsNewsScreen
import com.example.newspulse.ui.screens.SportsNewsScreen
import com.example.newspulse.ui.theme.NewsPulseTheme
import com.example.newspulse.ui.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsPulseTheme {
                App()
            }
        }
    }
}

//Routes
@Serializable
object MainScreenRoute

@Serializable
data class TrendingNewsDetailScreenRoute(
    val sourceName: String?,
    val sourceIcon: String?,
    val title: String,
    val imageUrl: String?,
    val description: String?,
    val link: String
)

@Serializable
object PoliticsNewsScreenRoute

@Serializable
object EntertainmentNewsScreenRoute

@Serializable
object SportsNewsScreenRoute

@Composable
fun App() {
    val viewModel: NewsViewModel = hiltViewModel()
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = MainScreenRoute) {
        composable<MainScreenRoute> {
            MainScreen(viewModel, navController)
        }
        composable<TrendingNewsDetailScreenRoute> {
            val args = it.toRoute<TrendingNewsDetailScreenRoute>()
            NewsDetailsScreen(args, navController)
        }
        composable<PoliticsNewsScreenRoute> {
            PoliticsNewsScreen(viewModel, navController)
        }
        composable<EntertainmentNewsScreenRoute> {
            EntertainmentNewsScreen(viewModel,navController)
        }
        composable<SportsNewsScreenRoute> {
            SportsNewsScreen(viewModel,navController)
        }
    }
}