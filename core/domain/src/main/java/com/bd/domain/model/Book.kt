package com.bd.domain.model

data class Book(
    val id: String,
    val title: String,
    val imageUrl: String?,
    val authors: List<String>
)
