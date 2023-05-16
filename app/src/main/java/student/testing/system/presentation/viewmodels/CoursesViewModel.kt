package student.testing.system.presentation.viewmodels

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import student.testing.system.models.CourseResponse
import student.testing.system.domain.DataState
import student.testing.system.domain.MainRepository
import javax.inject.Inject

@HiltViewModel
class CoursesViewModel @Inject constructor(private val repository: MainRepository) :
    BaseViewModel<List<CourseResponse>>() {

    init {
        getCourses()
    }

    private fun getCourses() {
        viewModelScope.launch {
            launchRequest(repository.getCourses())
        }
    }

    fun deleteCourse(courseId: Int): StateFlow<DataState<Int>> {
        val stateFlow = MutableStateFlow<DataState<Int>>(DataState.Loading)
        viewModelScope.launch {
            repository.deleteCourse(courseId).collect {
                if (it is DataState.Empty) {
                    stateFlow.emit(DataState.Success(courseId))
                } else {
                    stateFlow.emit(it as DataState.Error)
                }
            }
        }
        return stateFlow
    }
}