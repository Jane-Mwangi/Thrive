package design.fiti.thrive.data.remote.dto
import design.fiti.thrive.data.local.models.UserTransactionEntity


data class UserTransactionDto(
    val __v: Int,
    val _id: String,
    val amount: Int,
    val createdAt: String,
    val name: String,
    val transactionOwner: String,
    val transactionType: String
) {
    fun toUserTransactionEntity(): UserTransactionEntity = UserTransactionEntity(
        __v = __v,
        _id = _id,
        amount = amount,
        createdAt = createdAt,
        name = name,
        transactionOwner = transactionOwner,
        transactionType = transactionType

    )

}
