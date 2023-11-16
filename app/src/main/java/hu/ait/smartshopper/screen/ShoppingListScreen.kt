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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import hu.ait.smartshopper.data.ItemCategory
import hu.ait.smartshopper.data.ShoppingItem
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingListScreen(
    modifier: Modifier = Modifier,
    shoppingListViewModel: ShoppingListViewModel = hiltViewModel()
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
            title = { Text("Smart Shopper") },
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
                Text(text = "No items")
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
                onValueChange = {
                    shoppingItemTitle = it
                },
                label = { Text(text = "Enter item here:") }
            )

            // ItemCategory Spinner needs to be added here

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
                label = { Text(text = "Enter description here:") }
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = shoppingItemEstimatedPrice.toString(),
                onValueChange = {
                    shoppingItemEstimatedPrice = it.toIntOrNull() ?: 0
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                label = { Text(text = "Enter estimated price here:") }
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(checked = shoppingItemStatus, onCheckedChange = {
                    shoppingItemStatus = it
                })
                Text(text = "Got it!")
            }

            // This is where the to-do app had the Important click box.
//            Row(
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Checkbox(checked = todoImportant, onCheckedChange = {
//                    todoImportant = it
//                })
//                Text(text = "Important")
//            }
            Row {
                Button(
                    onClick = {
                        // This is where the Shopping Item is actually created.
                        // Currently the spinner is not implemented and I am hardcoding the item category as food everytime
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
                })
                {
                    Text(text = "Save")
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
                    Text(shoppingItem.title, modifier = Modifier.fillMaxWidth(0.35f),
                        textDecoration = TextDecoration.LineThrough)
                } else {
                    Text(shoppingItem.title, modifier = Modifier.fillMaxWidth(0.35f))
                }

                Spacer(modifier = Modifier.fillMaxSize(0.25f))
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
                    text = shoppingItem.estimatedPrice.toString(),
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
                        },
                    )
                }
            }
        }
    }
}