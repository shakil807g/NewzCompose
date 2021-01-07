package com.shakil.newzcompose.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Providers
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import com.shakil.newzcompose.ui.NewzComposeTheme
import com.shakil.newzcompose.util.AmbientBackDispatcher
import com.shakil.newzcompose.util.SysUiController
import com.shakil.newzcompose.util.SystemUiController
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val headlinesViewModel: MainViewModel by viewModels()

    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            NewzComposeTheme {
                // A surface container using the 'background' color from the theme
                val systemUiController = remember { SystemUiController(window) }
                Providers(SysUiController provides systemUiController) {
                    Providers(AmbientBackDispatcher provides onBackPressedDispatcher) {
                        ProvideWindowInsets {
                            NewzMain(headlinesViewModel)
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NewzComposeTheme {
        // Greeting("Android")
    }
}