package com.sebastianvm.mango.ui.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

interface State
interface UserAction

abstract class BaseViewModel<S : State, A : UserAction>(
    initialState: S
) : ViewModel() {
    private val _state = MutableStateFlow(initialState)
    val stateFlow: StateFlow<S> = _state
    val state: S
        get() = _state.value

    // In tests, vmScope should be set to an instance of TestScope
    var vmScope = viewModelScope

    abstract fun handle(action: A)

    // helper function to update state
    protected fun setState(update: S.() -> S) {
        _state.update(update)
    }
}
