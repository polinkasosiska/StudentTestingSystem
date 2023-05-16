package student.testing.system.models

import com.google.gson.annotations.SerializedName

data class CourseCreationReq(val name: String)

data class SignUpReq(val email: String, val username: String, val password: String)

data class TestCreationReq(
    @SerializedName("course_id") val courseId: Int,
    val name: String,
    @SerializedName("creation_time") val creationTIme: String,
    val questions: List<Question>
)

data class UserQuestion(
    @SerializedName("question_id") val questionId: Int,
    val answers: List<UserAnswer>
)

data class UserAnswer(
    @SerializedName("answer_id") val answerId: Int,
    @SerializedName("is_selected") val isSelected: Boolean
)
