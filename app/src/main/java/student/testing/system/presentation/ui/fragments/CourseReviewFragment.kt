package student.testing.system.presentation.ui.fragments

import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.fragment.app.Fragment
import androidx.navigation.NavArgument
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import student.testing.system.R
import student.testing.system.common.showIf
import student.testing.system.common.viewBinding
import student.testing.system.models.CourseResponse
import student.testing.system.databinding.FragmentCourseReviewBinding

class CourseReviewFragment : Fragment(R.layout.fragment_course_review) {

    private val binding by viewBinding(FragmentCourseReviewBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments == null) return
        val course = arguments?.getSerializable(CoursesFragment.ARG_COURSE) as CourseResponse


        val navView: BottomNavigationView = binding.navView
        val navController =
            requireActivity().findNavController(R.id.nav_host_fragment_activity_course_review)
        val arg1 = NavArgument.Builder().setDefaultValue(course).build()
        val navInflater = navController.navInflater
        val navGraph = navInflater.inflate(R.navigation.course_review_navigation)
        navGraph.addArgument(CoursesFragment.ARG_COURSE, arg1)
        navController.graph = navGraph
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.navigation_users -> {
                    binding.navView.showIf(true)
                    destination.addArgument(CoursesFragment.ARG_COURSE, arg1)
                }
                R.id.navigation_tests -> {
                    binding.navView.showIf(true)
                }
                else -> {
                    binding.navView.showIf(false)
                }
            }
        }
        navView.setupWithNavController(navController)
    }
}