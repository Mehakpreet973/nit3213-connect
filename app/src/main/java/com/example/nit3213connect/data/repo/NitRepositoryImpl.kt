package com.example.nit3213connect.data.repo

import com.example.nit3213connect.data.remote.LoginRequest
import com.example.nit3213connect.data.remote.Nit3213Api
import com.example.nit3213connect.domain.model.Entity
import com.example.nit3213connect.domain.model.toDomain
import javax.inject.Inject

class NitRepositoryImpl @Inject constructor(
    private val api: Nit3213Api
) : NitRepository {

    override suspend fun login(campus: String, username: String, password: String): String {
        val res = api.login(
            campus = campus.trim().lowercase(),
            body   = LoginRequest(username.trim(), password.trim())
        )
        val key = res.keypass?.trim()?.lowercase()
        require(!key.isNullOrEmpty()) { "Login response missing keypass" }
        return key
    }

    override suspend fun fetchEntities(keypass: String): List<Entity> {
        val res = api.getDashboard(keypass.trim().lowercase())
        val raw = res.entities ?: emptyList()
        return raw.map { it.toDomain() }
    }
}
