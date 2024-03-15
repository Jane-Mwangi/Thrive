package design.fiti.thrive.data.remote.dto

import design.fiti.thrive.domain.model.DeleteResponse


data class DeleteResponseDto(
    val message: String
) {
    fun toDeleteResponse() = DeleteResponse(message)
}