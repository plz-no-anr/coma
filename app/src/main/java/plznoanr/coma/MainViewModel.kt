package plznoanr.coma

import plznoanr.coma.core.ComaViewModel

class MainViewModel: ComaViewModel<UiState, Intent, SideEffect>() {
    override fun setInitialState(): UiState = UiState()

    override fun handleIntents(intent: Intent) = when (intent) {
        is Intent.ShowName -> showName(intent.name)
        else -> postSideEffect { SideEffect.ShowError }
    }

    private fun showName(name: String) {
        reduce { copy(name = name) }
    }

}