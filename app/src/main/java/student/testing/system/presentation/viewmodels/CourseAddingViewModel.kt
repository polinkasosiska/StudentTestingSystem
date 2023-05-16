package student.testing.system.presentation.viewmodels

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import student.testing.system.models.CourseResponse
import student.testing.system.domain.usecases.CreateCourseUseCase
import student.testing.system.domain.usecases.JoinCourseUseCase
import javax.inject.Inject

@HiltViewModel
class CourseAddingViewModel @Inject constructor(
    private val createCourseUseCase: CreateCourseUseCase,
    private val joinCourseUseCase: JoinCourseUseCase
) : BaseViewModel<CourseResponse>() {

    fun createCourse(name: String) {
        viewModelScope.launch {
            launchRequest(createCourseUseCase(name))
        }
    }

    fun joinCourse(courseCode: String){
        viewModelScope.launch {
            launchRequest(joinCourseUseCase(courseCode))
        }
    }
}