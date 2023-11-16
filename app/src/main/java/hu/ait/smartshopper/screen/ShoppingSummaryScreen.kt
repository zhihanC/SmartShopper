package hu.ait.smartshopper.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.multibindings.IntoMap
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingSummaryScreen(
    modifier: Modifier = Modifier,
    numFoodItems: Int,
    numHealthItems: Int,
    numClothesItems: Int,
    numElectronicsItems: Int,
    numCleaningItems: Int,
    numRecreationItems: Int,
    numMiscItems: Int
) {

    Column() {
        TopAppBar(
            title = { Text("Shopping List Summary") },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer)
        )
        Column(modifier = modifier.fillMaxSize()
            .padding(start = 40.dp, end = 40.dp), verticalArrangement = Arrangement.SpaceEvenly){

            var totalItems = numFoodItems + numHealthItems + numClothesItems +
                    numElectronicsItems + numCleaningItems + numRecreationItems + numMiscItems

            Text(text = "On your shopping list, there are a total of ${totalItems} items. There are:")
            Text(text= "${numFoodItems} food items")
            Text(text= "${numHealthItems} health items")
            Text(text= "${numClothesItems} clothes items")
            Text(text= "${numElectronicsItems} electronics items")
            Text(text= "${numCleaningItems} cleaning items")
            Text(text= "${numRecreationItems} recreation items")
            Text(text= "${numMiscItems} misc items")
        }
    }
}
