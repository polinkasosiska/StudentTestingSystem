package student.testing.system.data

import retrofit2.Response
import retrofit2.http.*
import student.testing.system.models.*
import javax.inject.Inject

interface RemoteDataSource {

    suspend fun auth(request: String): Response<PrivateUser>

    suspend fun signUp(request: SignUpReq): Response<PrivateUser>

    suspend fun getCourses(): Response<List<CourseResponse>>

    suspend fun createCourse(request: CourseCreationReq): Response<CourseResponse>

    suspend fun joinCourse(courseCode: String): Response<CourseResponse>

    suspend fun deleteCourse(courseId: Int): Response<Void>

    suspend fun getTests(courseId: Int): Response<List<Test>>

    suspend fun createTest(request: TestCreationReq): Response<Test>

    suspend fun deleteTest(testId: Int, courseId: Int): Response<Void>

    suspend fun calculateResult(
        testId: Int,
        courseId: Int,
        request: List<UserQuestion>
    ): Response<Void>

    suspend fun calculateDemoResult(
        courseId: Int,
        testId: Int,
        request: List<UserQuestion>
    ): Response<TestResult>

    suspend fun getResult(testId: Int, courseId: Int): Response<TestResult>

    suspend fun getResults(
        testId: Int,
        courseId: Int,
        showOnlyMaxResults: Boolean
    ): Response<ParticipantsResults>

    suspend fun addModerator(
        courseId: Int,
        moderatorId: Int
    ): Response<List<Participant>>

    suspend fun deleteModerator(
        courseId: Int,
        moderatorId: Int
    ): Response<List<Participant>>

    suspend fun deleteParticipant(
        courseId: Int,
        participantId: Int
    ): Response<List<Participant>>
}