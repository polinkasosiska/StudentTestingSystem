package student.testing.system.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import student.testing.system.models.CourseResponse
import student.testing.system.domain.DataState
import student.testing.system.domain.MainRepository
import student.testing.system.domain.usecases.CreateCourseUseCase
import student.testing.system.domain.usecases.JoinCourseUseCase
import javax.inject.Inject

open class BaseViewModel<T> : ViewModel() {
    private val _uiState = MutableStateFlow<DataState<T>>(DataState.Initial)
    val uiState: StateFlow<DataState<T>> = _uiState.asStateFlow()

    protected suspend fun launchRequest(request: Flow<DataState<T>>) {
        _uiState.value = DataState.Loading
        return request.collect {
            _uiState.value = it
        }
    }
}