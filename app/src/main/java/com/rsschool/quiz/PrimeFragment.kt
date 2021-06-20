package com.rsschool.quiz

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.rsschool.quiz.databinding.FragmentPrimeBinding


class PrimeFragment : Fragment() {

    interface CallBack {
        fun selectedQuest(count: Int)
        fun invokeFinishFragment()
    }

    private var _binding: FragmentPrimeBinding? = null
    private val binding get() = _binding!!
    private var callBack: CallBack? = null
    private val quizViewModel: Repository by lazy {
        ViewModelProvider(this).get(Repository::class.java)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callBack = context as CallBack
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val idTheme = arguments?.getInt(THEME_ID_KEY)
        if (idTheme != null) activity?.setTheme(idTheme)
        _binding = FragmentPrimeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var click: Int

        binding.nextButton.setOnClickListener {
            quizViewModel.nextCurrentIndex()
            if(quizViewModel.getCurrentIndex > 4) callBack?.invokeFinishFragment()
            else callBack?.selectedQuest(quizViewModel.getCurrentIndex)
        }

        if(quizViewModel.getCurrentIndex < 5){
            click = quizViewModel.getCurrentSelected
            updateUI(click)
        }

        binding.previousButton.setOnClickListener {
            quizViewModel.previousCurrentIndex()
            callBack?.selectedQuest(quizViewModel.getCurrentIndex)
        }

        binding.toolbar.setNavigationOnClickListener {
            quizViewModel.previousCurrentIndex()
            callBack?.selectedQuest(quizViewModel.getCurrentIndex)
        }

        binding.radioGroup.setOnCheckedChangeListener{ _, checkedId ->
            click = clickRadioId(checkedId)
            val option = selectedOption(click)
            quizViewModel.capturingOptions(option)
            quizViewModel.setCurrentSelected(click)
            if(quizViewModel.getCurrentSelected > 1) binding.nextButton.isEnabled = true
        }
    }

    private fun updateUI(click: Int) {
        binding.toolbar.title = quizViewModel.getIdCurrentQuestion
        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_chevron_left_24)
        binding.nextButton.text = getString(R.string.next)
        if (quizViewModel.getCurrentIndex == 4) binding.nextButton.text = getString(R.string.submit)
        if (quizViewModel.getCurrentIndex < 1) {
            binding.previousButton.isEnabled = false
            binding.toolbar.navigationIcon = null
        }
        if(quizViewModel.getCurrentSelected < 1) binding.nextButton.isEnabled = false
        binding.radioGroup.check(click)

        binding.question.text = getString(quizViewModel.getCurrentQuestion)
        binding.optionOne.text = getString(quizViewModel.option1)
        binding.optionTwo.text = getString(quizViewModel.option2)
        binding.optionThree.text = getString(quizViewModel.option3)
        binding.optionFour.text = getString(quizViewModel.option4)
        binding.optionFive.text = getString(quizViewModel.option5)
    }

    private fun clickRadioId(checkedId: Int) = when (checkedId) {
        R.id.option_one -> R.id.option_one
        R.id.option_two -> R.id.option_two
        R.id.option_three -> R.id.option_three
        R.id.option_four -> R.id.option_four
        R.id.option_five -> R.id.option_five
        else -> 0
    }

    private fun selectedOption(click: Int) = when (click) {
        R.id.option_one -> getString(quizViewModel.option1)
        R.id.option_two -> getString(quizViewModel.option2)
        R.id.option_three -> getString(quizViewModel.option3)
        R.id.option_four -> getString(quizViewModel.option4)
        R.id.option_five -> getString(quizViewModel.option5)
        else -> ""
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        callBack = null
        super.onDestroy()
    }

    companion object {

        fun newInstance(idTheme: Int): PrimeFragment{
            val fragment = PrimeFragment()
            val args = bundleOf()
            args.putInt(THEME_ID_KEY, idTheme)
            fragment.arguments = args
            return fragment
        }

        private const val THEME_ID_KEY = "THEME_ID"
    }
}