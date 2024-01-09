package plznoanr.coma

import plznoanr.coma.core.ComaIntent
import plznoanr.coma.core.ComaSideEffect
import plznoanr.coma.core.ComaState

data class UiState(
    val isLoading: Boolean = false,
    val name: String? = null,
) : ComaState

interface Intent : ComaIntent {

    data class ShowName(val name: String) : Intent

}

interface SideEffect : ComaSideEffect {

    object ShowError : SideEffect

}