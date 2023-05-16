package student.testing.system.domain.getResult

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import student.testing.system.R
import student.testing.system.domain.DataState
import student.testing.system.domain.MainRepository
import student.testing.system.domain.login.LoginState
import student.testing.system.models.CourseResponse
import student.testing.system.models.PrivateUser
import student.testing.system.models.TestResult
import javax.inject.Inject

class GetResultUseCase @Inject constructor(private val repository: MainRepository) {

    suspend operator fun invoke(testId: Int, courseId: Int): Flow<ResultState<TestResult>> {
        val stateFlow = MutableStateFlow<ResultState<TestResult>>(ResultState.Loading)
        repository.getResult(testId, courseId).collect {
            if (it is DataState.Success) {
                stateFlow.emit(ResultState.Success(it.data))
            } else if (it is DataState.Error && it.code == 404) {
                stateFlow.emit(ResultState.NoResult)
            } else if (it is DataState.Error) {
                stateFlow.emit(ResultState.Error(it.exception, it.code))
            }
        }
        return stateFlow
    }
}