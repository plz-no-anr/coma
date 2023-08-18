package plznoanr.coma.core

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber

abstract class ComaViewModel<UiState : Contract.State, in Intent : Contract.Intent, SideEffect : Contract.SideEffect>
    : ViewModel() {
    abstract fun setInitialState(): UiState

    abstract fun handleIntents(intent: Intent)

    private val initialState: UiState by lazy { setInitialState() }

    private val _state: MutableState<UiState> = mutableStateOf(initialState)
    val state: State<UiState> = _state

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

    fun ViewModel.postIntent(intent: Intent) {
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