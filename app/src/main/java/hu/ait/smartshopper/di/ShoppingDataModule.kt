package hu.ait.smartshopper.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import hu.ait.smartshopper.data.ShoppingDAO
import hu.ait.smartshopper.data.SmartShopperDatabase
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    fun provideShoppingDao(appDatabase: SmartShopperDatabase): ShoppingDAO {
        return appDatabase.shoppingDao()
    }

    @Provides
    @Singleton
    fun provideSmartShopperDatabase(@ApplicationContext appContext: Context): SmartShopperDatabase {
        return SmartShopperDatabase.getDatabase(appContext)
    }
}