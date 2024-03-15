package design.fiti.thrive.domain.model

data class UserTransaction(
    val __v: Int,
    val _id: String,
    val amount: Int,
    val createdAt: String,
    val name: String,
    val transactionOwner: String,
    val transactionType: String
)
