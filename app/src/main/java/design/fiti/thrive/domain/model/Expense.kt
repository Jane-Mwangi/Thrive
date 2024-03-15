package design.fiti.thrive.domain.model

data class Expense(
    val _id:String,
    val name: String,
    val amount: String,
    val userIdReference: String
)
