package student.testing.system.domain.usecases

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertTrue
import org.junit.Test
import student.testing.system.FakeRepository
import student.testing.system.domain.DataState
import student.testing.system.models.CourseResponse

@ExperimentalCoroutinesApi
class JoinCourseUseCaseTest {

    private val repository = FakeRepository()
    private val joinCourseUseCase = JoinCourseUseCase(repository)

    @Test
    fun `empty courseCode returns ValidationError`() = runTest {
        val actual = joinCourseUseCase("").first()
        assertTrue(actual is DataState.ValidationError)
    }

    @Test
    fun `failed response returns Error`() = runTest {
        val actual = joinCourseUseCase("QQQQQQ").first()
        assertTrue(actual is DataState.Error)
    }

    @Test
    fun `success response returns CourseResponse`() = runTest {
        val actual = joinCourseUseCase("5TYHKW").first()
        assertTrue(actual is DataState.Success)
        assertThat((actual as DataState.Success).data, instanceOf(CourseResponse::class.java))
    }
}