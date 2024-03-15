package design.fiti.thrive.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import design.fiti.thrive.data.local.models.ExpenseEntity
import design.fiti.thrive.data.local.models.IncomeEntity

@Database(
    entities = [ExpenseEntity::class, IncomeEntity::class],
    version = 1,
    exportSchema = false
)
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
