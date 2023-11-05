package hu.ait.smartshopper.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingListScreen(
    modifier: Modifier = Modifier
) {
    Column() {
        TopAppBar(
            title = { Text("Smart Shopper") },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer),
            actions = {
                IconButton(
                    onClick = {}
                ) {
                    Icon(Icons.Filled.Delete, null)
                }
                IconButton(
                    onClick = {}
                ) {
                    Icon(Icons.Filled.Add, null)
                }
            }
        )
    }
}

//TopAppBar(
//title = {
//    Text("AIT Todo")
//},
//colors = TopAppBarDefaults.smallTopAppBarColors(
//containerColor = MaterialTheme.colorScheme.secondaryContainer
//),
//actions = {
//    IconButton(onClick = {
//        todoViewModel.clearAllTodos()
//    }) {
//        Icon(Icons.Filled.Delete, null)
//    }
//    IconButton(onClick = {
//        onNavigateToSummary(
//            todoViewModel.getAllTodoNum(),
//            todoViewModel.getImportantTodoNum()
//        )
//    }) {
//        Icon(Icons.Filled.Info, null)
//    }
//    IconButton(onClick = {
//        showAddTodoDialog = true
//    }) {
//        Icon(Icons.Filled.AddCircle, null)
//    }
//})