package design.fiti.thrive.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import design.fiti.thrive.domain.model.Predictions

@Entity(tableName = "predictions")
data class PredictionsEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val predictedSpendings: List<Double>,
    val totalSpent: Double
) {
    fun toPredictions(): Predictions = Predictions(
        predictedSpendings, totalSpent
    )
}
