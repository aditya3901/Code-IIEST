package com.example.codeiiest

data class PostData(
    val title: String,
    val context: String,
    val link: String,
    val dateTime: String
) {
    constructor() : this("","","","")
}
