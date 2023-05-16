package student.testing.system.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import student.testing.system.domain.login.AuthIfPossibleUseCase
import student.testing.system.domain.login.AuthUseCase
import student.testing.system.domain.login.LoginState
import student.testing.system.models.PrivateUser
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
    private val authIfPossibleUseCase: AuthIfPossibleUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<LoginState<PrivateUser>>(LoginState.Loading)
    val uiState: StateFlow<LoginState<PrivateUser>> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            authIfPossibleUseCase().collect {
                _uiState.value = it
            }
        }
    }

    fun auth(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = LoginState.Loading
            authUseCase(email, password).collect {
                _uiState.value = it
            }
        }
    }
}