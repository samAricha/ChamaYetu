package teka.android.chamaaapp.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import teka.android.chamaaapp.data.local.repository.DbRepository
import teka.android.chamaaapp.data.local.room.ChamaaMainDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideChamaaDatabase(@ApplicationContext context: Context): ChamaaMainDatabase {
        return Room.databaseBuilder(
            context,
            ChamaaMainDatabase::class.java,
            "chamaa_database"
        ).build()
    }

    @Singleton
    @Provides
    fun provideRepository(database: ChamaaMainDatabase): DbRepository {
        return DbRepository(
            chamaDao = database.chamaDao(),
            contributionDao = database.contributionDao(),
            investmentDao = database.investmentDao(),
            loadDao = database.loadDao(),
            memberDao = database.memberDao(),
            transactionDao = database.transactionDao(),
            chamaAccountDao = database.chamaaAccountDao(),
            accountTypeDao = database.accountTypeDao(),
            transactionTypeDao = database.transactionTypeDao(),
        )
    }

}