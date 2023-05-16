package student.testing.system.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import student.testing.system.domain.DataState
import student.testing.system.domain.MainRepository
import student.testing.system.common.Utils
import student.testing.system.models.TestResult
import student.testing.system.models.UserQuestion
import javax.inject.Inject

@HiltViewModel
class TestPassingViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {

    val userQuestions: ArrayList<UserQuestion> = arrayListOf()

    fun calculateResult(testId: Int, courseId: Int): StateFlow<DataState<Int>> {
        val stateFlow = MutableStateFlow<DataState<Int>>(DataState.Loading)
        viewModelScope.launch {
            repository.calculateResult(testId, courseId, userQuestions).collect {
                if (it is DataState.Empty) {
                    stateFlow.emit(DataState.Success(0))
                }
            }
        }
        return stateFlow
    }

    fun calculateDemoResult(courseId: Int, testId: Int): StateFlow<DataState<TestResult>> {
        val stateFlow = MutableStateFlow<DataState<TestResult>>(DataState.Loading)
        viewModelScope.launch {
            repository.calculateDemoResult(courseId, testId, userQuestions).collect {
                stateFlow.emit(it)
            }
        }
        return stateFlow
    }

    fun getResult(testId: Int, courseId: Int): StateFlow<DataState<TestResult>> {
        val stateFlow = MutableStateFlow<DataState<TestResult>>(DataState.Loading)
        viewModelScope.launch {
            repository.getResult(testId, courseId).collect {
                stateFlow.emit(it)
            }
        }
        return stateFlow
    }
}