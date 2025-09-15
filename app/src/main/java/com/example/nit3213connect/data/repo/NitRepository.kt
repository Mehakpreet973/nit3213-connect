package com.example.nit3213connect.data.repo

import com.example.nit3213connect.domain.model.Entity

interface NitRepository {
    suspend fun login(campus: String, username: String, password: String): String
    suspend fun fetchEntities(keypass: String): List<Entity>
}
