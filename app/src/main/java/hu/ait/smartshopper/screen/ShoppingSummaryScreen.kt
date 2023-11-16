package hu.ait.smartshopper.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingSummaryScreen(
    modifier: Modifier = Modifier,
    shoppingListViewModel: ShoppingListViewModel = hiltViewModel()
) {
    val shoppingList by
    shoppingListViewModel.getAllShoppingList().collectAsState(emptyList())

    Column() {
        TopAppBar(
            title = { Text("Shopping List Summary") },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer)
        )
    }
}