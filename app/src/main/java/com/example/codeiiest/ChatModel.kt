package com.example.codeiiest

data class ChatModel(
    val userName: String,
    val userImage: String,
    val dateTime: String,
    val message: String
) {
    constructor() : this("","","","")
}