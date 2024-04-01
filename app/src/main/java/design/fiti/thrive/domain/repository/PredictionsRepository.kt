package design.fiti.thrive.domain.repository

import Resource
import design.fiti.thrive.domain.model.Predictions
import kotlinx.coroutines.flow.Flow

interface PredictionsRepository {
    suspend fun getUserPredictions(): Flow<Resource<Predictions>>
}