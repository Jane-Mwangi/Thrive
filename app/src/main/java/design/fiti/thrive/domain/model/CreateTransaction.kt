package design.fiti.thrive.domain.model

import design.fiti.thrive.data.local.models.CreateTransactionEntity

data class CreateTransaction(
    val amount: Int,
    val name: String,
    val transactionType: String
)