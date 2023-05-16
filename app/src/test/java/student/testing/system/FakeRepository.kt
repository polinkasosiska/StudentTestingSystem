package student.testing.system

import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import student.testing.system.domain.DataState
import student.testing.system.domain.MainRepository
import student.testing.system.models.*
import student.testing.system.sharedPreferences.PrefsUtils

class FakeRepository : MainRepository {

    data class MockedUser(val email: String, val password: String)

    override suspend fun auth(request: String): Flow<DataState<PrivateUser>> {
        val existingUsers = listOf(MockedUser("test@mail.ru", "pass"))
        for (user in existingUsers) {
            if (request.contains(user.email) && request.contains("password=${user.password}")) {
                return  flow { emit(DataState.Success(PrivateUser(1, "Ivan", user.email, "some_token"))) }
            }
        }
        return flow { emit(DataState.Error("Incorrect username or password")) }
    }

    override suspend fun signUp(request: SignUpReq): Flow<DataState<PrivateUser>> {
        TODO("Not yet implemented")
    }

    override suspend fun getCourses(): Flow<DataState<List<CourseResponse>>> {
        TODO("Not yet implemented")
    }

    override suspend fun createCourse(name: String): Flow<DataState<CourseResponse>> {
        TODO("Not yet implemented")
    }

    override suspend fun joinCourse(courseCode: String): Flow<DataState<CourseResponse>> {
        val courses = listOf("5TYHKW", "KASTXJ", "XHYX6U")
        if (courseCode in courses) {
            val course = mockk<CourseResponse>(relaxed = true)
            return flow { emit(DataState.Success(course)) }
        } else {
            return flow { emit(DataState.Error("Not found", 404)) }
        }
    }

    override suspend fun deleteCourse(courseId: Int, courseOwnerId: Int): Flow<DataState<Void>> {
        TODO("Not yet implemented")
    }

    override suspend fun getTests(courseId: Int): Flow<DataState<List<Test>>> {
        TODO("Not yet implemented")
    }

    override suspend fun createTest(request: TestCreationReq): Flow<DataState<Test>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTest(
        testId: Int,
        courseId: Int,
        courseOwnerId: Int
    ): Flow<DataState<Void>> {
        TODO("Not yet implemented")
    }

    override suspend fun calculateResult(
        testId: Int,
        courseId: Int,
        request: List<UserQuestion>
    ): Flow<DataState<Void>> {
        TODO("Not yet implemented")
    }

    override suspend fun calculateDemoResult(
        courseId: Int,
        testId: Int,
        courseOwnerId: Int,
        request: List<UserQuestion>
    ): Flow<DataState<TestResult>> {
        TODO("Not yet implemented")
    }

    override suspend fun getResult(testId: Int, courseId: Int): Flow<DataState<TestResult>> {
        if (testId == -1 || courseId == -1) {
            return flow { emit(DataState.Error("Access error", 403)) }
        }
        val passedTests = listOf(12, 24, 13)
        if (passedTests.contains(testId)) {
            return flow { emit(DataState.Success(TestResult(emptyList(), 10, 8.9))) }
        } else {
            return flow { emit(DataState.Error("Not found", 404)) }
        }
    }

    override suspend fun getResults(
        testId: Int,
        courseId: Int,
        courseOwnerId: Int,
        showOnlyMaxResults: Boolean
    ): Flow<DataState<ParticipantsResults>> {
        TODO("Not yet implemented")
    }

    override suspend fun addModerator(
        courseId: Int,
        courseOwnerId: Int,
        moderatorId: Int
    ): Flow<DataState<List<Participant>>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteModerator(
        courseId: Int,
        courseOwnerId: Int,
        moderatorId: Int
    ): Flow<DataState<List<Participant>>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteParticipant(
        courseId: Int,
        courseOwnerId: Int,
        participantId: Int
    ): Flow<DataState<List<Participant>>> {
        TODO("Not yet implemented")
    }
}