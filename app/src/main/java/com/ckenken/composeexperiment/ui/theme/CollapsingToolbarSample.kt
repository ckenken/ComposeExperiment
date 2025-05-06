package com.ckenken.composeexperiment.ui.theme

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import kotlin.math.abs

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollapsingToolbarSample() {
    // 用 TopAppBar 的 scroll behavior
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = {
                    // 動態控制 title 的大小
                    val collapseFraction = scrollBehavior.state.collapsedFraction
                    val textSize = lerp(30.sp, 20.sp, collapseFraction)
                    Text(
                        text = "Collapsing Toolbar",
                        fontSize = textSize
                    )
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = Color(0xFF6200EE)
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
