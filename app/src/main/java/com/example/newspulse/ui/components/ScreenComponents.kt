package com.example.newspulse.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.newspulse.R
import com.example.newspulse.TrendingNewsDetailScreenRoute
import com.example.newspulse.domain.model.Article
import com.example.newspulse.domain.model.News
import com.example.newspulse.ui.viewmodel.UiState
import com.example.newspulse.utils.ProgressIndicator

@Composable
fun TopBar(title:String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier
                .padding(horizontal = 8.dp)
        )
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(end = 16.dp)
                .clip(CircleShape)
                .background(Color.LightGray)
                .padding(10.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "search",
                tint = Color.Black,
            )
        }
    }
}

@Composable
fun NewsList(state: State<UiState<News>>,navController:NavController, loadMoreNews:(page: String?)-> Unit) {

    val lazyListState = rememberLazyListState()

    var newsList by remember {
        mutableStateOf<News?>(null)
    }

    newsList = when(state.value) {
        UiState.Loading-> {
            null
        }

        is UiState.Error -> {
            null
        }
        is UiState.Success -> {
            (state.value as UiState.Success<News>).data
        }
    }

    newsList?.let {
        LazyColumn(
            state = lazyListState,
            content = {
                items(it.results) { article ->
                    if (it.results.indexOf(article) == it.results.size - 1) {
                        loadMoreNews(it.nextPage)
                    }
                    key(newsList?.results) {
                        article.title
                        article.description
                        article.image_url
                        article.pubDate
                    }
                    NewsListItem(article) {
                        navController.navigate(
                            TrendingNewsDetailScreenRoute(
                                article.source_name,
                                article.source_icon,
                                article.title,
                                article.image_url,
                                article.description,
                                article.link
                            )
                        )
                    }
                }
            }
        )
    }?: ProgressIndicator()
}

@Composable
fun NewsListItem(article: Article, onClick: ()->Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
    ) {
        Text(
            text = article.title,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            fontFamily = FontFamily(Font(R.font.poppins_bold)),
            modifier = Modifier
                .padding(top = 16.dp)
                .padding(horizontal = 8.dp)
        )
        Text(
            text = article.pubDate,
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.open_sans)),
            color = Color.Gray,
            modifier = Modifier
                .padding(bottom = 8.dp)
                .padding(horizontal = 8.dp)
        )

        Text(
            text = article.description?:"No description available",
            fontSize = 16.sp,
            maxLines = 6,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Justify,
            modifier = Modifier
                .padding(bottom = 8.dp)
                .padding(horizontal = 8.dp)
        )

        AsyncImage(
            model = article.image_url,
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
        )

        HorizontalDivider(
            modifier = Modifier
                .padding(top = 16.dp)
        )
    }
}