package student.testing.system.data

import retrofit2.Response
import retrofit2.http.*
import student.testing.system.models.*
import student.testing.system.models.CourseResponse
import student.testing.system.models.TestResult
import student.testing.system.models.Test


interface MainService {

    @POST("auth/sign-in")
    @Headers("accept: application/json", "Content-Type: application/x-www-form-urlencoded")
    suspend fun auth(@Body request: String): Response<PrivateUser>

    @POST("auth/sign-up")
    suspend fun signUp(@Body request: SignUpReq): Response<PrivateUser>

    @GET("courses/")
    suspend fun getCourses(): Response<List<CourseResponse>>

    @POST("courses/")
    suspend fun createCourse(@Body request: CourseCreationReq): Response<CourseResponse>

    @POST("courses/{course_code}")
    suspend fun joinCourse(@Path("course_code") courseCode: String): Response<CourseResponse>

    @DELETE("courses/{course_id}")
    suspend fun deleteCourse(
        @Path("course_id") courseId: Int,
    ): Response<Void>

    @GET("tests/")
    suspend fun getTests(@Query("course_id") courseId: Int): Response<List<Test>>

    @POST("tests/")
    suspend fun createTest(@Body request: TestCreationReq): Response<Test>

    @DELETE("tests/{test_id}")
    suspend fun deleteTest(
        @Path("test_id") testId: Int,
        @Query("course_id") courseId: Int,
    ): Response<Void>

    @POST("tests/{test_id}")
    suspend fun calculateResult(
        @Path("test_id") testId: Int,
        @Query("course_id") courseId: Int,
        @Body request: List<UserQuestion>
    ): Response<Void>

    @POST("tests/demo_result/")
    suspend fun calculateDemoResult(
        @Query("course_id") courseId: Int,
        @Query("test_id") testId: Int,
        @Body request: List<UserQuestion>
    ): Response<TestResult>

    @GET("tests/result/{test_id}")
    suspend fun getResult(
        @Path("test_id") testId: Int,
        @Query("course_id") courseId: Int
    ): Response<TestResult>

    @GET("tests/results/{test_id}")
    suspend fun getResults(
        @Path("test_id") testId: Int,
        @Query("course_id") courseId: Int,
        @Query("only_max_result") showOnlyMaxResults: Boolean
    ): Response<ParticipantsResults>

    @POST("course/moderators/")
    suspend fun addModerator(
        @Query("course_id") courseId: Int,
        @Query("moderator_id") moderatorId: Int
    ): Response<List<Participant>>

    @DELETE("course/moderators/")
    suspend fun deleteModerator(
        @Query("course_id") courseId: Int,
        @Query("moderator_id") moderatorId: Int
    ): Response<List<Participant>>

    @DELETE("/course/management/participants")
    suspend fun deleteParticipant(
        @Query("course_id") courseId: Int,
        @Query("participant_id") participantId: Int
    ): Response<List<Participant>>
}