package student.testing.system.domain.login

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import student.testing.system.models.PrivateUser
import student.testing.system.sharedPreferences.PrefsUtils
import javax.inject.Inject

class AuthIfPossibleUseCase @Inject constructor(
    private val authUseCase: AuthUseCase,
    private val prefsUtils: PrefsUtils
) {

    suspend operator fun invoke(): Flow<LoginState<PrivateUser>> {
        val stateFlow = MutableStateFlow<LoginState<PrivateUser>>(LoginState.Loading)
        if (isAuthDataSaved()) {
            return authWithSavedData()
        } else {
            stateFlow.emit(LoginState.Unauthorized)
        }
        return stateFlow
    }

    private fun isAuthDataSaved() = prefsUtils.getEmail().isNotEmpty()

    private suspend fun authWithSavedData(): Flow<LoginState<PrivateUser>> {
        return authUseCase(prefsUtils.getEmail(), prefsUtils.getPassword())
    }
}