package student.testing.system.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CourseResponse(
    var name: String, var id: Int,
    var img: String,
    @SerializedName("course_code")
    var courseCode: String,
    var participants: List<Participant>,
) : Serializable