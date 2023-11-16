package hu.ait.smartshopper.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import hu.ait.smartshopper.R
import java.io.Serializable

@Entity(tableName = "shoppingtable")
data class ShoppingItem(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "category") var category: ItemCategory,
    @ColumnInfo(name = "description") var description: String,
    @ColumnInfo(name = "estimatedprice") var estimatedPrice: Int,
    @ColumnInfo(name = "status") var status: Boolean
): Serializable

enum class ItemCategory {
    FOOD, HEALTH, CLOTHES, ELECTRONICS, CLEANING, RECREATION, MISC;

    fun getIcon(): Int {
        return when (this) {
            FOOD -> R.drawable.food
            HEALTH -> R.drawable.health
            CLOTHES -> R.drawable.clothes
            ELECTRONICS -> R.drawable.electronics
            CLEANING -> R.drawable.cleaning
            RECREATION -> R.drawable.recreation
            MISC -> R.drawable.misc
        }
    }
}
// Used Icons:
// Healthy Food by FBJan:
// <a href="https://www.flaticon.com/free-icons/nutrition" title="nutrition icons">Nutrition icons created by FBJan - Flaticon</a>
// Heartbeat by Freepik:
// <a href="https://www.flaticon.com/free-icons/heartbeat" title="heartbeat icons">Heartbeat icons created by Freepik - Flaticon</a>
// Clothes by Freepik:
// <a href="https://www.flaticon.com/free-icons/fashion" title="fashion icons">Fashion icons created by Freepik - Flaticon</a>
// Responsive by xnimrodx:
// <a href="https://www.flaticon.com/free-icons/electronics" title="electronics icons">Electronics icons created by xnimrodx - Flaticon</a>
// Basket by Smashicons:
// <a href="https://www.flaticon.com/free-icons/cleaning" title="cleaning icons">Cleaning icons created by Smashicons - Flaticon</a>
// Laugh by Freepik:
// <a href="https://www.flaticon.com/free-icons/fun" title="fun icons">Fun icons created by Freepik - Flaticon</a>
// Surprise Box by noomtah:
// <a href="https://www.flaticon.com/free-icons/random" title="random icons">Random icons created by noomtah - Flaticon</a>

