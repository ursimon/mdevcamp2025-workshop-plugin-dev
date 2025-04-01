package com.example.workshop.screen

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val _loginState = MutableStateFlow(LoginState())
    val loginState: StateFlow<LoginState> = _loginState

    fun login(navController: NavController, username: String, password: String) {
        viewModelScope.launch {
            val isUsernameValid = username == "username"
            val isPasswordValid = password == "password"

            _loginState.value = LoginState(
                usernameError = !isUsernameValid,
                passwordError = !isPasswordValid,
            )

            val isLoginSuccessful = isUsernameValid && isPasswordValid

            if (isLoginSuccessful) {
                navController.navigate("success")
            } else {
                Toast.makeText(navController.context, "Login failed :-(", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun invalidateUsernameError() {
        _loginState.update {
            it.copy(usernameError = false)
        }
    }

    fun invalidatePasswordError() {
        _loginState.update {
            it.copy(passwordError = false)
        }
    }
}

data class LoginState(
    val usernameError: Boolean = false,
    val passwordError: Boolean = false
)