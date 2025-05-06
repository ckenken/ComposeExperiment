package com.ckenken.composeexperiment.ui.theme

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollapsingToolbarAdvancedSample() {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = {
                    val collapseFraction = scrollBehavior.state.collapsedFraction

                    // 字體大小
                    val textSize = lerp(32.sp, 20.sp, collapseFraction)

                    // 顏色變淡
                    val bgColor = lerp(Color(0xFF6200EE), Color(0xFF3700B3), collapseFraction)

                    // 標題垂直偏移
                    val verticalOffset = lerp(0.dp, (-8).dp, collapseFraction)

                    // Icon透明度
                    val iconAlpha = 1f - collapseFraction

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .offset(y = verticalOffset),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            text = "Collapsing Toolbar",
                            fontSize = textSize,
                            color = Color.White
                        )
                    }
                },
                navigationIcon = {
                    val collapseFraction = scrollBehavior.state.collapsedFraction
                    IconButton(onClick = { /* Back click */ }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            modifier = Modifier.alpha(1f - collapseFraction),
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    val collapseFraction = scrollBehavior.state.collapsedFraction
                    IconButton(onClick = { /* Share click */ }) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Share",
                            modifier = Modifier.alpha(1f - collapseFraction),
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = lerp(Color(0xFF6200EE), Color(0xFF3700B3), scrollBehavior.state.collapsedFraction)
                ),
                scrollBehavior = scrollBehavior
            )
        }
    ) { padding ->
        LazyColumn(
            contentPadding = padding,
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
        ) {
            items((1..50).toList()) { index ->
                Text(
                    text = "Item $index",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    fontSize = 18.sp
                )
            }
        }
    }
}
