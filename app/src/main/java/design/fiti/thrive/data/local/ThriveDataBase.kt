package design.fiti.thrive.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import design.fiti.thrive.data.local.models.ExpenseEntity
import design.fiti.thrive.data.local.models.IncomeEntity
import design.fiti.thrive.data.local.models.PredictionsEntity
import design.fiti.thrive.data.local.models.UserTransactionEntity

@Database(
    entities = [ExpenseEntity::class, IncomeEntity::class, UserTransactionEntity::class, PredictionsEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class ThriveDataBase : RoomDatabase() {
    abstract fun appDao(): AppDao

    companion object {
        @Volatile
        private var Instance: ThriveDataBase? = null

        fun getDatabase(context: Context): ThriveDataBase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, ThriveDataBase::class.java, "thrive_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }

        }

    }


}
