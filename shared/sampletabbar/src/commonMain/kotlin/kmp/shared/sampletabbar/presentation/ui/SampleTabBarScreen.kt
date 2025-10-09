import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.icerock.moko.resources.ImageResource
import kmp.shared.base.MR
import kmp.shared.sampletabbar.presentation.ui.NativeColor
import kmp.shared.sampletabbar.presentation.ui.NativeScaffold
import kmp.shared.sampletabbar.presentation.ui.TabItem
import kmp.shared.sampletabbar.presentation.ui.Toolbar
import kmp.shared.sampletabbar.presentation.ui.ToolbarButtonData
import kmp.shared.sampletabbar.presentation.ui.ToolbarButtonPosition
import kmp.shared.sampletabbar.presentation.vm.SampleTab
import kmp.shared.sampletabbar.presentation.vm.SampleTabBarIntent
import kmp.shared.sampletabbar.presentation.vm.SampleTabBarState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SampleTabBarScreen(
    state: SampleTabBarState,
    onIntent: (SampleTabBarIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    NativeScaffold(
        toolbar = Toolbar(
            title = SampleTab.entries[state.selectedTabPosition].title,
            buttons = listOf(
                ToolbarButtonData(
                    icon = MR.images.search,
                    description = "Search",
                    onClick = { println("XXX Search clicked") },
                    position = ToolbarButtonPosition.Leading,
                    tint = NativeColor(Color.Red),
                ),
                ToolbarButtonData(
                    icon = MR.images.person,
                    description = "Person",
                    onClick = { println("XXX Person clicked") },
                    position = ToolbarButtonPosition.Trailing,
                ),
                ToolbarButtonData(
                    icon = MR.images.home,
                    description = "Home",
                    onClick = { println("XXX Home clicked") },
                    position = ToolbarButtonPosition.Trailing,
                    tint = NativeColor(MaterialTheme.colorScheme.primary),
                ),
            ),
        ),
        tabs = state.tabs.map { tab ->
            TabItem(
                position = tab.ordinal,
                title = tab.title,
                icon = tab.icon,
            )
        },
        selectedTabPosition = state.selectedTabPosition,
        onTabSelected = { index ->
            onIntent(SampleTabBarIntent.OnTabSelected(index))
        },
        modifier = modifier.fillMaxSize(),
    ) { position, innerPadding ->
        if (position != null) {
            val tab = SampleTab.entries[position]
            when (tab) {
                SampleTab.Numbers -> ScrollableContent(contentPadding = innerPadding) { index ->
                    Text(
                        text = index.toString(),
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                    )
                }

                SampleTab.Sentences -> ScrollableContent(contentPadding = innerPadding) { index ->
                    Text(
                        text = "This is item number $index",
                        style = MaterialTheme.typography.headlineMedium,
                    )
                    Text(
                        text = "And this is it's body",
                        style = MaterialTheme.typography.bodySmall,
                    )
                }

                SampleTab.Images -> ScrollableContent(contentPadding = innerPadding) { index ->
                    AsyncImage(
                        model = "https://picsum.photos/id/$index/200/300",
                        contentDescription = null,
                        modifier = Modifier.fillMaxWidth(),
                        contentScale = ContentScale.FillWidth,
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalHazeMaterialsApi::class)
@Composable
private fun ScrollableContent(
    contentPadding: PaddingValues = PaddingValues(),
    modifier: Modifier = Modifier,
    itemCount: Int = 20,
    itemContent: @Composable ColumnScope.(index: Int) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
    ) {
        items(count = itemCount) { index ->
            Card(
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    itemContent(index)
                }
            }
        }
    }
}

private val SampleTab.title: String
    get() = when (this) {
        SampleTab.Numbers -> "Numbers"
        SampleTab.Sentences -> "Sentences"
        SampleTab.Images -> "Images"
    }

private val SampleTab.icon: ImageResource
    get() = when (this) {
        SampleTab.Numbers -> MR.images.home
        SampleTab.Sentences -> MR.images.search
        SampleTab.Images -> MR.images.person
    }
