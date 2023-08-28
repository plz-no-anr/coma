package plznoanr.coma

import plznoanr.coma.core.ComaViewModel

class MainViewModel: ComaViewModel<MainContract.State, MainContract.Intent, MainContract.SideEffect>() {
    override fun setInitialState(): MainContract.State = MainContract.State()

    override fun handleIntents(intent: MainContract.Intent) = when (intent) {
        is MainContract.Intent.ShowName -> showName(intent.name)
        else -> {}
    }

    private fun showName(name: String) {
        reduce { copy(name = name) }
    }

}