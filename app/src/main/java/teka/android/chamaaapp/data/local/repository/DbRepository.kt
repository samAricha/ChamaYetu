package teka.android.chamaaapp.data.local.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
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
import teka.android.chamaaapp.data.local.room.entities.ContributionWithMemberName
import teka.android.chamaaapp.data.local.room.entities.InvestmentEntity
import teka.android.chamaaapp.data.local.room.entities.LoanEntity
import teka.android.chamaaapp.data.local.room.entities.MemberEntity
import teka.android.chamaaapp.data.local.room.entities.TransactionEntity
import teka.android.chamaaapp.data.local.room.entities.TransactionTypeEntity
import teka.android.chamaaapp.presentation.members.EntityCountResult


class DbRepository(
    private val chamaDao: ChamaDao,
    private val contributionDao: ContributionDao,
    private val investmentDao: InvestmentDao,
    private val loadDao: LoanDao,
    private val memberDao: MemberDao,
    private val transactionDao: TransactionDao,
    private val chamaAccountDao: ChamaAccountDao,
    private val transactionTypeDao: TransactionTypeDao,
    private val accountTypeDao: AccountTypeDao
) {
    //the following are methods which are going to help us get our data.

    val allMembers : Flow<List<MemberEntity>> = memberDao.getAllMembers()
    val allChamas : Flow<List<ChamaEntity>> = chamaDao.getAllChamas()
    val allContributions : Flow<List<ContributionEntity>> = contributionDao.getAllContributions()
    val allInvestments : Flow<List<InvestmentEntity>> = investmentDao.getAllInvestments()
    val allLoans : Flow<List<LoanEntity>> = loadDao.getAllLoans()
    val allTransactions : Flow<List<TransactionEntity>> = transactionDao.getAllTransactions()
    val allChamaAccounts : Flow<List<ChamaAccountEntity>> = chamaAccountDao.getAllChamaAccounts()
    val allTransactionTypes : Flow<List<TransactionTypeEntity>> = transactionTypeDao.getAllTransactionTypes()
    val allAccountTypes : Flow<List<AccountTypeEntity>> = accountTypeDao.getAllAccountTypes()


    suspend fun insertMember(memberEntity: MemberEntity){
        memberDao.insert(memberEntity)
    }

    suspend fun insertChamaa(chamaEntity: ChamaEntity): String{
        chamaDao.insert(chamaEntity)
        return chamaEntity.chamaId
    }

    suspend fun insertChamaaAccount(chamaaAccountEntity: ChamaAccountEntity){
        chamaAccountDao.insertChamaAccount(chamaaAccountEntity)
    }

    suspend fun insertContribution(contributionEntity: ContributionEntity){
        contributionDao.insert(contributionEntity)
    }

    suspend fun insertAccountTypes(accounttypes: List<AccountTypeEntity>){
        accountTypeDao.insertAccountTypes(accounttypes)
    }

    fun getAllContributionsWithMemberName() : Flow<List<ContributionWithMemberName>>{
        return contributionDao.getAllContributionsWithMemberNames()
    }

    suspend fun updateMemberEntity(memberEntity: MemberEntity){
        memberDao.update(member = memberEntity)
    }

    suspend fun updateChamaaEntity(chamaEntity: ChamaEntity){
        chamaDao.update(chamaa = chamaEntity)
    }

    suspend fun updateChamaaAccountEntity(chamaaAccountEntity: ChamaAccountEntity){
        chamaAccountDao.update(chamaaAccount = chamaaAccountEntity)
    }

    suspend fun getTotalMembers(): EntityCountResult<Int> {
        return try {
            val memberCount = memberDao.getMemberCount().first()
            EntityCountResult.Success(memberCount)
        } catch (e: Exception) {
            EntityCountResult.Error(e)
        }
    }

    suspend fun getTotalChamaaAccounts(): EntityCountResult<Int> {
        return try {
            val chamaaAccountsCount = chamaAccountDao.getChamaaAccountsCount().first()
            EntityCountResult.Success(chamaaAccountsCount)
        } catch (e: Exception) {
            EntityCountResult.Error(e)
        }
    }

    suspend fun updateContributionEntity(contributionEntity: ContributionEntity){
        contributionDao.update(contribution = contributionEntity)
    }

}