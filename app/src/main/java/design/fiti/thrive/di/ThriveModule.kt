package design.fiti.thrive.di

import android.app.Application
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import design.fiti.thrive.data.local.AppDao
import design.fiti.thrive.data.local.Converters
import design.fiti.thrive.data.local.ThriveDataBase
import design.fiti.thrive.data.remote.ThriveApi
import design.fiti.thrive.data.remote.util.GsonParser
import design.fiti.thrive.data.repository.AuthRepositoryImplementation
//import design.fiti.thrive.data.repository.IncomeRepositoryImplementation
import design.fiti.thrive.data.repository.TransactionRepositoryImplementation
import design.fiti.thrive.domain.repository.AuthRepository
import design.fiti.thrive.domain.repository.TransactionRepository
import design.fiti.thrive.domain.repository.UserPreferencesRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ThriveModule {


    @Provides
    @Singleton
    fun provideAuthRepository(api: ThriveApi): AuthRepository {
        return AuthRepositoryImplementation(api = api)
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
    fun provideDatabase(app: Application): ThriveDataBase {
        return Room.databaseBuilder(
            app, ThriveDataBase::class.java, "thrive_db"
        )
            .addTypeConverter(Converters(GsonParser(Gson())))
            .build()
    }

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