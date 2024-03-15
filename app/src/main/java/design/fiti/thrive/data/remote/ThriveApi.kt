package design.fiti.thrive.data.remote

import design.fiti.thrive.data.remote.dto.CreateTransactionDto
import design.fiti.thrive.data.remote.dto.DeleteResponseDto
import design.fiti.thrive.data.remote.dto.UserDto
import design.fiti.thrive.data.remote.dto.UserTransactionDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path


interface ThriveApi {
    @POST("users")
    suspend fun signUp(@Body user: UserDto): String

    @POST("users/auth")
    suspend fun login(@Body user: UserDto): String

    companion object {
        const val BASE_URL = "http://192.168.42.202:3900/api/"
    }

    /**
     * Dealing with the Expense
     * -@author Jane
     */

//    @POST("/expense/create")
//    suspend fun createUserExpense(
//        @Body expense: ExpenseDto,
//        @Header("Authorization") authToken: String,
//    )
//
//    @GET("/expense/user/{userId}")
//    suspend fun getExpense_forTheUser(
//        @Header("Authorization") authToken: String,
//        @Path("userId") userId: String
//    ): List<ExpenseDto>
//
//    @DELETE("/expense/delete/{id}")
//    suspend fun deleteUserExpense(
//        @Header("Authorization") authToken: String,
//        @Path("id") id: String
//    ): DeleteResponseDto

    /**
     * Dealing with the Transactions
     * -@author Jane
     */

    //creating//POST income and expense
    @POST("transaction/")
    suspend fun createUserTransaction(
        @Body createTransaction: CreateTransactionDto,
        @Header("Authorization") authToken: String,
    )


    //getting expense
    @GET("transaction/expense")
    suspend fun getExpense_forTheUser(
        @Header("Authorization") authToken: String,
    ): List<UserTransactionDto>

    //getting income

    @GET("transaction/income")
    suspend fun getIncome_forTheUser(
        @Header("Authorization") authToken: String,
    ): List<UserTransactionDto>

    //delete expense and income
    @DELETE("transaction/:id")
    suspend fun deleteUserTransaction(
        @Header("Authorization") authToken: String,
        @Path("transactionId") transactionId: String
    ): DeleteResponseDto

    abstract fun createUserTransaction(userTransaction: UserTransactionDto, authToken: String)

//    /**
//     * - Dealing with Income
//     */
//    @POST("/income/create")
//    suspend fun createUserIncome(
//        @Body income: IncomeDto,
//        @Header("Authorization") authToken: String,
//    )
//
//    @GET("/income/user/{userId}")
//    suspend fun getIncome_forTheUser(
//        @Header("Authorization") authToken: String,
//        @Path("userId") userId: String
//    ): List<IncomeDto>
//
//    @DELETE("/income/delete/{id}")
//    suspend fun deleteUserIncome(
//        @Header("Authorization") authToken: String,
//        @Path("id") id: String
//    ): DeleteResponseDto
//
//    @GET("users/{email}")
//    suspend fun getUserDetails(
//        @Header("Authorization") authToken: String,
//        @Path("email") email: String
//    ): UserDto


}