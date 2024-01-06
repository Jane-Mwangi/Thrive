package design.fiti.thrive.data.remote

import design.fiti.thrive.data.remote.dto.UserDto
import retrofit2.http.Body
import retrofit2.http.POST


interface ThriveApi {
    @POST("/user/signup")
    suspend fun signUp(@Body user: UserDto): String
    @POST("/user/login")
    suspend fun login(@Body user : UserDto): String

    companion object {
        const val BASE_URL = "http://127.0.0.1:8000"
    }
}