package student.testing.system.presentation.ui.dialogFragments

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import student.testing.system.R
import student.testing.system.common.showSnackbar
import student.testing.system.common.subscribeInUI
import student.testing.system.common.trimString
import student.testing.system.databinding.DialogFragmentCourseAddingBinding
import student.testing.system.presentation.ui.fragments.CoursesFragment.Companion.ARG_COURSE
import student.testing.system.presentation.ui.fragments.CoursesFragment.Companion.KEY_COURSE_ADDING
import student.testing.system.presentation.viewmodels.CourseAddingViewModel


@AndroidEntryPoint
class CourseAddingDialogFragment : BottomSheetDialogFragment() {

    private val viewModel by viewModels<CourseAddingViewModel>()
    private var _binding: DialogFragmentCourseAddingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = DialogFragmentCourseAddingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.create.setOnClickListener() {
            showAlertDialog(R.string.create_course, R.string.input_name, R.string.create) {
                viewModel.createCourse(it)
            }
        }
        binding.join.setOnClickListener() {
            showAlertDialog(R.string.join_course, R.string.course_code_hint, R.string.join) {
                viewModel.joinCourse(it)
            }
        }
        subscribeObservers()
    }

    private fun subscribeObservers() {
        viewModel.uiState.subscribeInUI(this, binding.progressBar) {
            val result = Bundle()
            result.putSerializable(ARG_COURSE, it)
            parentFragmentManager.setFragmentResult(KEY_COURSE_ADDING, result)
            dismiss()
        }
    }

    private fun showAlertDialog(
        title: Int,
        hint: Int,
        positiveBtnText: Int,
        listener: (String) -> Unit
    ) {
        val builder = MaterialAlertDialogBuilder(requireContext())
        builder.setTitle(title)
        val input = EditText(requireContext())
        input.hint = getString(hint)
        input.inputType = InputType.TYPE_TEXT_FLAG_CAP_SENTENCES
        builder.setView(input)
        builder.setPositiveButton(positiveBtnText) { _, _ ->
            listener.invoke(input.text.trimString())
        }
        builder.setNegativeButton(R.string.cancel, null)
        builder.show()
    }

    companion object {
        fun newInstance(): CourseAddingDialogFragment {
            return CourseAddingDialogFragment()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}