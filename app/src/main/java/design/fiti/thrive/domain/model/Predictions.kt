package design.fiti.thrive.domain.model

data class Predictions(
    val predictedSpendings: List<Double>,
    val totalSpent: Double
)
