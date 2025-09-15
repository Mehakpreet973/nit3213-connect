package com.example.nit3213connect.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nit3213connect.data.repo.NitRepository
import com.example.nit3213connect.domain.model.Entity
import com.example.nit3213connect.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repo: NitRepository
) : ViewModel() {

    private val _entities = MutableStateFlow<Resource<List<Entity>>>(Resource.Loading)
    val entities: StateFlow<Resource<List<Entity>>> = _entities

    fun load(keypass: String) {
        viewModelScope.launch {
            _entities.value = Resource.Loading
            try {
                val items = repo.fetchEntities(keypass.trim().lowercase())
                _entities.value = Resource.Success(items)
            } catch (t: Throwable) {
                _entities.value = Resource.Error(
                    t.message ?: "Failed to load dashboard",
                    t
                )
            }
        }
    }
}
