import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollapsingToolbarWithExpandableSections() {
    val titles = listOf(
        "Introduction", "Features", "Specifications", "Reviews", "FAQs", "Contact",
        "Warranty", "Support", "About Us", "Terms", "Privacy Policy", "Feedback"
    )

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    var selectedTabIndex by remember { mutableStateOf(0) }

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
                    scrollBehavior = scrollBehavior,
                )

                if (scrollBehavior.state.collapsedFraction > 0.1f) {
                    ScrollableTabRow(
                        selectedTabIndex = selectedTabIndex,
                        edgePadding = 0.dp,
                        containerColor = Color(0xFF3700B3),
                        contentColor = Color.White,
                        modifier = Modifier.height(48.dp)
                    ) {
                        titles.forEachIndexed { index, title ->
                            Tab(
                                selected = selectedTabIndex == index,
                                onClick = {
                                    selectedTabIndex = index
                                    coroutineScope.launch {
                                        listState.animateScrollToItem(index)
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
                ExpandableSectionItem(title = title, isExpandable = index % 3 == 0) // 每3個做一個可展開
            }
        }
    }

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
fun ExpandableSectionItem(title: String, isExpandable: Boolean) {
    var expanded by remember { mutableStateOf(false) }
    val maxHeight = if (expanded || !isExpandable) Dp.Unspecified else 400.dp

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = maxHeight)
                .background(Color(0xFFF0F0F0))
                .padding(8.dp)
        ) {
            Text(
                text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. ".repeat(15),
                color = Color.DarkGray
            )
        }

        if (isExpandable && !expanded) {
            Spacer(modifier = Modifier.height(8.dp))
            TextButton(onClick = { expanded = true }) {
                Text("展開更多")
            }
        }
    }
}
