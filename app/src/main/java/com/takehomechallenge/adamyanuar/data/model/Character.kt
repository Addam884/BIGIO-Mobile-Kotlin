package com.takehomechallenge.adamyanuar.data.model

data class Character(
    val id: Int,
    val name: String,
    val species: String,
    val gender: String,
    val image: String,
    val origin: Origin,
    val location: Location,
    val episode: List<String>
)

data class Origin(
    val name: String
)

data class Location(
    val name: String
)