package com.bd.domain.model

data class Book(
    val title: String,
    val imageUrl: String?,
    val authors: List<String>
)
