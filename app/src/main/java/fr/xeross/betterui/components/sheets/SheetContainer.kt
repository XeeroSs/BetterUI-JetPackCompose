package fr.xeross.betterui.components.sheets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.xeross.betterui.R

interface SheetListener {
    val resIconId: Int
    val iconOnRight: Boolean
    val iconColor: Color
    val onClick: () -> Unit
}

/**
 * @version 1.1.0
 */
@Composable
fun SheetContainer(
    title: String,
    textColor: Color,
    actions: Array<SheetListener>,
    container: Color = Color.Unspecified,
    paddingHorizontal: Dp = 0.dp,
    paddingVertical: Dp = 0.dp,
    iconSize: Dp = 16.dp,
    spaceBetweenIcon: Dp = 15.dp,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .background(container)
            .padding(top = paddingVertical, start = paddingHorizontal, end = paddingHorizontal)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            for (a in actions) {
                if (!a.iconOnRight) {
                    IconButton(modifier = Modifier.size(iconSize), onClick = { a.onClick }) {
                        Icon(
                            painter = painterResource(id = a.resIconId),
                            tint = a.iconColor,
                            contentDescription = ""
                        )
                    }
                    Spacer(modifier = Modifier.width(spaceBetweenIcon))
                }
            }
            Text(
                text = title, overflow = TextOverflow.Ellipsis, maxLines = 1,
                color = textColor,
                fontSize = 18.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                for (a in actions) {
                    if (a.iconOnRight) {
                        Spacer(modifier = Modifier.width(spaceBetweenIcon))
                        IconButton(
                            modifier = Modifier.size(iconSize),
                            onClick = { a.onClick() }) {
                            Icon(
                                painter = painterResource(id = a.resIconId),
                                tint = a.iconColor,
                                contentDescription = ""
                            )
                        }
                    }
                }
            }
        }
        Box(modifier = Modifier.weight(1f)) {
            content()
        }
    }

}


@Preview
@Composable
private fun SheetContainerPreview() {
    SheetContainer("Sheet content content content content",
        Color.Red,
        arrayOf(
            object : SheetListener {
                override val resIconId = R.drawable.ic_check
                override val iconOnRight = false
                override val iconColor = Color.Blue
                override val onClick = {}
            },
            object : SheetListener {
                override val resIconId = R.drawable.ic_add
                override val iconOnRight = true
                override val iconColor = Color.Green
                override val onClick = {}
            }
        ),
        MaterialTheme.colorScheme.background,
        paddingHorizontal = 15.dp, paddingVertical = 15.dp) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            Text("Content", modifier = Modifier.align(Alignment.Center), fontSize = 30.sp)
        }
    }
}