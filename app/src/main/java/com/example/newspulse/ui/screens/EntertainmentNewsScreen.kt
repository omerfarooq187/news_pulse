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
fun EntertainmentNewsScreen(viewModel: NewsViewModel,navController: NavController) {
    val entertainmentNewsState = viewModel.entertainmentNewsState.collectAsState()
    Column(
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.systemBars)
            .fillMaxSize()
    ) {
        TopBar(
            title = "Entertainment"
        )
        NewsList(entertainmentNewsState, navController) { page ->
            page?.let { viewModel.loadMoreEntertainmentNews(it) }
        }
    }
}