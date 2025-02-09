package fr.xeross.betterui.components.inputs

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.scaleIn
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import fr.xeross.betterui.R
import fr.xeross.betterui.extensions.ComposeExtension.stateOf

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BetterCheckBox(
    color: Color,
    isFilled: Boolean = true,
    icon: Int = R.drawable.ic_check,
    size: Dp = 20.dp,
    iconSize: Dp = 20.dp / 2,
    border: Dp = 1.dp,
    isCheck: Boolean = false,
    corner: Dp = 5.dp,
    onChange: (Boolean) -> Unit,
) {
    var checked by stateOf(isCheck)

    val fill by animateColorAsState(
        if (isFilled) color else Color.Transparent,
        label = "color"
    )

    val empty by animateColorAsState(
        Color.Transparent,
        label = "color"
    )

    Box(modifier = Modifier
        .border(border, color, RoundedCornerShape(corner))
        .size(size)
        .drawBehind {
            val cornerRadius = corner.toPx()
            drawRoundRect(
                color = if (checked) fill else empty,
                cornerRadius = CornerRadius(cornerRadius, cornerRadius)
            )
        }
        .clip(RoundedCornerShape(corner))
        .clickable {
            checked = !checked
            onChange(checked)
        }
    ) {
        Column(modifier = Modifier.align(Alignment.Center)) {
            AnimatedVisibility(
                checked,
                enter = scaleIn(initialScale = 0.5f),
                exit = shrinkOut(shrinkTowards = Alignment.Center)
            ) {
                Icon(
                    modifier = Modifier.size(iconSize),
                    painter = painterResource(icon),
                    contentDescription = "icon",
                    tint = if (isFilled) Color.Unspecified else color
                )
            }
        }

    }
}


@Preview
@Composable
fun BetterCheckBoxPreview() {
    Row {
        Text(text = "Check Box Filled")
        Spacer(modifier = Modifier.width(5.dp))
        BetterCheckBox(Color.Cyan, isFilled = true, size = 20.dp, iconSize = 10.dp, corner = 5.dp) {

        }
    }
}