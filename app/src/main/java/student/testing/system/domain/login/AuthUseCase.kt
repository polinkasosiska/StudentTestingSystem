package student.testing.system.domain.login

import androidx.core.util.PatternsCompat
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import student.testing.system.R
import student.testing.system.common.AccountSession
import student.testing.system.domain.MainRepository
import student.testing.system.domain.DataState
import student.testing.system.models.PrivateUser
import student.testing.system.sharedPreferences.PrefsUtils
import javax.inject.Inject

class AuthUseCase @Inject constructor(
    private val repository: MainRepository,
    private val prefsUtils: PrefsUtils
) {

    suspend operator fun invoke(email: String, password: String): Flow<LoginState<PrivateUser>> {
        if (email.isEmpty()) {
            return flow { emit(LoginState.EmailError(R.string.error_empty_field)) }
        } else if (!PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()) {
            return flow { emit(LoginState.EmailError(R.string.error_invalid_email)) }
        } else if (password.isEmpty()) {
            return flow { emit(LoginState.PasswordError(R.string.error_empty_field)) }
        } else {
            return auth(email, password)
        }
    }

    private suspend fun auth(email: String, password: String): Flow<LoginState<PrivateUser>> {
        val stateFlow = MutableStateFlow<LoginState<PrivateUser>>(LoginState.Loading)
        val authRequest =
            "grant_type=&username=$email&password=$password&scope=&client_id=&client_secret="
        val call = repository.auth(authRequest)
        call.collect {
            if (it is DataState.Initial) {
                stateFlow.emit(LoginState.Initial)
            } else if (it is DataState.Loading) {
                stateFlow.emit(LoginState.Loading)
            } else if (it is DataState.Success) {
                saveAuthData(email, password)
                createSession(it.data)
                stateFlow.emit(LoginState.Success(it.data))
            } else if (it is DataState.Error) {
                stateFlow.emit(LoginState.Error(it.exception, it.code))
            }
        }
        return stateFlow
    }

    private fun createSession(privateUser: PrivateUser) {
        AccountSession.instance.token = privateUser.token
        AccountSession.instance.userId = privateUser.id
        AccountSession.instance.email = privateUser.email
        AccountSession.instance.username = privateUser.username
    }

    private fun saveAuthData(email: String, password: String) {
        prefsUtils.setEmail(email)
        prefsUtils.setPassword(password)
    }
}