package com.sebastianvm.mango.ui.mvvm

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

interface State
interface UserAction

abstract class BaseViewModel<S : State, A : UserAction>(
    initialState: S
) : ViewModel() {
    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<S> = _state

    abstract fun handle(action: A)

    // helper function to update state
    protected fun setState(update: S.() -> S) {
        _state.update(update)
    }
}
