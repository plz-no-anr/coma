package plznoanr.coma

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import plznoanr.coma.core.ComaViewModel

class MainViewModel : ComaViewModel<UiState, Intent, SideEffect>() {

    override fun setInitialState(): UiState = UiState()

    init {
        intentFlow
            .handleIntent {
                when (it) {
                    is OnButtonClicked -> Unit
                    else -> postSideEffect { ShowError }
                }
            }
            .reduce {
                when (it) {
                    is OnButtonClicked -> copy(name = it.name)
                    else -> this
                }
            }.launchIn(viewModelScope)
    }
}