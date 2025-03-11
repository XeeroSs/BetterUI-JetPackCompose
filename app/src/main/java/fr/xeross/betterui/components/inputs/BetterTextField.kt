package fr.xeross.betterui.components.inputs

import android.R.attr.singleLine
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.xeross.betterui.R
import fr.xeross.betterui.extensions.ColorExtension.lighter
import fr.xeross.betterui.extensions.ColorExtension.transparent

/**
 * @version 1.0.0
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BetterTextField(
    modifier: Modifier =  Modifier.height(60.dp),
    enabled: Boolean = true,

    value: String,
    label: String,
    mutableState: MutableState<String>? = null,

    icons: Array<BetterIconTextField> = emptyArray(),

    colorBorder: Color,
    colorDefault: TextFieldColors = TextFieldDefaults.colors(
        unfocusedIndicatorColor = Color.Transparent,
    ),

    textStyle: TextStyle = TextStyle.Default,
    labelStyle: TextStyle = TextStyle.Default,
    shape: Shape = RoundedCornerShape(40f),

    default: BetterTextFieldDefault = BetterTextFieldDefault(),
    contentPadding : PaddingValues = PaddingValues(10.dp),

    onValueChange: (String) -> Unit,
) {
    var text = mutableState ?: remember { mutableStateOf(value) }
    val interactionSource = remember { MutableInteractionSource() }
    val focusRequester = remember { FocusRequester() }
    var isFocused by remember { mutableStateOf(false) }

    val leadingIcons = icons.filter { !it.isTraining() }.ifEmpty { null }
    val trainingIcons = icons.filter { it.isTraining() }.ifEmpty { null }

    BasicTextField(
        value = text.value,
        keyboardOptions = default.keyboardOptions,
        modifier = if (!isFocused) modifier
            .clip(shape)
            .fillMaxWidth()
            .focusRequester(focusRequester)
            .onFocusChanged {
                isFocused = it.isFocused
            }
        else modifier
            .clip(shape)
            .fillMaxWidth()
            .focusRequester(focusRequester)
            .onFocusChanged {
                isFocused = it.isFocused
            }
            .border(2.dp, colorBorder, shape),
        onValueChange = {
            val keyboard = default.keyboardOptions.keyboardType
            if (keyboard == KeyboardType.Number || keyboard == KeyboardType.NumberPassword
                || keyboard == KeyboardType.Phone || keyboard == KeyboardType.Decimal
            ) {
                if (text.value.length < it.length) {
                    if (it.length < default.limit || it.isEmpty() || it.matches(Regex("^\\d*([.,]\\d{1,2})?$"))) {
                        onValueChange(it)
                        text.value =
                            it
                    }
                    return@BasicTextField
                }
                onValueChange(it)
                text.value = it
            } else {
                onValueChange(it)
                text.value = it
            }
        },
        textStyle =textStyle,
        singleLine = default.singleLine,
        interactionSource = interactionSource,
        visualTransformation = default.visualTransformation,
        cursorBrush = default.cursorBrush,
        keyboardActions = default.keyboardActions,
        onTextLayout = default.onTextLayout,
        readOnly = default.readOnly,
    ) { innerTextField ->
        TextFieldDefaults.DecorationBox(
            colors = colorDefault,
            trailingIcon = trainingIcons?.let {
                {
                 Row(verticalAlignment = Alignment.CenterVertically) {
                        trainingIcons.forEach {
                            if (it.onClick() == null) {
                                    Icon(
                                        modifier = Modifier
                                            .size(it.size()),
                                        painter = painterResource(id = it.resIconId()),
                                        tint = it.color(), contentDescription = ""
                                    )
                            } else {
                                IconButton(onClick = { it.onClick() }) {
                                    Icon(
                                        modifier = Modifier
                                            .size(it.size()),
                                        painter = painterResource(id = it.resIconId()),
                                        tint = it.color(), contentDescription = ""
                                    )
                                }
                            }
                        }
                    }
                }
            },
            leadingIcon = leadingIcons?.let {
                @Composable {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        leadingIcons.forEach {
                            if (it.onClick() == null) {
                                Icon(
                                    modifier = Modifier
                                        .size(it.size()),
                                    painter = painterResource(id = it.resIconId()),
                                    tint = it.color(), contentDescription = ""
                                )
                            } else {
                                IconButton(onClick = { it.onClick() }) {
                                    Icon(
                                        modifier = Modifier
                                            .size(it.size()),
                                        painter = painterResource(id = it.resIconId()),
                                        tint = it.color(), contentDescription = ""
                                    )
                                }
                            }
                        }
                    }
                }
            },
            value = "",
            label = {
                @Composable {
                    Text(
                        text = if (text.value.isEmpty() && !isFocused) label else "",
                        fontWeight = labelStyle.fontWeight,
                        fontSize = labelStyle.fontSize,
                        color = labelStyle.color,
                    )
                }
            },
            contentPadding =contentPadding,
            visualTransformation = default.visualTransformation,
            innerTextField = innerTextField,
            singleLine = default.singleLine,
            enabled = enabled,
            interactionSource = interactionSource
        )
    }
}

data class BetterTextFieldDefault(
    var keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    var keyboardActions: KeyboardActions = KeyboardActions.Default,
    var limit: Int = 1000,
    var singleLine: Boolean = true,
    var readOnly: Boolean = false,
    var maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    var minLines: Int = 1,
    var visualTransformation: VisualTransformation = VisualTransformation.None,
    var onTextLayout: (TextLayoutResult) -> Unit = {},
    var cursorBrush: Brush = SolidColor(Color.Black),
    var decorationBox: @Composable (innerTextField: @Composable () -> Unit) -> Unit =
        @Composable { innerTextField -> innerTextField() }
)

interface BetterIconTextField {
    fun resIconId(): Int
    fun onClick(): (() -> Unit)? = null
    @Composable
    fun color(): Color = Color.Gray
    fun size(): Dp = 30.dp
    fun isTraining(): Boolean = true
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = false)
@Composable
private fun BetterTextFieldPreview() {
    val text = remember { mutableStateOf("") }
    Column(modifier = Modifier.padding(10.dp))
    {
        BetterTextField(
            value = text.value,
            label = "Hint",
            colorDefault = TextFieldDefaults.colors(
                unfocusedIndicatorColor = Color.Transparent,
                unfocusedLabelColor = Color.Yellow,
            ),
            textStyle = TextStyle(
                color = Color.Red,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
            ),
            labelStyle = TextStyle(fontSize = 30.sp,),
            colorBorder = Color.Blue,
            icons = arrayOf(
                object : BetterIconTextField {
                    override fun resIconId(): Int = R.drawable.ic_add
                    override fun onClick() = null
                    @Composable
                    override fun color() = Color.Red
                    override fun size(): Dp = 30.dp
                    override fun isTraining(): Boolean = false
                },
                object : BetterIconTextField {
                    override fun resIconId(): Int = R.drawable.ic_check
                    override fun onClick() = null
                    @Composable
                    override fun color(): Color = Color.Green
                    override fun size(): Dp = 30.dp
                    override fun isTraining(): Boolean = true
                },
                object : BetterIconTextField {
                    override fun resIconId(): Int = R.drawable.ic_add
                    override fun onClick(): (() -> Unit)? = { text.value = "" }
                    @Composable
                    override fun color(): Color = Color.Blue
                    override fun size(): Dp = 30.dp
                    override fun isTraining(): Boolean = true
                },
            ),
        ) { }

        Spacer(modifier = Modifier.height(20.dp))

        BetterTextField(
            shape = RoundedCornerShape(100),
            modifier = Modifier.height(50.dp),
            value = text.value,
            label = "Search",
            colorDefault = TextFieldDefaults.colors(
                unfocusedContainerColor = Color(0xFFE6EDF5).transparent(90f),
                focusedContainerColor = Color(0xFFE6EDF5).transparent(90f),
                focusedTextColor = MaterialTheme.colorScheme.onBackground,
                unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                disabledLabelColor = MaterialTheme.colorScheme.primary.transparent(30f),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            textStyle = TextStyle(
                color = Color.Red,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
            ),
            labelStyle = TextStyle(fontSize = 20.sp,),
            colorBorder = Color.Blue.lighter(0.5f).transparent(50f),
            icons = arrayOf(
                object : BetterIconTextField {
                    override fun resIconId(): Int = R.drawable.ic_add
                    override fun onClick() = null
                    @Composable
                    override fun color(): Color = MaterialTheme.colorScheme.primary
                    override fun size(): Dp = 20.dp
                    override fun isTraining(): Boolean = false
                },
                object : BetterIconTextField {
                    override fun resIconId(): Int = R.drawable.ic_check
                    override fun onClick() = null
                    @Composable
                    override fun color(): Color = Color.Gray
                    override fun size(): Dp = 20.dp
                    override fun isTraining(): Boolean = true
                },
                object : BetterIconTextField {
                    override fun resIconId(): Int = R.drawable.ic_add
                    override fun onClick(): (() -> Unit)? = { text.value = "" }
                    @Composable
                    override fun color(): Color = Color.Gray
                    override fun size(): Dp = 20.dp
                    override fun isTraining(): Boolean = true
                },
            ),
        ) { }
    }
}