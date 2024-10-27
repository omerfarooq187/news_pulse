package com.example.newspulse.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withLink
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.newspulse.R
import com.example.newspulse.TrendingNewsDetailScreenRoute

@Composable
fun NewsDetailsScreen(args: TrendingNewsDetailScreenRoute, navController: NavHostController) {
    val title by remember {
        mutableStateOf(args.title)
    }
    val description by remember {
        mutableStateOf(args.description)
    }
    val imageUrl by remember {
        mutableStateOf(args.imageUrl)
    }
    val sourceName by remember {
        mutableStateOf(args.sourceName)
    }
    val sourceIcon by remember {
        mutableStateOf(args.sourceIcon)
    }
    val link by remember {
        mutableStateOf(args.link)
    }
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars)
            .verticalScroll(scrollState)
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Default.ArrowBack,
            contentDescription = "back",
            modifier = Modifier
                .padding(16.dp)
                .clickable {
                    navController.popBackStack()
                }
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            AsyncImage(
                model = sourceIcon,
                contentDescription = "icon",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
            Text(
                text = sourceName?:"No source name available",
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                fontFamily = FontFamily(Font(R.font.poppins_bold)),
                color = Color.Gray
            )
        }
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )
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
                    model = imageUrl,
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
        Text(
            text = description?:"No description available",
            fontSize = 16.sp,
            textAlign = TextAlign.Justify,
            modifier = Modifier
                .padding(4.dp)
        )


        val uriHandler = LocalUriHandler.current
        BasicText(
            buildAnnotatedString {
                append("\nFor more detailed news ...")
                val sourceLink = LinkAnnotation.Url(
                    url = link,
                    styles = TextLinkStyles(style = SpanStyle(color = Color.Blue, fontSize = 16.sp))
                ) {
                    val url = (it as LinkAnnotation.Url).url
                    uriHandler.openUri(url)
                }
                withLink(sourceLink) {
                    append("\n"+link)
                }
            },
            modifier = Modifier
                .padding(4.dp)
        )
    }
}