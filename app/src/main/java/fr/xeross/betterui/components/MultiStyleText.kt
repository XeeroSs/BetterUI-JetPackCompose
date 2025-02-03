package fr.xeross.betterui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import fr.xeross.betterui.theme.BetterUITheme

/**
 * @version 1.0.0
 */

@OptIn(ExperimentalTextApi::class)
@Composable
fun MultiStyleText(
    vararg text: Pair<String, StyleRange>,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = LocalTextStyle.current
) {
    val annotatedString = buildAnnotatedString {
        text.forEachIndexed { index, range ->
            if (range.second.space && index > 0) append(" ")
            val brush = range.second.brush
            if (brush != null) {
                withStyle(
                    style = SpanStyle(
                        brush = range.second.brush,
                        fontSize = range.second.size ?: fontSize,
                        fontWeight = range.second.weight ?: fontWeight,

                        )
                ) {
                    append(range.first)
                }
            } else {
                withStyle(
                    style = SpanStyle(
                        color = range.second.color ?: color,
                        fontSize = range.second.size ?: fontSize,
                        fontWeight = range.second.weight ?: fontWeight
                    )
                ) {
                    append(range.first)
                }
            }
        }
    }

    Text(
        text = annotatedString,
        modifier = modifier,
        color = color,
        fontWeight = fontWeight,
        fontStyle = fontStyle,
        fontFamily = fontFamily,
        fontSize = fontSize,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = lineHeight,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        onTextLayout = onTextLayout,
        style = style
    )
}

@Preview(showBackground = true)
@Composable
fun MultiStyleTextPreview() {
    BetterUITheme {
        Column {
            MultiStyleText(
                Pair("Ceci", StyleRange(color = Color.Red, weight = FontWeight.Normal)),
                Pair("est", StyleRange()),
                Pair("un", StyleRange()),
                Pair(
                    "Te",
                    StyleRange(color = Color.DarkGray, size = 25.sp, weight = FontWeight.Bold)
                ),
                Pair(
                    "st",
                    StyleRange(
                        space = false,
                        color = Color.LightGray,
                        size = 25.sp,
                        weight = FontWeight.Bold
                    )
                ),
                Pair("!", StyleRange()),
                fontWeight = FontWeight.Light,
                color = Color.Gray
            )
        }
    }
}


data class StyleRange(
    val color: Color? = null,
    val brush: Brush? = null,
    val size: TextUnit? = null,
    val weight: FontWeight? = null,
    val space: Boolean = true
)