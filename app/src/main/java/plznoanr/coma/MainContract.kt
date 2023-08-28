package plznoanr.coma

import plznoanr.coma.core.ComaContract


class MainContract {
    data class State(
        val isLoading: Boolean = false,
        val name: String? = null,
    ) : ComaContract.State

    interface Intent : ComaContract.Intent {

        data class ShowName(val name: String) : Intent

    }

    interface SideEffect : ComaContract.SideEffect {

        object ShowError : SideEffect

    }

}