package student.testing.system.presentation.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onEach
import student.testing.system.R
import student.testing.system.common.*
import student.testing.system.databinding.FragmentTestCreationBinding
import student.testing.system.presentation.ui.adapters.QuestionsAdapter
import student.testing.system.presentation.viewmodels.CourseSharedViewModel
import student.testing.system.presentation.viewmodels.TestCreationViewModel
import student.testing.system.presentation.viewmodels.TestsViewModel
import java.util.*

@AndroidEntryPoint
class TestCreationFragment : Fragment(R.layout.fragment_test_creation) {

    private val sharedViewModel: CourseSharedViewModel by activityViewModels()
    private val testsViewModel by viewModels<TestsViewModel>()
    private val viewModel: TestCreationViewModel by activityViewModels()
    private val binding by viewBinding(FragmentTestCreationBinding::bind)
    lateinit var adapter: QuestionsAdapter

    companion object {
        const val ARG_TEST = "test"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = QuestionsAdapter(arrayListOf()) {
            confirmAction(R.string.delete_request) { _, _ ->
                adapter.deleteItem(it)
            }
        }
        binding.rv.layoutManager = LinearLayoutManager(requireContext())
        binding.rv.adapter = adapter
        binding.btnAdd.setOnClickListener() {
            findNavController().navigate(R.id.action_testCreationFragment_to_questionCreationFragment)
        }
        viewModel.questionsFlow.distinctUntilChanged().onEach {
            adapter.submitData(it)
        }.launchWhenStartedCollect(lifecycleScope)
        binding.btnPublish.setOnClickListener {
            sharedViewModel.courseFlow.distinctUntilChanged().onEach {
                createTest(it.id)
            }.launchWhenStartedCollect(lifecycleScope)
        }
    }

    private fun createTest(courseId: Int) {
        if (!binding.etName.isNotEmpty()) return
        testsViewModel.createTest(
            courseId,
            binding.etName.text.trimString(),
            Date().formatToString("yyyy-MM-dd")!!,
            adapter.dataList
        ).subscribeInUI(this, binding.progressBar) {
            findNavController().previousBackStackEntry?.savedStateHandle?.set(ARG_TEST, it)
            requireActivity().onBackPressed()
        }
    }
}