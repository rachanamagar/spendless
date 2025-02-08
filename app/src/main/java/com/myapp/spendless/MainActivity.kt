package com.myapp.spendless

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.myapp.spendless.presentation.component.PinList
import com.myapp.spendless.presentation.component.PinScreen
import com.myapp.spendless.presentation.component.WelcomeScreen
import com.myapp.spendless.ui.theme.SpendlessTheme
import com.myapp.spendless.ui.theme.SurfaceBackground

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SpendlessTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = SurfaceBackground
                ) {

                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = "WelcomeScreen"
                    ) {

                        composable("WelcomeScreen") {
                            WelcomeScreen { navController.navigate("PinScreen")}
                        }

                        composable("PinScreen"){
                            PinScreen(navController)
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
    SpendlessTheme {
        Greeting("Android")
    }
}