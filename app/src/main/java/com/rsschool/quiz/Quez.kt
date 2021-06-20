package com.rsschool.quiz

data class Quez(
    val id: String,
    val question: Int,
    val option1: Int,
    val option2: Int,
    val option3: Int,
    val option4: Int,
    val option5: Int,
    var answer: Boolean,
    var selected: Int,
    var selectedOption: String
)
