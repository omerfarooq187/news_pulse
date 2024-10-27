package com.example.newspulse.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandIn
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.newspulse.EntertainmentNewsScreenRoute
import com.example.newspulse.PoliticsNewsScreenRoute
import com.example.newspulse.R
import com.example.newspulse.SportsNewsScreenRoute
import com.example.newspulse.TrendingNewsDetailScreenRoute
import com.example.newspulse.domain.model.Article
import com.example.newspulse.domain.model.News
import com.example.newspulse.ui.viewmodel.NewsViewModel
import com.example.newspulse.ui.viewmodel.UiState
import com.example.newspulse.utils.ProgressIndicator


@Composable
fun MainScreen(viewModel: NewsViewModel, navController: NavHostController) {
    val topHeadlinesListState = rememberLazyListState()

    var sectionVisibility by remember {
        mutableStateOf(true)
    }
    // Track the scroll offset to determine the shrinking effect
    var scrollOffset by remember { mutableFloatStateOf(0f) }

    LaunchedEffect(topHeadlinesListState) {
        snapshotFlow {
            Pair(
                topHeadlinesListState.firstVisibleItemIndex,
                topHeadlinesListState.firstVisibleItemScrollOffset
            )
        }.collect { (index, offset) ->
            scrollOffset = offset.toFloat() // Update scroll offset
            sectionVisibility = index == 0 && offset == 0 // Check if the first section is visible
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        AnimatedVisibility(
            visible = sectionVisibility,
            enter = expandIn(),
            exit = shrinkOut()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                TrendingNewsSection(viewModel,navController)
                CategorySection(navController,viewModel)
            }
        }

        val paddingModifier = if (!sectionVisibility) {
            Modifier.windowInsetsPadding(WindowInsets.systemBars)
        } else {
            Modifier.padding(0.dp)
        }

        // List of top headlines (this will scroll as usual)
        TopHeadlines(viewModel, navController, topHeadlinesListState, paddingModifier)
    }
}


@Composable
fun TrendingNewsSection(viewModel: NewsViewModel, navController: NavHostController) {
    val trendingNewsState = viewModel.trendingNewsState.collectAsState().value
    var news by remember {
        mutableStateOf<News?>(null)
    }
    when (trendingNewsState) {
        is UiState.Loading -> {
            news = null
        }

        is UiState.Success -> {
            news = trendingNewsState.data
        }

        is UiState.Error -> {
            Text(text = trendingNewsState.message)
        }
    }

    val pagerState = rememberPagerState {
        news?.results?.size ?: 0
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .windowInsetsPadding(WindowInsets.systemBars)
                .fillMaxWidth()
        ) {
            Text(
                text = "Trending News",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(16.dp)
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
        if (news != null) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent)
                    .clickable {
                        navController.navigate(
                            TrendingNewsDetailScreenRoute(
                                news?.results?.get(pagerState.currentPage)?.source_name ?: "",
                                news?.results?.get(pagerState.currentPage)?.source_icon ?: "",
                                news?.results?.get(pagerState.currentPage)?.title ?: "",
                                news?.results?.get(pagerState.currentPage)?.image_url ?: "",
                                news?.results?.get(pagerState.currentPage)?.description ?: "",
                                news?.results?.get(pagerState.currentPage)?.link ?: ""
                            )
                        )
                    },
                key = { index ->
                    news?.let {
                        it.results[index].title
                        it.results[index].description
                        it.results[index].image_url
                    } ?: 0
                },
                pageContent = { index ->
                    val article = news?.let { it.results[index] }
                    if (article != null) {
                        TrendingNewsCard(article)
                    }
                    if (pagerState.currentPage == pagerState.pageCount - 1) {
                        news?.let { viewModel.loadMoreTrendingNews(it.nextPage!!) }
                    }
                }
            )
        } else {
            ProgressIndicator()
        }
    }
}


//Trending News card that displays the trending news in horizontal pager
@Composable
fun TrendingNewsCard(article: Article) {
    Card(
        modifier = Modifier
            .width(400.dp)
            .padding(8.dp)
            .height(200.dp),
        shape = RoundedCornerShape(22.dp),
        elevation = CardDefaults.cardElevation(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            AsyncImage(
                model = article.image_url,
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(Color.Transparent, Color.Black),
                            startY = 300f
                        )
                    )
                    .padding(16.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    text = article.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = article.description?:"No description available",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.8f),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

// Category section which displays category items in row
@Composable
fun CategorySection(navController: NavController, viewModel: NewsViewModel) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = "Categories",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            modifier = Modifier
                .padding(top = 16.dp, bottom = 8.dp)
                .padding(horizontal = 8.dp)
        )

        LazyRow {
            items(categories.size) { index ->
                CategoryCard(index) {
                    when(categories[index].title) {
                        "Politics" -> {
                            viewModel.getPoliticsNews()
                            navController.navigate(
                                PoliticsNewsScreenRoute
                            )
                        }
                        "Entertainment" -> {
                            viewModel.getEntertainmentNews()
                            navController.navigate(
                                EntertainmentNewsScreenRoute
                            )
                        }
                        "Sports" -> {
                            viewModel.getSportsNews()
                            navController.navigate(
                                SportsNewsScreenRoute
                            )
                        }
                    }
                }
            }
        }
    }
}


// Category Model data class
data class Category(
    val title: String,
    val image: Int
)

//Category items
val categories = listOf(
    Category(
        "Crime",
        R.drawable.crime
    ),
    Category(
        "Politics",
        R.drawable.politics
    ),
    Category(
        "Entertainment",
        R.drawable.entertainment
    ),
    Category(
        "Sports",
        R.drawable.sports
    )
)


//Category presentation card composable
@Composable
fun CategoryCard(index: Int, onClick:()->Unit) {

    val category = categories[index]
    Card(
        elevation = CardDefaults.elevatedCardElevation(16.dp),
        modifier = Modifier
            .padding(8.dp)
            .height(150.dp)
            .width(150.dp)
            .clickable {
                onClick()
            }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(category.image),
                contentDescription = category.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
            )

            Column(
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            listOf(Color.Transparent, Color.Black),
                            startY = 100f
                        )
                    )
                    .padding(16.dp)
            ) {
                Text(
                    text = category.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

//Headlines news section
@Composable
fun TopHeadlines(
    viewModel: NewsViewModel,
    navController: NavHostController,
    topHeadlinesListState: LazyListState,
    modifier: Modifier
) {
    var topHeadlines by remember {
        mutableStateOf<News?>(null)
    }
    when (val topHeadlinesState = viewModel.topHeadlinesState.collectAsState().value) {
        is UiState.Loading -> {
            topHeadlines = null
        }

        is UiState.Success -> {
            topHeadlines = topHeadlinesState.data
        }

        is UiState.Error -> {
            Text(text = topHeadlinesState.message)
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = "Top Headlines",
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            modifier = Modifier
                .padding(top = 16.dp, bottom = 8.dp)
                .padding(horizontal = 8.dp)
        )
        topHeadlines?.let {
            LazyColumn(
                state = topHeadlinesListState,
                content = {
                    items(it.results) { article ->

                        if (it.results.indexOf(article) == it.results.size -1) {
                            it.nextPage?.let { page-> viewModel.loadMoreHeadlines(page) }
                        }

                        key(topHeadlines?.results) {
                            article.title
                            article.description
                            article.image_url
                            article.pubDate
                        }
                        Headline(article) {
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
        } ?: ProgressIndicator()
    }
}


@Composable
fun Headline(article: Article, onClick: ()->Unit) {
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
            fontSize = 20.sp,
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