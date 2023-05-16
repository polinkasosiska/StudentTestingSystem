package student.testing.system.presentation.viewmodels

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import student.testing.system.domain.MainRepository
import student.testing.system.models.Participant
import javax.inject.Inject

@HiltViewModel
class ParticipantsViewModel @Inject constructor(private val repository: MainRepository) :
    BaseViewModel<List<Participant>>() {

    fun addModerator(courseId: Int, moderatorId: Int){
        viewModelScope.launch {
            launchRequest(repository.addModerator(courseId, moderatorId))
        }
    }

    fun deleteModerator(courseId: Int, moderatorId: Int) {
        viewModelScope.launch {
            launchRequest(repository.deleteModerator(courseId, moderatorId))
        }
    }

    fun deleteParticipant(courseId: Int, participantId: Int) {
        viewModelScope.launch {
            launchRequest(repository.deleteParticipant(courseId, participantId))
        }
    }
}