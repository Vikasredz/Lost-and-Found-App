package com.example.lostandfoundapp

data class ItemModel(
    val id: Int,
    val type: String,
    val name: String,
    val phone: String,
    val description: String,
    val date: String,
    val location: String,
    val category: String,
    val imageUri: String,
    val createdAt: String
)