package student.testing.system.domain.login

import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import student.testing.system.models.PrivateUser
import student.testing.system.sharedPreferences.PrefsUtils
import student.testing.system.R
import student.testing.system.FakeRepository


@ExperimentalCoroutinesApi
class AuthUseCaseTest {

    private val repository = FakeRepository()
    private val prefsUtils = mockk<PrefsUtils>(relaxed = true)
    private val authUseCase = AuthUseCase(repository, prefsUtils)

    @Test
    fun `empty email returns EmailError`() = runTest {
        val expected = LoginState.EmailError(R.string.error_empty_field)
        val actual = authUseCase.invoke("", "").first()
        assertEquals(expected, actual)
    }

    @Test
    fun `invalid E-mail format returns EmailError`() = runTest {
        val expected = LoginState.EmailError(R.string.error_invalid_email)
        val actual = authUseCase.invoke("someEmail", "").first()
        assertEquals(expected, actual)
    }

    @Test
    fun `empty password returns PasswordError`() = runTest {
        val expected = LoginState.PasswordError(R.string.error_empty_field)
        val actual = authUseCase.invoke("test@mail.ru", "").first()
        assertEquals(expected, actual)
    }

    @Test
    fun `success auth returns PrivateUser`() = runBlocking {
        val actual = authUseCase.invoke("test@mail.ru", "pass").first()
        assertTrue(actual is LoginState.Success)
        assertThat((actual as LoginState.Success).data, instanceOf(PrivateUser::class.java))
    }

    @Test
    fun `failed auth returns Error`() = runBlocking {
        val expected = LoginState.Error("Incorrect username or password")
        val actual = authUseCase.invoke("other@mail.ru", "pass").first()
        assertEquals(expected, actual)
    }
}