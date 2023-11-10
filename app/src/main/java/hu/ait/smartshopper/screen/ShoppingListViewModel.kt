package hu.ait.smartshopper.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.ait.smartshopper.data.ShoppingDAO
import hu.ait.smartshopper.data.ShoppingItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingListViewModel @Inject constructor(
    private val shoppingDAO: ShoppingDAO
): ViewModel() {
    fun getAllShoppingList(): Flow<List<ShoppingItem>> {
        return shoppingDAO.getAllShoppingItems()
    }

    fun addToShoppingList(shoppingItem: ShoppingItem) {
        viewModelScope.launch {
            shoppingDAO.insert(shoppingItem)
        }
    }

    fun deleteFromShoppingList(shoppingItem: ShoppingItem) {
        viewModelScope.launch {
            shoppingDAO.delete(shoppingItem)
        }
    }

    fun editShoppingItem(shoppingItem: ShoppingItem) {
        viewModelScope.launch {
            shoppingDAO.update(shoppingItem)
        }
    }

    fun changeShoppingItemStatus(shoppingItem: ShoppingItem, status: Boolean) {
        val newShoppingItem = shoppingItem.copy()
        newShoppingItem.status = status
        viewModelScope.launch {
            shoppingDAO.update(newShoppingItem)
        }
    }

    fun deleteAllShoppingItems() {
        viewModelScope.launch {
            shoppingDAO.deleteAllShoppingItems()
        }
    }
}