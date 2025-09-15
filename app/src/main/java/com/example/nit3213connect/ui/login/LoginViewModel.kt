package com.example.nit3213connect.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nit3213connect.data.repo.NitRepository
import com.example.nit3213connect.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repo: NitRepository
) : ViewModel() {

    data class UiState(
        val username: String = "",
        val password: String = "",
        val campus: String = "footscray",
        val loginState: Resource<String>? = null // keypass on success
    )

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state

    fun updateUsername(v: String) { _state.value = _state.value.copy(username = v) }
    fun updatePassword(v: String) { _state.value = _state.value.copy(password = v) }
    fun updateCampus(v: String)   { _state.value = _state.value.copy(campus = v) }

    fun login() {
        val s = _state.value
        if (s.username.isBlank() || s.password.isBlank()) {
            _state.value = s.copy(loginState = Resource.Error("Please enter username & password"))
            return
        }
        viewModelScope.launch {
            _state.value = s.copy(loginState = Resource.Loading)
            try {
                val keypass = repo.login(s.campus.lowercase(), s.username, s.password)
                _state.value = _state.value.copy(loginState = Resource.Success(keypass))
            } catch (t: Throwable) {
                _state.value = _state.value.copy(loginState = Resource.Error(t.message ?: "Login failed", t))
            }
        }
    }
}
