package student.testing.system.presentation.ui.fragments

import android.os.Bundle
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import student.testing.system.R
import student.testing.system.common.subscribeInUI
import student.testing.system.common.viewBinding
import student.testing.system.databinding.FragmentResultsReviewBinding
import student.testing.system.presentation.ui.adapters.UsersResultsAdapter
import student.testing.system.presentation.viewmodels.ResultsViewModel


@AndroidEntryPoint
class ResultsReviewFragment : Fragment(R.layout.fragment_results_review) {

    private val binding by viewBinding(FragmentResultsReviewBinding::bind)
    private val args: ResultsReviewFragmentArgs by navArgs()
    private lateinit var adapter: UsersResultsAdapter
    private var showOnlyMaxResults = false
    private val viewModel by viewModels<ResultsViewModel>()

    companion object {
        const val KEY_SHOW_ONLY_MAX_RESULTS = "showOnlyMaxResults"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showOnlyMaxResults = savedInstanceState?.getBoolean(KEY_SHOW_ONLY_MAX_RESULTS) ?: false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getResults(args.testId, args.courseId)
        binding.btnMenu.setOnClickListener(this::showPopup)
        subscribeObservers()
    }

    private fun subscribeObservers() {
        viewModel.uiState.subscribeInUI(this, binding.progressBar) {
            adapter = UsersResultsAdapter(it.results, it.maxScore)
            binding.rv.layoutManager = LinearLayoutManager(requireContext())
            binding.rv.adapter = adapter
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(KEY_SHOW_ONLY_MAX_RESULTS, showOnlyMaxResults)
    }

    private fun showPopup(v: View) {
        val popup = PopupMenu(requireActivity(), v)
        val inflater: MenuInflater = popup.menuInflater
        inflater.inflate(R.menu.results_context_menu, popup.menu)
        popup.show()
        popup.menu.getItem(0)
            .setTitle(if (showOnlyMaxResults) R.string.all_results else R.string.max_results)
        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.results_filter -> {
                    showOnlyMaxResults = !showOnlyMaxResults
                    viewModel.getResults(args.testId, args.courseId, showOnlyMaxResults)
                }
            }
            true
        }
    }
}