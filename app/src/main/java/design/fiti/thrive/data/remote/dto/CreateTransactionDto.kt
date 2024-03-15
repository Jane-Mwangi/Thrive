package design.fiti.thrive.data.remote.dto

import design.fiti.thrive.data.local.models.CreateTransactionEntity

data class CreateTransactionDto(
    val amount: Int,
    val name: String,
    val transactionType: String
){


    fun toCreateTransaction():CreateTransactionEntity= CreateTransactionEntity(
        amount=amount,
        name = name,
        transactionType = transactionType
    )
}