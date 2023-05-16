package student.testing.system.presentation.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import student.testing.system.R
import student.testing.system.common.showSnackbar
import student.testing.system.common.subscribeInUI
import student.testing.system.common.viewBinding
import student.testing.system.databinding.FragmentPassingTestBinding
import student.testing.system.models.Answer
import student.testing.system.models.UserAnswer
import student.testing.system.models.UserQuestion
import student.testing.system.presentation.ui.adapters.AnswersAdapter
import student.testing.system.presentation.viewmodels.TestPassingViewModel
import student.testing.system.presentation.viewmodels.TestsViewModel


@AndroidEntryPoint
class TestPassingFragment : Fragment(R.layout.fragment_passing_test) {

    private val viewModel: TestPassingViewModel by activityViewModels()
    private val binding by viewBinding(FragmentPassingTestBinding::bind)
    private val args: TestPassingFragmentArgs by navArgs()
    private lateinit var adapter: AnswersAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnBackPressedListener()
        val test = args.test
        var position = args.position
        val isUserModerator = args.isUserModerator
        val question = test.questions[position]
        binding.tvQuestion.text = question.question
        binding.tvQuestionNumber.text = getString(R.string.question_number, (position + 1), test.questions.count())
        if (position == test.questions.count() - 1) {
            binding.btnNext.setText(R.string.send)
        }
        adapter = AnswersAdapter(test.questions[position].answers as ArrayList<Answer>, false)
        binding.rv.layoutManager = LinearLayoutManager(requireContext())
        binding.rv.adapter = adapter

        binding.btnNext.setOnClickListener {
            if (!adapter.dataList.any { it.isRight }) {
                showSnackbar(R.string.error_select_answers)
                return@setOnClickListener
            }
            val userAnswers = arrayListOf<UserAnswer>()
            for (ans in adapter.dataList) {
                userAnswers += UserAnswer(ans.id!!, ans.isRight)
            }
            viewModel.userQuestions += UserQuestion(question.id!!, userAnswers)
            if (test.questions.count() - 1 == position) {
                if (isUserModerator) {
                    viewModel.calculateDemoResult(test.courseId, test.id)
                        .subscribeInUI(this, binding.progressBar) {
                            requireActivity().onBackPressed()
                            val action = TestPassingFragmentDirections.viewResult(it)
                            findNavController().navigate(action)
                        }
                } else {
                    viewModel.calculateResult(test.id, test.courseId)
                        .subscribeInUI(this, binding.progressBar) {
                            requireActivity().onBackPressed()
                            requestResult(test.id, test.courseId)
                        }
                }
            } else {
                val action = TestPassingFragmentDirections.actionTestPassingFragmentSelf(test, ++position, isUserModerator)
                findNavController().navigate(action)
            }

        }
    }

    private fun requestResult(testId: Int, courseId: Int) {
        viewModel.getResult(testId, courseId)
            .subscribeInUI(this, binding.progressBar) {
                val action = TestPassingFragmentDirections.viewResult(it)
                findNavController().navigate(action)
            }
    }

    private fun setOnBackPressedListener() {
        requireActivity()
            .onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    viewModel.userQuestions.clear()
                    if (isEnabled) {
                        isEnabled = false
                        requireActivity().onBackPressed()
                    }
                }
            }
            )
    }
}