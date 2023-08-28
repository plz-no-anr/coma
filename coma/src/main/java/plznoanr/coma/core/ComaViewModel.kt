package plznoanr.coma.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber

abstract class ComaViewModel<UiState : ComaContract.State, in Intent : ComaContract.Intent, SideEffect : ComaContract.SideEffect>
    : ViewModel() {
    abstract fun setInitialState(): UiState

    protected abstract fun handleIntents(intent: Intent)

    private val initialState: UiState by lazy { setInitialState() }

    private val _state: MutableStateFlow<UiState> = MutableStateFlow(initialState)
    val state: StateFlow<UiState> = _state

    private val _intent: MutableSharedFlow<Intent> = MutableSharedFlow()

    private val _sideEffect: Channel<SideEffect> = Channel()
    val sideEffect = _sideEffect.receiveAsFlow()

    private val exceptionHandler by lazy {
        CoroutineExceptionHandler { _, t ->
            Timber.e(t)
        }
    }

    init {
        subscribeToIntents()
    }

    private fun subscribeToIntents() {
        viewModelScope.launch(exceptionHandler) {
            _intent.collect {
                handleIntents(it)
            }
        }
    }

    fun postIntent(intent: Intent) {
        viewModelScope.launch(exceptionHandler) { _intent.emit(intent) }
    }

    protected fun ViewModel.reduce(reducer: UiState.() -> UiState) {
        _state.value = state.value.reducer()
    }

    protected fun ViewModel.postSideEffect(builder: () -> SideEffect) {
        val effectValue = builder()
        viewModelScope.launch(exceptionHandler) { _sideEffect.send(effectValue) }
    }

}