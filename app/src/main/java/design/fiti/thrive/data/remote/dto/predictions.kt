package design.fiti.thrive.data.remote.dto

import design.fiti.thrive.data.local.models.PredictionsEntity

data class PredictionsDto(
    val predictedSpendings: List<Double>,
    val totalSpent: Double
) {
    fun toPredictionsEntity(): PredictionsEntity = PredictionsEntity(
        predictedSpendings = predictedSpendings,
        totalSpent = totalSpent
    )
}