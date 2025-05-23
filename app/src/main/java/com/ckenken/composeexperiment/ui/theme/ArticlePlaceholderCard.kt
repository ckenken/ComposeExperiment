package com.ckenken.composeexperiment.ui.theme

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer


@Preview(showBackground = true)
@Composable
fun ArticleLoadingList() {
    LazyColumn {
        items(5) { // 顯示 5 條假資料
            ArticlePlaceholderCard()
        }
    }
}

@Composable
fun ArticlePlaceholderCard() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // 標題 Placeholder
        Box(
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .height(24.dp)
                .placeholder(
                    visible = true,
                    highlight = PlaceholderHighlight.shimmer(),
                    color = Color.LightGray
                )
        )
        Spacer(modifier = Modifier.height(8.dp))
        // 內容 Placeholder
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp)
                .placeholder(
                    visible = true,
                    highlight = PlaceholderHighlight.shimmer(),
                    color = Color.LightGray
                )
        )
    }
}