package hu.ait.smartshopper.screen

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import hu.ait.smartshopper.R
import hu.ait.smartshopper.data.ItemCategory
import hu.ait.smartshopper.data.ShoppingItem
import kotlinx.coroutines.launch
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingListScreen(
    modifier: Modifier = Modifier,
    shoppingListViewModel: ShoppingListViewModel = hiltViewModel(),
    onNavigateToSummary: (Int, Int, Int, Int, Int, Int ,Int) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    val shoppingList by
    shoppingListViewModel.getAllShoppingList().collectAsState(emptyList())

    var showAddShoppingItemDialog by rememberSaveable {
        mutableStateOf(false)
    }

    var shoppingItemToEdit: ShoppingItem? by rememberSaveable {
        mutableStateOf(null)
    }

    Column() {
        TopAppBar(
            title = { Text(stringResource(R.string.smart_shopper_title)) },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer),
            actions = {
                IconButton(
                    onClick = {
                        shoppingListViewModel.deleteAllShoppingItems()
                    }
                ) {
                    Icon(Icons.Filled.Delete, null)
                }
                IconButton(
                    onClick = {
                        showAddShoppingItemDialog = true
                        shoppingItemToEdit = null
                    }
                ) {
                    Icon(Icons.Filled.Add, null)
                }
                IconButton(onClick = {
                    coroutineScope.launch {
                        val foodItemsNum = shoppingListViewModel.getFoodItems()
                        val healthItemsNum = shoppingListViewModel.getHealthItems()
                        val clothesItemsNum = shoppingListViewModel.getClothesItems()
                        val electronicsItemsNum = shoppingListViewModel.getElectronicsItems()
                        val cleaningItemsNum = shoppingListViewModel.getCleaningItems()
                        val recreationItemsNum = shoppingListViewModel.getRecreationItems()
                        val miscItemsNum = shoppingListViewModel.getMiscItems()
                        onNavigateToSummary(
                            foodItemsNum,
                            healthItemsNum,
                            clothesItemsNum,
                            electronicsItemsNum,
                            cleaningItemsNum,
                            recreationItemsNum,
                            miscItemsNum
                        )
                    }
                }) {
                    Icon(Icons.Filled.Info, null)
                }
            }
        )
        Column(modifier = modifier.padding(10.dp)) {
            if (showAddShoppingItemDialog) {
                AddNewShoppingItemForm(shoppingListViewModel,
                    { showAddShoppingItemDialog = false },
                    shoppingItemToEdit
                )
            }

            if (shoppingList.isEmpty())
                Text(text = stringResource(R.string.no_items))
            else {
                LazyColumn(modifier = Modifier.fillMaxHeight()) {
                    items(shoppingList) {
                        ShoppingItemCard(
                            shoppingItem = it,
                            onRemoveItem = { shoppingListViewModel.deleteFromShoppingList(it) },
                            onShoppingItemCheckChange = { checkState ->
                                shoppingListViewModel.changeShoppingItemStatus(it, checkState)
                            },
                            onEditItem = { editedShoppingItem ->
                                shoppingItemToEdit = editedShoppingItem
                                showAddShoppingItemDialog = true
                            })
                    }
                }
            }
        }
    }
}


@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun AddNewShoppingItemForm(
    shoppingListViewModel: ShoppingListViewModel,
    onDialogDismiss: () -> Unit = {},
    shoppingItemToEdit: ShoppingItem? = null
) {
    var context = LocalContext.current
    
    Dialog(
        onDismissRequest = onDialogDismiss
    ) {
        var shoppingItemTitle by rememberSaveable {
            mutableStateOf(shoppingItemToEdit?.title?: "")
        }

        var shoppingItemCategory by rememberSaveable {
            mutableStateOf(shoppingItemToEdit?.category?: ItemCategory.FOOD)
        }

        var shoppingItemDescription by rememberSaveable {
            mutableStateOf(shoppingItemToEdit?.description?: "")
        }

        var shoppingItemEstimatedPrice by rememberSaveable {
            mutableStateOf(shoppingItemToEdit?.estimatedPrice?: 0)
        }

        var shoppingItemStatus by rememberSaveable {
            mutableStateOf(shoppingItemToEdit?.status?: false)
        }

        var titleErrorText by rememberSaveable {
            mutableStateOf("")
        }

        var titleInputErrorState by rememberSaveable {
            mutableStateOf(false)
        }

        fun validateTitle(text: String) {
            val isBlank = text.isBlank()
            titleErrorText = context.getString(R.string.please_enter_a_title)
            titleInputErrorState = isBlank
        }

        var amountInputErrorState by rememberSaveable {
            mutableStateOf(false)
        }

        var amountErrorText by rememberSaveable {
            mutableStateOf("")
        }

        fun validateAmount(text: String) {
            val allDigit = text.all{char -> char.isDigit()}
            amountErrorText = context.getString(R.string.estimated_price_cannot_be_0)
            if (text == "0") {
                amountInputErrorState = true
            }
        }

        Column(
            Modifier
                .background(
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    shape = MaterialTheme.shapes.medium
                )
                .padding(10.dp)
        ) {

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = shoppingItemTitle,
                isError = titleInputErrorState,
                onValueChange = {
                    shoppingItemTitle = it
                    validateTitle(shoppingItemTitle)
                },
                label = { Text(text = stringResource(R.string.enter_item_here)) },
                singleLine = true,
                supportingText = {
                    if (titleInputErrorState)
                        Text(
                            text = titleErrorText,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.labelSmall,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                },
                trailingIcon = {
                    if (titleInputErrorState) {
                        Icon(
                            Icons.Filled.Warning, "error",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )

            Spinner(
                listOf(ItemCategory.FOOD, ItemCategory.HEALTH,
                    ItemCategory.CLOTHES, ItemCategory.ELECTRONICS,
                    ItemCategory.CLEANING, ItemCategory.RECREATION,
                    ItemCategory.MISC),
                preselected = shoppingItemCategory,
                onSelectionChanged = {
                    myData -> shoppingItemCategory = myData
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = shoppingItemDescription,
                onValueChange = {
                    shoppingItemDescription = it
                },
                label = { Text(text = stringResource(R.string.enter_description_here)) },
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = shoppingItemEstimatedPrice.toString(),
                isError = amountInputErrorState,
                onValueChange = {
                    shoppingItemEstimatedPrice = it.toIntOrNull() ?: 0
                    validateAmount(shoppingItemEstimatedPrice.toString())
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                label = { Text(text = stringResource(R.string.enter_estimated_price_here)) },
                singleLine = true,
                supportingText = {
                    if (amountInputErrorState)
                        Text(
                            text = amountErrorText,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.labelSmall,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                },
                trailingIcon = {
                    if (amountInputErrorState) {
                        Icon(
                            Icons.Filled.Warning, "error",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(checked = shoppingItemStatus, onCheckedChange = {
                    shoppingItemStatus = it
                })
                Text(text = stringResource(R.string.got_it))
            }

            Row {
                Button(
                    onClick = {
                        if (shoppingItemTitle.isEmpty()) {
                            titleErrorText = context.getString(R.string.title_cannot_be_empty)
                            titleInputErrorState = true
                        } else if (shoppingItemEstimatedPrice.toString() == "0") {
                            amountErrorText = context.getString(R.string.estimated_price_cannot_be_0)
                            amountInputErrorState = true
                        } else {
                            if (shoppingItemToEdit == null) {
                                shoppingListViewModel.addToShoppingList(
                                    ShoppingItem(
                                        0,
                                        shoppingItemTitle,
                                        shoppingItemCategory,
                                        shoppingItemDescription,
                                        shoppingItemEstimatedPrice,
                                        shoppingItemStatus
                                    )
                                )
                            } else {
                                var shoppingItemEdited = shoppingItemToEdit.copy(
                                    title = shoppingItemTitle,
                                    category = shoppingItemCategory,
                                    description = shoppingItemDescription,
                                    estimatedPrice = shoppingItemEstimatedPrice,
                                    status = shoppingItemStatus
                                )
                                shoppingListViewModel.editShoppingItem(shoppingItemEdited)
                            }
                            onDialogDismiss()
                        }
                    })
                {
                    Text(text = stringResource(R.string.save))
                }
            }
        }
    }
}

@Composable
fun ShoppingItemCard(
    shoppingItem: ShoppingItem,
    onShoppingItemCheckChange: (Boolean) -> Unit = {},
    onRemoveItem: () -> Unit = {},
    onEditItem: (ShoppingItem) -> Unit = {}
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        modifier = Modifier.padding(5.dp)
    ) {
        var expanded by rememberSaveable {
            mutableStateOf(false)
        }

        Column(
            modifier = Modifier
                .padding(20.dp)
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio =
                        Spring.DampingRatioHighBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                )
        ) {
            Row(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = shoppingItem.category.getIcon()),
                    contentDescription = "ItemCategory",
                    modifier = Modifier
                        .size(40.dp)
                        .padding(end = 10.dp)
                )

                if (shoppingItem.status) {
                    Text(shoppingItem.title, modifier = Modifier.fillMaxWidth(0.3f),
                        textDecoration = TextDecoration.LineThrough)
                } else {
                    Text(shoppingItem.title, modifier = Modifier.fillMaxWidth(0.3f))
                }

                Spacer(modifier = Modifier.fillMaxSize(0.2f))
                Checkbox(
                    checked = shoppingItem.status,
                    onCheckedChange = { onShoppingItemCheckChange(it) }
                )
                Spacer(modifier = Modifier.width(5.dp))
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Delete",
                    modifier = Modifier.clickable {
                        onRemoveItem()
                    },
                    tint = Color.Red
                )
                Spacer(modifier = Modifier.width(5.dp))
                Icon(
                    imageVector = Icons.Filled.Build,
                    contentDescription = "Edit",
                    modifier = Modifier.clickable {
                        onEditItem(shoppingItem)
                    },
                    tint = Color.Blue
                )
                Spacer(modifier = Modifier.width(5.dp))
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = if (expanded)
                            Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                        contentDescription = if (expanded) {
                            "Less"
                        } else {
                            "More"
                        }
                    )
                }
            }

            if (expanded) {
                Text(
                    text = shoppingItem.description,
                    style = TextStyle(
                        fontSize = 12.sp,
                    )
                )
                Text(
                    text = "$ " + shoppingItem.estimatedPrice.toString(),
                    style = TextStyle(
                        fontSize = 12.sp,
                    )
                )
            }
        }
    }
}

@Composable
fun Spinner(
    list: List<ItemCategory>,
    preselected: ItemCategory,
    onSelectionChanged: (myData: ItemCategory) -> Unit,
    modifier: Modifier = Modifier
) {
    var selected by remember { mutableStateOf(preselected) }
    var expanded by remember { mutableStateOf(false) } // initial value
    OutlinedCard(
        modifier = modifier.clickable {
            expanded = !expanded
        }
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top,
        ) {
            Text(
                text = selected.toString(),
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
            Icon(Icons.Outlined.ArrowDropDown, null, modifier =
            Modifier.padding(8.dp))
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                list.forEach { listEntry ->
                    DropdownMenuItem(
                        onClick = {
                            selected = listEntry
                            expanded = false
                            onSelectionChanged(selected)
                        },
                        text = {
                            Text(
                                text = listEntry.toString(),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.Start)
                            )
                        }
                    )
                }
            }
        }
    }
}