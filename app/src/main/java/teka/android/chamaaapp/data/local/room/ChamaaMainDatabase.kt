package teka.android.chamaaapp.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import teka.android.chamaaapp.data.local.room.daos.AccountTypeDao
import teka.android.chamaaapp.data.local.room.daos.ChamaAccountDao
import teka.android.chamaaapp.data.local.room.daos.ChamaDao
import teka.android.chamaaapp.data.local.room.daos.ContributionDao
import teka.android.chamaaapp.data.local.room.daos.InvestmentDao
import teka.android.chamaaapp.data.local.room.daos.LoanDao
import teka.android.chamaaapp.data.local.room.daos.MemberDao
import teka.android.chamaaapp.data.local.room.daos.TransactionDao
import teka.android.chamaaapp.data.local.room.daos.TransactionTypeDao
import teka.android.chamaaapp.data.local.room.entities.AccountTypeEntity
import teka.android.chamaaapp.data.local.room.entities.ChamaAccountEntity
import teka.android.chamaaapp.data.local.room.entities.ChamaEntity
import teka.android.chamaaapp.data.local.room.entities.ContributionEntity
import teka.android.chamaaapp.data.local.room.entities.InvestmentEntity
import teka.android.chamaaapp.data.local.room.entities.LoanEntity
import teka.android.chamaaapp.data.local.room.entities.MemberEntity
import teka.android.chamaaapp.data.local.room.entities.TransactionEntity
import teka.android.chamaaapp.data.local.room.entities.TransactionTypeEntity


@Database(
    entities = [
        ChamaEntity::class,
        ContributionEntity::class,
        InvestmentEntity::class,
        LoanEntity::class,
        MemberEntity::class,
        TransactionEntity::class,
        AccountTypeEntity::class,
        ChamaAccountEntity::class,
        TransactionTypeEntity::class
               ],
    version = 1,
    exportSchema = false
)
abstract class ChamaaMainDatabase: RoomDatabase() {

    abstract fun chamaDao(): ChamaDao
    abstract fun contributionDao(): ContributionDao
    abstract fun investmentDao(): InvestmentDao
    abstract fun loadDao(): LoanDao
    abstract fun memberDao(): MemberDao
    abstract fun transactionDao(): TransactionDao
    abstract fun chamaaAccountDao(): ChamaAccountDao
    abstract fun accountTypeDao(): AccountTypeDao
    abstract fun transactionTypeDao(): TransactionTypeDao

    companion object{
        @Volatile//this is creating the db in a thread safe manner
        var INSTANCE: ChamaaMainDatabase? = null
        fun getDatabase(context: Context): ChamaaMainDatabase {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context,
                    ChamaaMainDatabase::class.java,
                    "chamaa_main_db"
                ).build()
                INSTANCE = instance
                return instance
            }

        }
    }

}