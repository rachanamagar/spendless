package com.myapp.spendless

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.myapp.spendless.model.User
import com.myapp.spendless.presentation.component.HomeScreen
import com.myapp.spendless.presentation.component.LoginScreen
import com.myapp.spendless.presentation.component.NewTransaction
import com.myapp.spendless.presentation.component.PinLoginScreen
import com.myapp.spendless.presentation.component.PinScreen
import com.myapp.spendless.presentation.component.WelcomeScreen
import com.myapp.spendless.presentation.viewmodels.TransactionViewModel
import com.myapp.spendless.presentation.viewmodels.UserViewmodel
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

                    NavHost(
                        navController = navController,
                        startDestination = "WelcomeScreen"
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

                        composable("LoginScreen") {
                            LoginScreen(navController) {}
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
                            "HomeScreen/{name}",
                            arguments = listOf(
                                navArgument("name") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val name = backStackEntry.arguments?.getString("name")
                            if (name != null) {
                                HomeScreen(name){
                                    navController.navigate("newTransactionScreen")
                                }
                            }
                        }

                        composable("newTransactionScreen"){
                            val viewModel: TransactionViewModel = hiltViewModel()
                            NewTransaction(){
                                viewModel.insertTransaction()
                                navController.popBackStack()

                            }
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