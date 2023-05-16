package student.testing.system.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ParticipantResult(
    @SerializedName("user_id") val userId: Int,
    val username: String,
    val email: String,
    val score: Double
): Parcelable
