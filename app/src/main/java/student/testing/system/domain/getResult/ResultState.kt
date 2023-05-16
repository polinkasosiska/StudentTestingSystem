package student.testing.system.domain.getResult

sealed class ResultState<out R> {
    object Loading : ResultState<Nothing>()
    data class Success<out T>(val data: T) : ResultState<T>()
    data class Error(val exception: String, val code: Int = -1) : ResultState<Nothing>()
    object NoResult: ResultState<Nothing>()
}