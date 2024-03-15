package design.fiti.thrive.domain.model

data class Income(
    val _id:String,
    val name: String,
    val amount: Int,
    val userIdReference: String
)
