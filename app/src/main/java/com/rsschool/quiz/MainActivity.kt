package com.rsschool.quiz


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.rsschool.quiz.databinding.ActivityMainBinding

//правильные ответы 3,1,3,2,1

class MainActivity : AppCompatActivity(), PrimeFragment.CallBack {

    private lateinit var binding: ActivityMainBinding
    private val quizViewModel: Repository by lazy {
        ViewModelProvider(this).get(Repository::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        selectedQuest(quizViewModel.getCurrentIndex)
    }

    override fun selectedQuest(count: Int) {
        val themeId = selectedTheme(count)
        window.statusBarColor = ContextCompat.getColor(this, selectedColor(count))
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, PrimeFragment.newInstance(themeId)).commit()
    }

    override fun invokeFinishFragment() {
        window.statusBarColor = ContextCompat.getColor(this, selectedColor(0))
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, FinishFragment()).commit()
    }

    private fun selectedTheme(count: Int) = when (count) {
        0 -> R.style.Theme_Quiz_First
        1 -> R.style.Theme_Quiz_Second
        2 -> R.style.Theme_Quiz_Third
        3 -> R.style.Theme_Quiz_Fourth
        else -> R.style.Theme_Quiz_Fifth
    }

    private fun selectedColor(count: Int) = when (count) {
        0 -> R.color.deep_orange_100_dark
        1 -> R.color.yellow_100_dark
        2 -> R.color.light_green_100_dark
        3 -> R.color.cyan_100_dark
        else -> R.color.deep_purple_100_dark
    }

    override fun onBackPressed() {
        quizViewModel.reset()
        super.onBackPressed()
    }
}