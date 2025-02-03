package fr.xeross.betterui.components.pickers.colors

import android.graphics.Color.colorToHSV
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.VectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.xeross.betterui.R
import java.util.Locale

val DEFAULT_COLOR_PANEL = Color.Blue

@Preview
@Composable
fun ColorPickerPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {
        ColorPicker(Icons.Filled.Done, DEFAULT_COLOR_PANEL)
        ColorPicker(painterResource(id = R.drawable.ic_add), DEFAULT_COLOR_PANEL)
    }
}

@Composable
fun ColorPicker(
    resIconId: ImageVector,
    defaultColor: Color ,
    onValueChange: (Color) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val hsv = remember {
            val hsv = floatArrayOf(0f, 0f, 0f)
            colorToHSV(defaultColor.toArgb(), hsv)
            mutableStateOf(
                Triple(hsv[0], hsv[1], hsv[2])
            )
        }
        val color = remember(hsv.value) {
            mutableStateOf(Color.hsv(hsv.value.first, hsv.value.second, hsv.value.third))
        }
        ColorPickerPanel(
            Modifier
                .height(300.dp)
                .fillMaxWidth(), hue = hsv.value.first
        ) { sat, value ->
            hsv.value = Triple(hsv.value.first, sat, value)

            onValueChange(Color.hsv(hsv.value.first, hsv.value.second, hsv.value.third))
        }
        Spacer(modifier = Modifier.height(5.dp))
        ColorPickerHueBar(Modifier) { hue ->
            hsv.value = Triple(hue, hsv.value.second, hsv.value.third)
        }
        Spacer(modifier = Modifier.height(5.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(75.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(MaterialTheme.colors.sheetOnBackground)
        ) {
            Text(
                text = "Hex",
                fontWeight = FontWeight.Bold,
                fontFamily = montserratFamily,
                fontSize = 16.sp,
                color = MaterialTheme.colors.sheetBackground,
                modifier = Modifier.padding(start = 20.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(end = 20.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .align(Alignment.CenterEnd)
                        .background(Color(0xFF1E1E1E))
                ) {
                    Box(
                        modifier = Modifier
                            .padding(6.dp)
                            .clip(RoundedCornerShape(5.dp))
                            .background(color.value)
                            .size(35.dp)
                    ) {
                        Icon(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .size(20.dp),
                            tint = MaterialTheme.colors.sheetBackground,
                            painter = painterResource(id = icon.resIconId),
                            contentDescription = "icon"
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    val rgb = color.value.toArgb() and 0xffffff
                    val hex = String.format("#%06x", rgb).uppercase(Locale.ROOT)
                    Text(
                        text = hex,
                        fontWeight = FontWeight.Normal,
                        fontFamily = montserratFamily,
                        fontSize = 16.sp,
                        color = MaterialTheme.colors.sheetBackground,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
    }
}