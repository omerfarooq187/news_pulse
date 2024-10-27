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
fun PoliticsNewsScreen(viewModel: NewsViewModel, navController: NavController) {
    val politicsNewsState = viewModel.politicsNewsState.collectAsState()
    Column(
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.systemBars)
            .fillMaxSize()
    ) {
        TopBar(
            title = "Politics"
        )
        NewsList(politicsNewsState, navController) { page->
            page?.let { viewModel.loadMorePoliticsNews(it) }
        }
    }


}