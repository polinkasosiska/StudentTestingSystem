package student.testing.system.domain.login

import androidx.annotation.StringRes

sealed class LoginState<out R> {
    object Initial : LoginState<Nothing>()
    object Unauthorized : LoginState<Nothing>()
    data class Success<out T>(val data: T) : LoginState<T>()
    data class Error(val exception: String, val code: Int = -1) : LoginState<Nothing>()
    data class EmailError(@StringRes val messageResId: Int) : LoginState<Nothing>()
    data class PasswordError(@StringRes val messageResId: Int) : LoginState<Nothing>()
    object Loading : LoginState<Nothing>()
}