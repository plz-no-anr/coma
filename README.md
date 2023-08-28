# Coma
COmpose Mvi for Android

[![Maven Central](https://img.shields.io/maven-central/v/io.github.plz-no-anr/coma.svg)](https://central.sonatype.com/artifact/io.github.plz-no-anr/coma)
[![API](https://img.shields.io/badge/API-23%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=23)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)



### 1. Add the mavenCentral() on project level(root level) build.gradle file:
``` gradle
allprojects {
    repositories {
        mavenCentral()
    }
}
```

### 2. Add dependency on module level build.gradle file:

#### build.gradle
``` groovy
dependencies {
    implementation 'io.github.plz-no-anr:coma:latestVersion'
}
```

#### build.gradle.kts
``` kotlin dsl
dependencies {
    implementation("io.github.plz-no-anr:coma:$latestVersion")
}
```

## Getting Started

#### 1. create new Contract
``` kotlin
class MainContract {

    data class State( // This is the ui data to show to the user
        val isLoading: Boolean = false,
        val name: String? = null,
    ) : ComaContract.State

    interface Intent : ComaContract.Intent { // This is an event received from the user.

        data class ShowName(val name: String) : Intent 

    }

    interface SideEffect : ComaContract.SideEffect {
    // This is a side effect passed on to the user. (show Snackbar, open External Browser)
        object ShowError : SideEffect

    }

}
```

#### 2. ViewModel

``` kotlin
class MainViewModel: ComaViewModel<MainContract.State, MainContract.Intent, MainContract.SideEffect>() {

    override fun setInitialState(): MainContract.State = MainContract.State() // Initail UIState

    override fun handleIntents(intent: MainContract.Intent) = when (intent) { // Handling Intent
        is MainContract.Intent.ShowName -> showName(intent.name) 
        else -> {}
    }

    private fun showName(name: String) {
        reduce { copy(name = name) } // reduce is a function that changes the state of the ui
    }

}
```

#### 3. View

``` kotlin
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                   val state by viewModel.state.collectAsState()
                   state.value.name?.let { UserNameView(it) } // set UI

                    Button(onClick = { viewModel.postIntent(MainContract.Intent.ShowName("")) }) {
                        Text(text = "Show Name")
                    }
                }
            }
        }
    }
}
```



## License

Coma is released under the MIT license. <a href="https://github.com/plz-no-anr/coma/blob/main/LICENSE">See LICENSE</a> for details.

