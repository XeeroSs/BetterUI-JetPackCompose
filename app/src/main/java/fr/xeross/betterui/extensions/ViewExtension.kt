package fr.xeross.betterui.extensions

import android.view.View
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ViewExtension {

    fun Int.toDp() = this * 3


    fun View.antiSpam(delay: Long) {
        this.isEnabled = false
        // Start task with coroutines
        CoroutineScope(Dispatchers.Main).launch {
            delay(delay)

            this@antiSpam.isEnabled = true
        }
    }

}