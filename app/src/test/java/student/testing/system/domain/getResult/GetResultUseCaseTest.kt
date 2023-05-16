package student.testing.system.domain.getResult

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import student.testing.system.FakeRepository
import student.testing.system.models.TestResult


@ExperimentalCoroutinesApi
class GetResultUseCaseTest {

    private val repository = FakeRepository()
    private val getResultUseCase = GetResultUseCase(repository)

    @Test
    fun `when 404 returns NoResult state`() = runTest {
        val expected = ResultState.NoResult
        val actual = getResultUseCase(1, 1).first()
        assertEquals(expected, actual)
    }

    @Test
    fun `all errors except 404 returns Error state`() = runTest {
        val actual = getResultUseCase(-1, 1).first()
        assertTrue(actual is ResultState.Error)
    }

    @Test
    fun `success response returns TestResult`() = runTest {
        val actual = getResultUseCase(13, 1).first()
        assertTrue(actual is ResultState.Success)
        assertThat((actual as ResultState.Success).data, instanceOf(TestResult::class.java))
    }
}