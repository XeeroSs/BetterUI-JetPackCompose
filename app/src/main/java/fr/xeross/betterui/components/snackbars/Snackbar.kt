package fr.xeross.betterui.components.snackbars

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import fr.xeross.betterui.R
import kotlinx.coroutines.launch

/**
 * @version 1.0.0
 */
@Composable
fun SnackbarView(
    snackbar: SnackbarHostState,
    containerColor: Color,
    contentColor: Color,
    trainingIcon: Int? = null,
    spaceBottomNavigationHeight: Dp? = null,
    defaultData: SnackbarDefaultData = SnackbarDefaultData()
) {
    val modifier = if (spaceBottomNavigationHeight != null) {
        Modifier.padding(start = 15.dp, end = 15.dp, bottom = spaceBottomNavigationHeight)
    } else {
        Modifier.padding(horizontal = 10.dp)
    }
    SnackbarHost(
        snackbar,
        modifier
            .clip(defaultData.cornerShape)
    ) { data ->
        Snackbar(
            modifier = Modifier,
            action = { },
            dismissAction = { },
            actionOnNewLine = false,
            shape = defaultData.cornerShape,
            containerColor = containerColor,
            contentColor = contentColor,
            actionContentColor = SnackbarDefaults.actionContentColor,
            dismissActionContentColor = SnackbarDefaults.dismissActionContentColor,
            content = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                ) {
                    trainingIcon?.let {
                        Icon(
                            painter = painterResource(id = it),
                            modifier = Modifier.size(defaultData.iconSize),
                            contentDescription = "Snackbar icon"
                        )
                        Spacer(modifier = Modifier.width(defaultData.spaceBetweenIconAndText))
                    }
                    Text(
                        text = data.visuals.message,
                        fontWeight = defaultData.fontWeight,
                        fontFamily = defaultData.fontFamily,
                        fontSize = defaultData.fontSize,
                        fontStyle = defaultData.fontStyle,
                        letterSpacing = defaultData.textLetterSpacing,
                        textDecoration = defaultData.textDecoration,
                        textAlign = defaultData.textAlign,
                        lineHeight = defaultData.textLineHeight,
                        overflow = defaultData.textOverflow,
                        softWrap = defaultData.textSoftWrap,
                        maxLines = defaultData.textMaxLines
                    )
                }
            }
        )
    }
}

data class SnackbarDefaultData(
    val cornerShape: RoundedCornerShape = RoundedCornerShape(100),
    val iconSize: Dp = 18.dp,
    val spaceBetweenIconAndText: Dp = 8.dp,
    val fontSize: TextUnit = TextUnit.Unspecified,
    val fontStyle: FontStyle? = null,
    val fontWeight: FontWeight? = FontWeight.Normal,
    val fontFamily: FontFamily? = null,
    val textLetterSpacing: TextUnit = TextUnit.Unspecified,
    val textDecoration: TextDecoration? = null,
    val textAlign: TextAlign? = null,
    val textLineHeight: TextUnit = TextUnit.Unspecified,
    val textOverflow: TextOverflow = TextOverflow.Clip,
    val textSoftWrap: Boolean = true,
    val textMaxLines: Int = Int.MAX_VALUE,
)

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun SnackbarViewPreview() {
    MaterialTheme {


        val scope = rememberCoroutineScope()
        val snackbar = remember { SnackbarHostState() }

        Scaffold(snackbarHost = {
            SnackbarView(
                snackbar,
                Color.Green,
                Color.White,
                R.drawable.ic_add,
                spaceBottomNavigationHeight = 100.dp
            )
        }
        ) { p ->
            Box(modifier = Modifier.padding(p)) {
                Button(onClick = {
                    scope.launch {
                        snackbar.showSnackbar("Snackbar message")
                    }
                }) {
                    Text("Show Snackbar")
                }
            }
        }
    }
}