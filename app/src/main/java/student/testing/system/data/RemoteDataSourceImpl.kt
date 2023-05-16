package student.testing.system.data

import student.testing.system.models.*
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private val mainService : MainService) :
    RemoteDataSource {
    override suspend fun auth(request: String) = mainService.auth(request)
    override suspend fun signUp(request: SignUpReq) = mainService.signUp(request)
    override suspend fun getCourses() = mainService.getCourses()
    override suspend fun createCourse(request: CourseCreationReq) = mainService.createCourse(request)
    override suspend fun joinCourse(courseCode: String) = mainService.joinCourse(courseCode)
    override suspend fun deleteCourse(courseId: Int) = mainService.deleteCourse(courseId)
    override suspend fun getTests(courseId: Int) = mainService.getTests(courseId)
    override suspend fun createTest(request: TestCreationReq) = mainService.createTest(request)
    override suspend fun deleteTest(testId: Int, courseId: Int) = mainService.deleteTest(testId, courseId)
    override suspend fun calculateResult(testId: Int, courseId: Int, request: List<UserQuestion>) = mainService.calculateResult(testId, courseId, request)
    override suspend fun calculateDemoResult(courseId: Int, testId: Int, request: List<UserQuestion>) = mainService.calculateDemoResult(courseId, testId, request)
    override suspend fun getResult(testId: Int, courseId: Int) = mainService.getResult(testId, courseId)
    override suspend fun getResults(testId: Int, courseId: Int, showOnlyMaxResults: Boolean) = mainService.getResults(testId, courseId, showOnlyMaxResults)
    override suspend fun addModerator(courseId: Int, moderatorId: Int) = mainService.addModerator(courseId, moderatorId)
    override suspend fun deleteModerator(courseId: Int, moderatorId: Int) = mainService.deleteModerator(courseId, moderatorId)
    override suspend fun deleteParticipant(courseId: Int, participantId: Int) = mainService.deleteParticipant(courseId, participantId)
}