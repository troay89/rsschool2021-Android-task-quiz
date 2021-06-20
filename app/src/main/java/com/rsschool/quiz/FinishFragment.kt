package com.rsschool.quiz

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.rsschool.quiz.databinding.FragmentFinishBinding
import kotlin.system.exitProcess


class FinishFragment : Fragment() {

    private var _binding: FragmentFinishBinding? = null
    private val binding get() = _binding!!
    private var callBack: PrimeFragment.CallBack? = null
    private val quizViewModel: Repository by lazy {
        ViewModelProvider(this).get(Repository::class.java)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callBack = context as PrimeFragment.CallBack
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFinishBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val  result = getString(R.string.finish_result, quizViewModel.result())
        val send = getString(R.string.send, result, getString(R.string.quiz1),getString(R.string.your_answer),
            quizViewModel.getOption(0),getString(R.string.quiz2),getString(R.string.your_answer), quizViewModel.getOption(1),
            getString(R.string.quiz3),getString(R.string.your_answer), quizViewModel.getOption(2), getString(R.string.quiz4), getString(R.string.your_answer),
            quizViewModel.getOption(3), getString(R.string.quiz5), getString(R.string.your_answer), quizViewModel.getOption(4))
        quizViewModel.reset()
        binding.textView.text = result
        binding.share.setOnClickListener {
            Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_SUBJECT, getString(R.string.topic))
                putExtra(Intent.EXTRA_TEXT, send)
            }.also {
                    intent ->
//                    startActivity(intent)
                val chooserIntent = Intent.createChooser(intent, getString(R.string.send_report))
                startActivity(chooserIntent)
            }
        }

        binding.anew.setOnClickListener {
            callBack?.selectedQuest(0)
        }

        binding.close.setOnClickListener{
            exitProcess(0)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        callBack = null
        super.onDestroy()
    }
}