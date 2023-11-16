package hu.ait.smartshopper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import hu.ait.smartshopper.screen.ShoppingListScreen
import hu.ait.smartshopper.screen.ShoppingListViewModel
import hu.ait.smartshopper.screen.ShoppingSummaryScreen
import hu.ait.smartshopper.screen.SplashScreen
import hu.ait.smartshopper.ui.theme.SmartShopperTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SmartShopperTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SmartShopperNavHost()
                }
            }
        }
    }
}

@Composable
fun SmartShopperNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "splashscreen"
) {
    NavHost(
        modifier = modifier, navController = navController, startDestination = startDestination
    ) {
        composable("splashscreen") {
            SplashScreen(
                onNavigateToMain = {
                    navController.navigate("shoppinglist")
                }
            )
        }
        composable("shoppinglist") {
            ShoppingListScreen(
                onNavigateToSummary = {
                    navController.navigate("summaryscreen")
                }
            )
        }
        composable("summaryscreen") {
            ShoppingSummaryScreen()
        }
    }
}