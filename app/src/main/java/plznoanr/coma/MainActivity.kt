package plznoanr.coma

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.onEach
import plznoanr.coma.ui.theme.ComaTheme

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by lazy {
        MainViewModel()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComaTheme {
                LaunchedEffect(Unit) {
                    viewModel.sideEffect.onEach {
                        when (it) {
                            is ShowError -> {
                                // Show error Snackbar
                            }
                        }
                    }
                }

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val state by viewModel.state.collectAsState()
                    val onIntent: (Intent) -> Unit = { viewModel.postIntent(it) }

                    Column {
                        state.name?.let { Greeting(it) }

                        Spacer(modifier = Modifier.size(20.dp))
                        Button(
                            modifier = Modifier.size(200.dp),
                            onClick = { onIntent(OnButtonClicked("Steve")) }) {
                            Text(text = "Show Name")
                        }
                    }


                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComaTheme {
        Greeting("Android")
    }
}