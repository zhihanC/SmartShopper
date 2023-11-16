package hu.ait.smartshopper.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingDAO {
    @Query("SELECT * from shoppingtable")
    fun getAllShoppingItems(): Flow<List<ShoppingItem>>

    @Query("SELECT * from shoppingtable WHERE id = :id")
    fun getShoppingItem(id: Int): Flow<ShoppingItem>

//    @Query("SELECT COUNT(*) from shoppingtable")
//    suspend fun getShoppingItemsNum(): Int

    @Query("""SELECT COUNT(*) from shoppingtable WHERE category="FOOD"""")
    suspend fun getFoodItems(): Int

    @Query("""SELECT COUNT(*) from shoppingtable WHERE category="HEALTH"""")
    suspend fun getHealthItems(): Int

    @Query("""SELECT COUNT(*) from shoppingtable WHERE category="CLOTHES"""")
    suspend fun getClothesItems(): Int

    @Query("""SELECT COUNT(*) from shoppingtable WHERE category="ELECTRONICS"""")
    suspend fun getElectronicsItems(): Int

    @Query("""SELECT COUNT(*) from shoppingtable WHERE category="CLEANING"""")
    suspend fun getCleaningItems(): Int

    @Query("""SELECT COUNT(*) from shoppingtable WHERE category="RECREATION"""")
    suspend fun getRecreationItems(): Int

    @Query("""SELECT COUNT(*) from shoppingtable WHERE category="MISC"""")
    suspend fun getMiscItems(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(todo: ShoppingItem)

    @Update
    suspend fun update(todo: ShoppingItem)

    @Delete
    suspend fun delete(todo: ShoppingItem)

    @Query("DELETE from shoppingtable")
    suspend fun deleteAllShoppingItems()
}