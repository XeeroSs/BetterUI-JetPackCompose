package fr.xeross.betterui.components.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.xeross.betterui.extensions.ColorExtension.transparent
import fr.xeross.betterui.extensions.ComposeExtension.stateOf

interface IRoundedTab {
    fun resTabNameId(): Int
    fun position(): Int
}


// todo animation transition
@Composable
fun <E : IRoundedTab> RoundedTabView(
    tabs: Array<E>,
    selectedTab: E ,
    disable: Boolean = false,
    colorBackground: Color,
    colorContent: Color,
    modifier: Modifier = Modifier,
    textSize: TextUnit = 13.sp,
    border: Dp? = 1.5.dp,
    onTabSelected: (E) -> Unit = {}
) {
    val localDensity = LocalDensity.current
    var height by stateOf(40.dp)
    var width by stateOf(0.dp)

    val content = if (!disable) colorContent else colorContent.transparent(50f)
    val container = if (!disable) colorBackground else colorBackground.transparent(50f)

    Box(modifier = modifier.onGloballyPositioned { coordinates ->
        height = with(localDensity) { coordinates.size.height.toDp() }
        width = with(localDensity) { coordinates.size.width.toDp() }
    }) {
        TabRow(selectedTabIndex = selectedTab.position(),
            containerColor = container,
            modifier = modifier
                .clip(RoundedCornerShape(100)),
            divider = {},
            indicator = {
            }
        ) {
            tabs.forEachIndexed { index, e ->
                val text = e.resTabNameId()
                val selected = selectedTab.position() == index
                Tab(
                    modifier = if (!selected) Modifier
                        .height(height)
                        .padding(5.dp)
                        .clip(RoundedCornerShape(100))
                        .border(1.5.dp, MaterialTheme.colorScheme.primary,RoundedCornerShape(100) )
                        .background(
                            container
                        )
                    else Modifier
                        .height(height)
                        .padding(5.dp)
                        .clip(RoundedCornerShape(50f))
                        .background(
                            content
                        ),
                    selected = selected,
                    onClick = {
                        if (disable) return@Tab
                        val s = tabs[index]
                        onTabSelected(s)
                    },
                    text = {
                        Text(
                            text = stringResource(id = text),
                            fontSize = textSize,
                            color = if (!selected) content
                            else container,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                )
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun RoundedTabPreview() {

}