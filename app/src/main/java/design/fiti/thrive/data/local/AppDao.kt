package design.fiti.thrive.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import design.fiti.thrive.data.local.models.ExpenseEntity
import design.fiti.thrive.data.local.models.IncomeEntity
import design.fiti.thrive.data.local.models.PredictionsEntity
import design.fiti.thrive.data.local.models.UserTransactionEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface AppDao {
    /**
     * -Dealing with Transactions
     * @author Jane Mwangi
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE, entity = UserTransactionEntity::class)
    suspend fun insertUserTransaction(framework: UserTransactionEntity)


    @Update(entity = UserTransactionEntity::class)
    suspend fun updateUserTransaction(framework: UserTransactionEntity)

//    @Delete(entity = UserTransactionEntity::class)
//    suspend fun deleteUserTransaction(framework: UserTransactionEntity)


    @Query("DELETE from usertransaction")
    suspend fun deleteAllUserTransaction()

    @Query("SELECT * from usertransaction")
    fun getAllUserTransaction(): Flow<List<UserTransactionEntity>>

    @Query("SELECT * from usertransaction WHERE transactionType = :transactionType")
    fun getAllUserExpense(transactionType: String = "expense"): Flow<List<UserTransactionEntity>>

    @Query("SELECT * from usertransaction WHERE transactionType = :transactionType")
    fun getAllUserIncome(transactionType: String = "income"): Flow<List<UserTransactionEntity>>

    /**
     * -Dealing with Predictions
     *
     */

    @Insert(onConflict = OnConflictStrategy.IGNORE, entity = PredictionsEntity::class)
    suspend fun insertUserPredictions(framework: PredictionsEntity)
    @Query("DELETE from predictions")
    suspend fun deleteAllUserPredictions()
    @Query("SELECT * from predictions")
    fun getUserPredictions(): Flow<PredictionsEntity>?


}
//    /**
//     * -Dealing with Expense
//     * @author Jane Mwangi
//     */
//    @Insert(onConflict = OnConflictStrategy.IGNORE, entity = ExpenseEntity::class)
//    suspend fun insertExpense(framework: ExpenseEntity)
//
//    @Update(entity = ExpenseEntity::class)
//    suspend fun updateExpense(framework: ExpenseEntity)
//
//    @Delete(entity = ExpenseEntity::class)
//    suspend fun deleteExpense(framework: ExpenseEntity)
//
//    @Query("Delete from expense")
//    suspend fun deleteAllExpense()
//
//    @Query("SELECT * from expense")
//    fun getAllExpense(): Flow<List<ExpenseEntity>>
//
//    @Query("SELECT * from expense WHERE userIdReference = :userIdReference")
//    fun getAllExpense_forTheUser(userIdReference: String): Flow<List<ExpenseEntity>>
//
//
//
//
//    /**
//     * -Dealing with Income
//     * @author Jane Mwangi
//     */
//    @Insert(onConflict = OnConflictStrategy.IGNORE, entity = IncomeEntity::class)
//    suspend fun insertIncome(framework: IncomeEntity)
//
//    @Update(entity = IncomeEntity::class)
//    suspend fun updateIncome(framework: IncomeEntity)
//
//    @Delete(entity = IncomeEntity::class)
//    suspend fun deleteIncome(framework: IncomeEntity)
//
//    @Query("Delete from income")
//    suspend fun deleteAllIncome()
//
//    @Query("SELECT * from income")
//    fun getAllIncome(): Flow<List<IncomeEntity>>
//
//    @Query("SELECT * from income WHERE userIdReference = :userIdReference")
//    fun getAllIncome_forTheUser(userIdReference: String): Flow<List<IncomeEntity>>
