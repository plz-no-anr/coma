package plznoanr.coma.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.shareIn
import timber.log.Timber

abstract class ComaViewModel<UiState : ComaState, Intent : ComaIntent, SideEffect : ComaSideEffect>
    : ViewModel() {
    abstract fun setInitialState(): UiState

    private val initialState: UiState by lazy { setInitialState() }

    private val _state: MutableStateFlow<UiState> = MutableStateFlow(initialState)
    val state: StateFlow<UiState> = _state.asStateFlow()

    private val _intent: MutableSharedFlow<Intent> =
        MutableSharedFlow(extraBufferCapacity = Int.MAX_VALUE)

    protected val intentFlow: SharedFlow<Intent> = _intent
        .shareIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
        )

    private val _sideEffect: Channel<SideEffect> = Channel(Channel.UNLIMITED)
    val sideEffect = _sideEffect.receiveAsFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, t ->
        Timber.e(t)
    }

    protected fun Flow<Intent>.handleIntent(event: suspend (Intent) -> Unit): Flow<Intent> =
        onEach {
            event(it)
        }

    fun postIntent(intent: Intent) {
        _intent.tryEmit(intent)
    }

    protected fun <T> Flow<T>.reduce(reducer: UiState.(T) -> UiState): Flow<T> =
        onEach {
            _state.value = state.value.reducer(it)
        }

    protected fun postSideEffect(builder: () -> SideEffect) {
        val effectValue = builder()
        _sideEffect.trySend(effectValue)
    }

    override fun onCleared() {
        super.onCleared()
        _sideEffect.close()
        Timber.d("onCleared")
    }

}