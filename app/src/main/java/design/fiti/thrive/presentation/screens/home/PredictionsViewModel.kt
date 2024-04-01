package design.fiti.thrive.presentation.screens.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import design.fiti.thrive.domain.repository.PredictionsRepository
import design.fiti.thrive.domain.repository.UserPreferencesRepository
import javax.inject.Inject

@HiltViewModel
class PredictionsViewModel  @Inject constructor(
    private val predictionsRepository:PredictionsRepository,
    val userPreferencesRepository: UserPreferencesRepository
):ViewModel(){



}