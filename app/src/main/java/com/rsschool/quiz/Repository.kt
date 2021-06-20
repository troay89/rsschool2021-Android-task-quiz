package com.rsschool.quiz

import androidx.lifecycle.ViewModel

private var currentIndex = 0

private var dataList = listOf(
    Quez("Question1",R.string.quiz1, R.string.answer1_1, R.string.answer1_2,R.string.answer1_3,R.string.answer1_4,R.string.answer1_5,false, 0,""),
    Quez("Question2",R.string.quiz2, R.string.answer2_1, R.string.answer2_2,R.string.answer2_3,R.string.answer2_4,R.string.answer2_5, false, 0,""),
    Quez("Question3",R.string.quiz3, R.string.answer3_1, R.string.answer3_2,R.string.answer3_3,R.string.answer3_4,R.string.answer3_5, false, 0,""),
    Quez("Question4",R.string.quiz4, R.string.answer4_1, R.string.answer4_2,R.string.answer4_3,R.string.answer4_4,R.string.answer4_5, false, 0,""),
    Quez("Question5",R.string.quiz5, R.string.answer5_1, R.string.answer5_2,R.string.answer5_3,R.string.answer5_4,R.string.answer5_5, false, 0,""),
)

class Repository: ViewModel() {

    //3,1,3,2,1
    val getCurrentSelected: Int get() = dataList[currentIndex].selected
    val getCurrentQuestion get() = dataList[currentIndex].question
    val getCurrentIndex get() = currentIndex
    val getIdCurrentQuestion get() = dataList[currentIndex].id
    val option1 get() = dataList[currentIndex].option1
    val option2 get() = dataList[currentIndex].option2
    val option3 get() = dataList[currentIndex].option3
    val option4 get() = dataList[currentIndex].option4
    val option5 get() = dataList[currentIndex].option5

    fun nextCurrentIndex(){
        currentIndex++
    }

    fun previousCurrentIndex(){
        currentIndex--
    }

    fun capturingOptions(option: String){
        dataList[currentIndex].selectedOption = option
    }

    fun setCurrentSelected(selected: Int) {
        dataList[currentIndex].selected = selected
        when(currentIndex) {
            0 -> dataList[currentIndex].answer = selected == R.id.option_three
            1 -> dataList[currentIndex].answer = selected == R.id.option_one
            2 -> dataList[currentIndex].answer = selected == R.id.option_three
            3 -> dataList[currentIndex].answer = selected == R.id.option_two
            4 -> dataList[currentIndex].answer = selected == R.id.option_one
        }
    }

    fun result():Int {
        var count = 0
        for (right  in dataList){
            if(right.answer) count++
        }
        return 100 / 5 * count
    }

    fun getOption(selected: Int):String {
        return dataList[selected].selectedOption
    }

    fun reset() {
        currentIndex = 0
        val reset = listOf(
            Quez("Question1", R.string.quiz1, R.string.answer1_1, R.string.answer1_2,R.string.answer1_3,R.string.answer1_4,R.string.answer1_5,false, 0,""),
            Quez("Question2",R.string.quiz2, R.string.answer2_1, R.string.answer2_2,R.string.answer2_3,R.string.answer2_4,R.string.answer2_5, false, 0,""),
            Quez("Question3",R.string.quiz3, R.string.answer3_1, R.string.answer3_2,R.string.answer3_3,R.string.answer3_4,R.string.answer3_5, false, 0,""),
            Quez("Question4",R.string.quiz4, R.string.answer4_1, R.string.answer4_2,R.string.answer4_3,R.string.answer4_4,R.string.answer4_5, false, 0,""),
            Quez("Question5",R.string.quiz5, R.string.answer5_1, R.string.answer5_2,R.string.answer5_3,R.string.answer5_4,R.string.answer5_5, false, 0,""),
        )
        dataList = reset
    }
}