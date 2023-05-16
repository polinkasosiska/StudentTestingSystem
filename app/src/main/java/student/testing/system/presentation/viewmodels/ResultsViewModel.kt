package student.testing.system.presentation.viewmodels

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import student.testing.system.domain.MainRepository
import student.testing.system.models.ParticipantsResults
import student.testing.system.sharedPreferences.PrefsUtils
import javax.inject.Inject

@HiltViewModel
class ResultsViewModel @Inject constructor(
    private val repository: MainRepository,
    val prefsUtils: PrefsUtils
) : BaseViewModel<ParticipantsResults>() {

    fun getResults(
        testId: Int,
        courseId: Int,
        showOnlyMaxResults: Boolean = false
    ) {
        viewModelScope.launch {
            launchRequest(repository.getResults(testId, courseId, showOnlyMaxResults))
        }
    }
}