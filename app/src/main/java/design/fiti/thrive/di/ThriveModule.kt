package design.fiti.thrive.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import design.fiti.thrive.data.remote.ThriveApi
import design.fiti.thrive.data.repository.AuthRepositoryImplementation
import design.fiti.thrive.domain.repository.AuthRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ThriveModule {

    @Provides
    @Singleton
    fun provideDictionaryApi(): ThriveApi {
        return Retrofit.Builder()
            .baseUrl(ThriveApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ThriveApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(api: ThriveApi): AuthRepository {
        return AuthRepositoryImplementation(api = api)
    }
}