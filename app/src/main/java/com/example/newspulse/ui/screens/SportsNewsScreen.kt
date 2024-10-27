package com.example.newspulse.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.newspulse.ui.components.NewsList
import com.example.newspulse.ui.components.TopBar
import com.example.newspulse.ui.viewmodel.NewsViewModel

@Composable
fun SportsNewsScreen(viewModel: NewsViewModel, navController: NavController) {
    val sportsNewsState = viewModel.sportsNewsState.collectAsState()
    Column(
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.systemBars)
            .fillMaxSize()
    ) {
        TopBar(
            title = "Sports"
        )
        NewsList(sportsNewsState,navController) { page->
            page?.let { viewModel.loadMoreSportsNews(it) }
        }
    }
}