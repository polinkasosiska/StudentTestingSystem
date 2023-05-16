package student.testing.system.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import student.testing.system.models.CourseResponse

class CourseSharedViewModel : ViewModel() {

    val courseFlow =
        MutableSharedFlow<CourseResponse>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    fun setCourse(course: CourseResponse) {
        viewModelScope.launch {
            courseFlow.tryEmit(course)
        }
    }
}