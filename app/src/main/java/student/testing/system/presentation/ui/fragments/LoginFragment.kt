package student.testing.system.presentation.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach
import student.testing.system.R
import student.testing.system.common.*
import student.testing.system.databinding.FragmentLoginBinding
import student.testing.system.domain.DataState
import student.testing.system.domain.login.LoginState
import student.testing.system.presentation.ui.activity.MainActivity
import student.testing.system.presentation.viewmodels.LoginViewModel


@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private val viewModel by viewModels<LoginViewModel>()
    private val binding by viewBinding(FragmentLoginBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            loginLayout.editText?.doOnTextChanged { _, _, _, _ ->
                loginLayout.error = null
            }
            passwordLayout.editText?.doOnTextChanged { _, _, _, _ ->
                passwordLayout.error = null
            }
            btnSignUp.setOnClickListener {
                val bundle = Bundle()
                Navigation.findNavController(root)
                    .navigate(R.id.action_loginFragment_to_signUpFragment, bundle)
            }
            btnLogin.setOnClickListener() {
                auth(login.text.trimString(), password.text.trimString())
            }
        }
        subscribeObserver()
    }

    private fun subscribeObserver() {
        viewModel.uiState.onEach {
            binding.progressBar.showIf(it is LoginState.Loading)
            if (it is LoginState.Unauthorized) {
                binding.progressBar.showIf(false)
                binding.main.showIf(true)
            } else if (it is LoginState.Success) {
                requireActivity().finish()
                startActivity(Intent(requireContext(), MainActivity::class.java))
            } else if (it is LoginState.Error) {
                showSnackbar(it.exception)
            } else if (it is LoginState.EmailError) {
                binding.loginLayout.error = getString(it.messageResId)
            } else if (it is LoginState.PasswordError) {
                binding.passwordLayout.error = getString(it.messageResId)
            }
        }.launchWhenStartedCollect(lifecycleScope)
    }

    private fun auth(email: String, password: String) {
        viewModel.auth(email, password)
    }
}