package com.myapp.spendless.di

import android.content.Context
import androidx.room.Room
import com.myapp.spendless.data.LocalData.SpendLessDao
import com.myapp.spendless.data.LocalData.SpendLessDatabase
import com.myapp.spendless.data.LocalData.TransactionDao
import com.myapp.spendless.data.LocalData.TransactionRepoImpl
import com.myapp.spendless.data.UserRepositoryImpl
import com.myapp.spendless.model.TransactionRepository
import com.myapp.spendless.model.UserRepository
import com.myapp.spendless.util.DataStoreManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideYourDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        SpendLessDatabase::class.java,
        SpendLessDatabase.TABLE
    ).fallbackToDestructiveMigration()
        .allowMainThreadQueries()
        .build()

    @Singleton
    @Provides
    fun provideYourDao(db: SpendLessDatabase) = db.getDao()

    @Singleton
    @Provides
    fun provideTransactionDao(db: SpendLessDatabase) = db.getTransactionDao()

    @Singleton
    @Provides
    fun providesRepository(dao: SpendLessDao): UserRepository {
        return UserRepositoryImpl(dao)
    }

    @Singleton
    @Provides
    fun providesTransactionRepository(daoTransaction: TransactionDao): TransactionRepository {
        return TransactionRepoImpl(daoTransaction)
    }

    @Provides
    @Singleton
    fun provideDataStoreManager(@ApplicationContext context: Context): DataStoreManager {
        return DataStoreManager(context)
    }
}