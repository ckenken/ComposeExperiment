import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollapsingToolbarSampleWithTab() {
    val titles = listOf(
        "Introduction", "Features", "Specifications", "Reviews", "FAQs", "Contact",
        "Warranty", "Support", "About Us", "Terms", "Privacy Policy", "Feedback"
    )

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val density = LocalDensity.current

    var selectedTabIndex by remember { mutableStateOf(0) }

    // ⚡ 這裡正確計算 collapsed TopAppBar 高度 + TabRow 高度
    val collapsedTopAppBarHeightDp = 56.dp
    val tabRowHeightDp = 48.dp
    val scrollOffsetPx = 0

    Scaffold(
        topBar = {
            Column {
                LargeTopAppBar(
                    title = {
                        val collapseFraction = scrollBehavior.state.collapsedFraction
                        val textSize = lerp(30.sp, 20.sp, collapseFraction)
                        Text(
                            text = "Collapsing Toolbar",
                            fontSize = textSize,
                            color = Color.White
                        )
                    },
                    colors = TopAppBarDefaults.largeTopAppBarColors(
                        containerColor = Color(0xFF6200EE)
                    ),
                    scrollBehavior = scrollBehavior
                )

                if (scrollBehavior.state.collapsedFraction > 0.1f) {
                    ScrollableTabRow(
                        selectedTabIndex = selectedTabIndex,
                        edgePadding = 0.dp,
                        containerColor = Color(0xFF3700B3),
                        contentColor = Color.White,
                        modifier = Modifier.height(tabRowHeightDp)
                    ) {
                        titles.forEachIndexed { index, title ->
                            Tab(
                                selected = selectedTabIndex == index,
                                onClick = {
                                    selectedTabIndex = index
                                    coroutineScope.launch {
                                        listState.animateScrollToItem(
                                            index = index,
                                            scrollOffset = scrollOffsetPx
                                        )
                                    }
                                },
                                text = { Text(title) }
                            )
                        }
                    }
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            state = listState,
            contentPadding = paddingValues,
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection)
        ) {
            itemsIndexed(titles) { index, title ->
                SectionItem(title = title)
            }
        }
    }

    // 監聽滑動位置，自動切換 Tab focus
    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .collect { visibleIndex ->
                if (visibleIndex in titles.indices) {
                    selectedTabIndex = visibleIndex
                }
            }
    }
}

@Composable
fun SectionItem(title: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp) // 讓每個 section 比較高，好滑動
            .padding(16.dp),
        verticalArrangement = Arrangement.Top // title 固定貼最上面
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "This is the detailed content for \"$title\".",
            color = Color.DarkGray
        )
    }
}
