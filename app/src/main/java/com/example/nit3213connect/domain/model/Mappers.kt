package com.example.nit3213connect.domain.model

import com.example.nit3213connect.data.remote.EntityRemote

fun EntityRemote.toDomain(): Entity {
    val title = (species ?: scientificName ?: "Untitled").trim()
    val subtitle = (scientificName ?: species ?: "").trim()

    val extras = buildList {
        habitat?.takeIf { it.isNotBlank() }?.let { add("Habitat: $it") }
        diet?.takeIf { it.isNotBlank() }?.let { add("Diet: $it") }
        conservationStatus?.takeIf { it.isNotBlank() }?.let { add("Status: $it") }
        averageLifespan?.let { add("Lifespan: $it yrs") }
    }.joinToString(" • ")

    val desc = buildString {
        description?.takeIf { it.isNotBlank() }?.let { append(it) }
        if (extras.isNotBlank()) {
            if (isNotEmpty()) append("\n")
            append(extras)
        }
    }

    return Entity(title = title, subtitle = subtitle, description = desc)
}
