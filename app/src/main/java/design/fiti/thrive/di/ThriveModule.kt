package design.fiti.thrive.di

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import design.fiti.thrive.core.util.USER_PREFERENCES_NAME
import design.fiti.thrive.data.local.AppDao
import design.fiti.thrive.data.local.Converters
import design.fiti.thrive.data.local.ThriveDataBase
import design.fiti.thrive.data.remote.ThriveApi
import design.fiti.thrive.data.remote.util.GsonParser
import design.fiti.thrive.data.repository.AuthRepositoryImplementation
import design.fiti.thrive.data.repository.PredictionsRepositoryImplementation
//import design.fiti.thrive.data.repository.IncomeRepositoryImplementation
import design.fiti.thrive.data.repository.TransactionRepositoryImplementation
import design.fiti.thrive.data.repository.UserPreferencesImplementation
import design.fiti.thrive.domain.repository.AuthRepository
import design.fiti.thrive.domain.repository.PredictionsRepository
import design.fiti.thrive.domain.repository.TransactionRepository
import design.fiti.thrive.domain.repository.UserPreferencesRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton


val Context.ourdataStore by preferencesDataStore(
    name = USER_PREFERENCES_NAME
)

@Module
@InstallIn(SingletonComponent::class)
object ThriveModule {

    @Provides
    @Singleton
    fun provideThriveDatabase(app: Application): ThriveDataBase {
        return Room.databaseBuilder(
            app, ThriveDataBase::class.java, "thrive_db"
        ).addTypeConverter(Converters(GsonParser(Gson())))
            .build()
    }


    @Provides
    @Singleton
    fun provideAuthRepository(
        api: ThriveApi,
        userPreferencesRepository: UserPreferencesRepository
    ): AuthRepository {
        return AuthRepositoryImplementation(api = api, userPreferences = userPreferencesRepository)
    }


    @Provides
    @Singleton
    fun provideUserPreferenceRepository(
        @ApplicationContext context: Context,
    ): UserPreferencesRepository {
        val dataStore: DataStore<Preferences> = context.ourdataStore
        return UserPreferencesImplementation(dataStore)
    }


    @Provides
    @Singleton
    fun provideDatastore(
        @ApplicationContext context: Context,
    ): DataStore<Preferences> {
        val dataStore: DataStore<Preferences> = context.ourdataStore
        return dataStore
    }


    @Provides
    @Singleton
    fun ProvideCreateTransactionRepository(
        api: ThriveApi,
        db: ThriveDataBase,
        prefs: UserPreferencesRepository
    ): TransactionRepository {
        return TransactionRepositoryImplementation(api, dao = db.appDao(), userPreferences = prefs)
    }

    @Provides
    @Singleton
    fun ProvidePredictionRepository(
        api: ThriveApi,
        db: ThriveDataBase,
        prefs: UserPreferencesRepository
    ): PredictionsRepository {
        return PredictionsRepositoryImplementation(api, dao = db.appDao(), userPreferences = prefs)
    }


//    @Provides
//    @Singleton
//    fun provideExpenseRepository(
//        api: ThriveApi,
//        db: ThriveDataBase,
//        prefs: UserPreferencesRepository
//    ): ExpenseRepository {
//        return ExpenseRepositoryImplementation(api, dao = db.appDao(), userPreferences = prefs)
//    }

//    @Provides
//    @Singleton
//    fun provideIncomeRepository(
//        api: ThriveApi,
//        db: ThriveDataBase,
//        prefs: UserPreferencesRepository
//    ): IncomeRepository {
//        return IncomeRepositoryImplementation(api, dao = db.appDao(), userPreferences = prefs)
//    }
//    @Provides
//    @Singleton
//    fun provideTransactionRepository(
//        api: ThriveApi,
//        db: ThriveDataBase,
//        prefs: UserPreferencesRepository
//    ): TransactionRepository {
//        return TransactionRepositoryImplementation(api, dao = db.appDao(), userPreferences = prefs)
//    }


    @Provides
    @Singleton
    fun provideThriveApi(): ThriveApi {
        val gson = GsonBuilder().create()
        return Retrofit.Builder()
            .baseUrl(ThriveApi.BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ThriveApi::class.java)
    }

}