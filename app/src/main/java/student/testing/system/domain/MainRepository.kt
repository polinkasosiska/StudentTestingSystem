package student.testing.system.domain

import kotlinx.coroutines.flow.Flow
import student.testing.system.models.*

interface MainRepository {
    suspend fun auth(request: String): Flow<DataState<PrivateUser>>

    suspend fun signUp(request: SignUpReq): Flow<DataState<PrivateUser>>

    suspend fun getCourses(): Flow<DataState<List<CourseResponse>>>

    suspend fun createCourse(name: String): Flow<DataState<CourseResponse>>

    suspend fun joinCourse(courseCode: String): Flow<DataState<CourseResponse>>

    suspend fun deleteCourse(courseId: Int): Flow<DataState<Void>>

    suspend fun getTests(courseId: Int): Flow<DataState<List<Test>>>

    suspend fun createTest(request: TestCreationReq): Flow<DataState<Test>>

    suspend fun deleteTest(testId: Int, courseId: Int): Flow<DataState<Void>>

    suspend fun calculateResult(
        testId: Int,
        courseId: Int,
        request: List<UserQuestion>
    ): Flow<DataState<Void>>

    suspend fun calculateDemoResult(
        courseId: Int,
        testId: Int,
        request: List<UserQuestion>
    ): Flow<DataState<TestResult>>

    suspend fun getResult(testId: Int, courseId: Int): Flow<DataState<TestResult>>

    suspend fun getResults(
        testId: Int,
        courseId: Int,
        showOnlyMaxResults: Boolean
    ): Flow<DataState<ParticipantsResults>>

    suspend fun addModerator(
        courseId: Int,
        moderatorId: Int
    ): Flow<DataState<List<Participant>>>

    suspend fun deleteModerator(
        courseId: Int,
        moderatorId: Int
    ): Flow<DataState<List<Participant>>>

    suspend fun deleteParticipant(
        courseId: Int,
        participantId: Int
    ): Flow<DataState<List<Participant>>>
}