package design.fiti.thrive.data.repository

import Resource
import android.util.Log
import design.fiti.thrive.core.util.httpError
import design.fiti.thrive.data.local.AppDao
import design.fiti.thrive.data.remote.ThriveApi
import design.fiti.thrive.domain.model.Predictions
import design.fiti.thrive.domain.model.UserTransaction
import design.fiti.thrive.domain.repository.PredictionsRepository
import design.fiti.thrive.domain.repository.UserPreferencesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import retrofit2.HttpException
import java.io.IOException

class PredictionsRepositoryImplementation(
    private val api: ThriveApi,
    private val dao: AppDao,
    private val userPreferences: UserPreferencesRepository
) : PredictionsRepository {
    override suspend fun getUserPredictions(): Flow<Resource<Predictions>> = flow {
        emit(Resource.Loading())
        Log.d("Home Vm", "Nimeitwa...................................")
        var cachedPredictions =
            dao.getUserPredictions()
                ?.stateIn(CoroutineScope(Job() + Dispatchers.IO))?.value

        Log.d(
            "Home Vm yettyuuuuu...............................",
            "data in Cached Predictions : ${cachedPredictions}"
        )


//        cachedPredictions = cachedPredictions.toPredictions()
        var authToken: String =
            userPreferences.authToken.stateIn(CoroutineScope(Job() + Dispatchers.IO)).value
        Log.d(
            "Home Vm",
            "data in Repo TOKEN stored: ${authToken}........CACHED-PREDICTION:         ${cachedPredictions}"
        )

        if (cachedPredictions != null)
            emit(Resource.Loading(data = cachedPredictions.toPredictions()))

        try {
            val fetchedPredictions = api.getPredictions_forTheUser(
                authToken = authToken,
            )
            Log.d("Home Vm", "data in Repo: ${fetchedPredictions}")

            dao.deleteAllUserPredictions()

            dao.insertUserPredictions(fetchedPredictions.toPredictionsEntity())

        } catch (e: HttpException) {
            Log.d(
                "Home Predictions Vm What is fetched",
                "Bug in Repo: ${e.response()} ${e.message()} ${e.message} ${e.localizedMessage}"
            )

            emit(Resource.Error(httpError, cachedPredictions?.toPredictions()))
        } catch (e: IOException) {
            Log.d(
                "Home Predictions Vm What is fetched",
                "Bug in Repo:  ${e.message} ${e.localizedMessage}"
            )

        }

        val newPredictions: Predictions? =
            dao.getUserPredictions()
                ?.stateIn(CoroutineScope(Job() + Dispatchers.IO))?.value?.toPredictions()

        emit(Resource.Success(newPredictions))
    }


}