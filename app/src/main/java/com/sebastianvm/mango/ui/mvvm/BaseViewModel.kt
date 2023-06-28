package com.sebastianvm.mango.ui.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

interface State
interface UserAction

abstract class BaseViewModel<S : State, A: UserAction>(
    initialState: S
) : ViewModel() {
    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<S> = _state

    abstract suspend fun onAction(action: A)

    fun handle(action: A) {
        viewModelScope.launch { onAction(action) }
    }

    // helper function to update state
    internal fun setState(update: S.() -> S) {
        _state.value = _state.value.update()
    }
}