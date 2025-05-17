import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
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
//                if (scrollBehavior.state.collapsedFraction > 0.1f) {
//                    BookingTopAppBar({}, {}, {})
//                } else {
                LargeTopAppBar(
                    title = {
                        if (scrollBehavior.state.collapsedFraction > 0.1f) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                IconButton(onClick = { }) {
                                    Icon(Icons.Default.KeyboardArrowLeft, contentDescription = "Back")
                                }

                                Spacer(Modifier.width(16.dp))

                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        text = "1月30日 ~ 2月1日・1晚",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Default.Home,
                                            contentDescription = null,
                                            modifier = Modifier.size(16.dp),
                                            tint = Color.Gray
                                        )
                                        Text(" 1  ", color = Color.Gray, fontSize = 14.sp)

                                        Icon(
                                            imageVector = Icons.Default.Person,
                                            contentDescription = null,
                                            modifier = Modifier.size(16.dp),
                                            tint = Color.Gray
                                        )
                                        Text(" 2  ", color = Color.Gray, fontSize = 14.sp)

                                        Icon(
                                            imageVector = Icons.Default.Face,
                                            contentDescription = null,
                                            modifier = Modifier.size(16.dp),
                                            tint = Color.Gray
                                        )
                                        Text(" 0", color = Color.Gray, fontSize = 14.sp)
                                    }
                                }
                                Spacer(Modifier.width(8.dp))
                                IconButton(onClick = { }) {
                                    Icon(Icons.Default.FavoriteBorder, contentDescription = "Favorite")
                                }
                                Spacer(Modifier.width(8.dp))
                                IconButton(onClick = { }) {
                                    Icon(Icons.Default.Share, contentDescription = "Share")
                                }
                            }
                        } else {
                            val collapseFraction = scrollBehavior.state.collapsedFraction
                            val textSize = lerp(30.sp, 20.sp, collapseFraction)
                            Text(
                                text = "Collapsing Toolbar",
                                fontSize = textSize,
                                color = Color.White
                            )
                        }
                    },
                    colors = TopAppBarDefaults.largeTopAppBarColors(
                        containerColor = Color(0xFF6200EE)
                    ),
                    scrollBehavior = scrollBehavior,
                )
//                }

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
