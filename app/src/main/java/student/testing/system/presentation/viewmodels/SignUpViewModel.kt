package student.testing.system.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import student.testing.system.domain.MainRepository
import student.testing.system.domain.DataState
import student.testing.system.common.AccountSession
import student.testing.system.common.Utils
import student.testing.system.models.PrivateUser
import student.testing.system.models.SignUpReq
import student.testing.system.sharedPreferences.PrefsUtils
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    val repository: MainRepository,
    val prefsUtils: PrefsUtils
) : ViewModel(){

    private val _uiState = MutableStateFlow<DataState<PrivateUser>>(DataState.Initial)
    val uiState: StateFlow<DataState<PrivateUser>> = _uiState.asStateFlow()

    fun signUp(email: String, username: String, password: String) {
        _uiState.value = DataState.Loading
        viewModelScope.launch {
            repository.signUp(SignUpReq(email, username, password)).collect {
                if (it is DataState.Success) {
                    val privateUser = it.data
                    AccountSession.instance.token = privateUser.token
                    AccountSession.instance.userId = privateUser.id
                    AccountSession.instance.email = privateUser.email
                    AccountSession.instance.username = privateUser.username
                    prefsUtils.setEmail(email)
                    prefsUtils.setPassword(password)
                }
                _uiState.value = it
            }
        }
    }
}