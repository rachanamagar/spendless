package com.myapp.spendless

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.myapp.spendless.feature.HomeScreen.presentation.ui.HomeScreen
import com.myapp.spendless.feature.HomeScreen.presentation.ui.ListOfTransaction
import com.myapp.spendless.feature.LoginScreen.presentation.LoginScreen
import com.myapp.spendless.feature.HomeScreen.presentation.component.NewTransaction
import com.myapp.spendless.feature.LoginScreen.presentation.PinLoginScreen
import com.myapp.spendless.feature.Registration.presentation.PinScreen
import com.myapp.spendless.feature.Registration.presentation.WelcomeScreen
import com.myapp.spendless.feature.Setting.presentation.SecurityScreen
import com.myapp.spendless.feature.Setting.presentation.SessionViewModel
import com.myapp.spendless.feature.Setting.SettingScreen
import com.myapp.spendless.feature.Setting.preference.SpendlessPreferenceScreen
import com.myapp.spendless.feature.HomeScreen.presentation.viewmodel.TransactionViewModel
import com.myapp.spendless.feature.Registration.presentation.UserViewmodel
import com.myapp.spendless.ui.theme.SpendlessTheme
import com.myapp.spendless.ui.theme.SurfaceBackground
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
                    val viewmodel: UserViewmodel = hiltViewModel()
                    val viewModelTransaction: TransactionViewModel = hiltViewModel()

                    val viewModelSession: SessionViewModel = hiltViewModel()
                    val state by viewModelSession.sessionState.collectAsStateWithLifecycle()

                    LaunchedEffect(state.isSessionExpire) {
                        if (state.isSessionExpire) {
                            navController.navigate("LoginScreen")
                        }
                    }
                    NavHost(
                        navController = navController,
                        startDestination = "LoginScreen"
                    ) {

                        composable("WelcomeScreen") {
                            WelcomeScreen(
                                viewmodel = viewmodel,
                                { navController.navigate("PinScreen") },
                                { navController.navigate("LoginScreen") })
                        }

                        composable("PinScreen") {
                            PinScreen(navController, viewmodel)
                        }

                        composable("LoginScreen") { entry ->
                            val pin = entry.savedStateHandle.get<String>("pinCode") ?: "Pin"
                            LoginScreen(navController, pin) {
                                navController.navigate("HomeScreen")
                            }
                        }

                        composable(
                            "PinLoginScreen/{name}",
                            arguments = listOf(
                                navArgument("name") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val name = backStackEntry.arguments?.getString("name")
                            if (name != null) {
                                PinLoginScreen(name, navController)
                            }
                        }

                        composable(
                            "HomeScreen"

                        ) {
                            HomeScreen(
                                { navController.navigate("settings") },
                                {navController.navigate("newTransactionScreen")}
                            ) {
                                navController.navigate("AllTransaction")

                            }
                        }

                        composable("newTransactionScreen") {
                            val viewModel: TransactionViewModel = hiltViewModel()
                            NewTransaction(
                                onCreateClicked = {
                                    viewModel.insertTransaction()
                                    navController.popBackStack()
                                }
                            ) {
                                navController.popBackStack()
                            }
                        }

                        composable("AllTransaction") {
                            ListOfTransaction { navController.popBackStack() }
                        }

                        composable("settings") {
                            SettingScreen({
                                navController.navigate("security")
                            },
                                { navController.navigate("preferences") },
                                { navController.popBackStack() }
                            ) {
                                viewmodel.logOutUser()
                                navController.navigate("LoginScreen")
                            }
                        }

                        composable("security") {
                            SecurityScreen({ navController.popBackStack() }){
                                navController.navigate("LoginScreen")
                            }
                        }

                        composable("preferences") {
                            SpendlessPreferenceScreen(
                                onSave = { viewModelTransaction.changeSymbol(it) },
                            )
                            { navController.navigate("HomeScreen") }
                        }
                    }
                }
            }
        }
    }
    override fun onResume() {
        super.onResume()
        Log.d("MainActivity", "onResume called - Activity in foreground")
    }

    override fun onPause() {
        super.onPause()
        Log.d("MainActivity", "onPause called - Activity in background")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MainActivity", "onDestroy called - Activity destroyed")
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