package com.sebastianvm.mango.ui.navigation

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle

fun <T : Parcelable> SavedStateHandle.getArgs(): T {
    return get(ARGS)!!
}