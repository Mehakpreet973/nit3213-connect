package com.example.nit3213connect.data.remote

import com.google.gson.annotations.SerializedName

// ---------- Auth ----------
data class LoginRequest(
    val username: String,
    val password: String
)

data class LoginResponse(
    @SerializedName("keypass") val keypass: String?
)

// ---------- Dashboard ----------
data class DashboardResponse(
    @SerializedName("entities")    val entities: List<EntityRemote>? = null,
    @SerializedName("entityTotal") val entityTotal: Int? = null
)

data class EntityRemote(
    @SerializedName("species")            val species: String? = null,
    @SerializedName("scientificName")     val scientificName: String? = null,
    @SerializedName("habitat")            val habitat: String? = null,
    @SerializedName("diet")               val diet: String? = null,
    @SerializedName("conservationStatus") val conservationStatus: String? = null,
    @SerializedName("averageLifespan")    val averageLifespan: Int? = null,
    @SerializedName("description")        val description: String? = null
)
