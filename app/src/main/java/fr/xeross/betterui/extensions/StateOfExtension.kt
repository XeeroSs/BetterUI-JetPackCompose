package fr.xeross.betterui.extensions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

object ComposeExtension {


    @Composable
    fun <T> stateNullableOf(value: T?, defaultValueIfNull: T?): MutableState<T?> {
        return androidx.compose.runtime.remember {
            mutableStateOf(value ?: defaultValueIfNull)
        }
    }

    @Composable
    fun <T> stateOf(value: T?, defaultValueIfNull: T): MutableState<T> {
        return androidx.compose.runtime.remember {
            mutableStateOf(value ?: defaultValueIfNull)
        }
    }

    @Composable
    fun <T> stateOf(value: T): MutableState<T> {
        return androidx.compose.runtime.remember {
            mutableStateOf(value)
        }
    }

    @Composable
    fun <V,T> stateOfWith(with: V,value: T): MutableState<T> {
        return androidx.compose.runtime.remember(with) {
            mutableStateOf(value)
        }
    }

    @Composable
    fun <T> stateOf(value: () -> T): MutableState<T> {
        return androidx.compose.runtime.remember {
            mutableStateOf(value())
        }
    }


}