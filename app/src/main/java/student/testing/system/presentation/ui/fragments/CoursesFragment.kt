package student.testing.system.presentation.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.MenuInflater
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import student.testing.system.R
import student.testing.system.models.CourseResponse
import student.testing.system.common.*
import student.testing.system.databinding.FragmentCoursesBinding
import student.testing.system.sharedPreferences.PrefsUtils
import student.testing.system.presentation.ui.activity.LaunchActivity
import student.testing.system.presentation.ui.adapters.CoursesAdapter
import student.testing.system.presentation.ui.dialogFragments.CourseAddingDialogFragment
import student.testing.system.presentation.viewmodels.CoursesViewModel
import javax.inject.Inject


@AndroidEntryPoint
class CoursesFragment : Fragment(R.layout.fragment_courses) {

    private val viewModel by viewModels<CoursesViewModel>()
    private val binding by viewBinding(FragmentCoursesBinding::bind)
    lateinit var adapter: CoursesAdapter

    @Inject
    lateinit var prefUtils: PrefsUtils

    companion object {
        const val KEY_COURSE_ADDING = "courseAdding"
        const val ARG_COURSE = "course"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = CoursesAdapter(object : CoursesAdapter.ClickListener {
            override fun onClick(course: CourseResponse) {
                val bundle = Bundle()
                bundle.putSerializable(ARG_COURSE, course)
                Navigation.findNavController(binding.root)
                    .navigate(R.id.action_coursesFragment_to_courseReviewFragment, bundle)
            }

            override fun onLongClick(courseId: Int) {
                confirmAction(R.string.delete_request) { _, _ ->
                    deleteCourse(courseId)
                }
            }
        })
        binding.rv.layoutManager = LinearLayoutManager(requireContext())
        binding.rv.adapter = adapter
        binding.btnAdd.setOnClickListener() {
            CourseAddingDialogFragment
                .newInstance()
                .show(requireActivity().supportFragmentManager, KEY_COURSE_ADDING);
        }
        setFragmentResultListener()
        binding.btnMenu.setOnClickListener(this::showPopup)
        subscribeObservers()
    }

    private fun subscribeObservers() {
        viewModel.uiState.subscribeInUI(this, binding.progressBar) {
            adapter.setDataList(it as MutableList<CourseResponse>)
        }
    }

    private fun showPopup(v: View) {
        val popup = PopupMenu(requireActivity(), v)
        val inflater: MenuInflater = popup.menuInflater
        inflater.inflate(R.menu.courses_context_menu, popup.menu)
        popup.show()
        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_logout -> {
                    confirmAction(R.string.logout_request) { _, _ ->
                        prefUtils.clearData()
                        requireActivity().finish()
                        startActivity(Intent(requireContext(), LaunchActivity::class.java))
                    }
                }
                R.id.action_who_am_i -> {
                    val account = AccountSession.instance
                    val userInfo = getString(R.string.user_info, account.username, account.email)
                    showInfoDialog(R.string.we_remind_you, userInfo)
                }
            }
            true
        }
    }

    private fun showInfoDialog(@StringRes title: Int, message: String) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(R.string.thanks, null)
            .show()
    }

    private fun setFragmentResultListener() {
        requireActivity()
            .supportFragmentManager
            .setFragmentResultListener(KEY_COURSE_ADDING, this) { requestKey, bundle ->
                val result = bundle.getSerializable(ARG_COURSE)
                adapter.addItem(result as CourseResponse)
            }
    }

    private fun deleteCourse(courseId: Int) {
        viewModel.deleteCourse(courseId).subscribeInUI(this, binding.progressBar) {
            adapter.deleteById(it)
        }
    }
}