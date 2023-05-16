package student.testing.system.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class TestResult(
    val questions: List<QuestionResult>,
    @SerializedName("max_score") val maxScore: Int,
    val score: Double
): Parcelable
