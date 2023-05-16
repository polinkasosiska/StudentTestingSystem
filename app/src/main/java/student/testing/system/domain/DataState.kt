package student.testing.system.domain

import androidx.annotation.StringRes

sealed class DataState<out R> {
    object Initial : DataState<Nothing>()
    data class Success<out T>(val data: T) : DataState<T>()
    data class Empty(val code: Int) : DataState<Nothing>()
    data class Error(val exception: String, val code: Int = -1) : DataState<Nothing>()
    data class ValidationError(@StringRes val messageResId: Int) : DataState<Nothing>()
    object Loading : DataState<Nothing>()
}