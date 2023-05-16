package student.testing.system.presentation.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import student.testing.system.R
import student.testing.system.common.viewBinding
import student.testing.system.databinding.FragmentResultReviewBinding
import student.testing.system.presentation.ui.adapters.UserResultAdapter


@AndroidEntryPoint
class ResultReviewFragment : Fragment(R.layout.fragment_result_review) {

    private val binding by viewBinding(FragmentResultReviewBinding::bind)
    private val args: ResultReviewFragmentArgs by navArgs()
    private lateinit var adapter: UserResultAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val testResult = args.testResult
        adapter = UserResultAdapter(testResult)
        binding.rv.layoutManager = LinearLayoutManager(requireContext())
        binding.rv.adapter = adapter
        if (testResult.score % 1.0 != 0.0) {
            binding.tvResult.text =
                getString(R.string.total_user_result, testResult.score, testResult.maxScore)
        } else {
            binding.tvResult.text = getString(
                R.string.total_user_result_int,
                testResult.score.toInt(),
                testResult.maxScore
            )
        }
    }
}