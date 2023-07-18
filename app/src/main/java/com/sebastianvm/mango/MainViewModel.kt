package com.sebastianvm.mango

import com.sebastianvm.mango.data.TaxRepository
import com.sebastianvm.mango.ui.mvvm.BaseViewModel
import com.sebastianvm.mango.ui.mvvm.State
import com.sebastianvm.mango.ui.mvvm.UserAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


object MainState : State

sealed interface MainUserAction : UserAction
object InitDatabase : MainUserAction


@HiltViewModel
class MainViewModel @Inject constructor(private val taxRepository: TaxRepository) : BaseViewModel<MainState, MainUserAction>(MainState) {

    override fun handle(action: MainUserAction) {
        when (action) {
            is InitDatabase -> {
                vmScope.launch {
                    taxRepository.populateTaxes()
                }
            }
        }
    }
}