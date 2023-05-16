package student.testing.system.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Participant(
    val email: String,
    val username: String,
    @SerializedName("user_id") val userId: Int,
    @SerializedName("is_moderator") val isModerator: Boolean,
    @SerializedName("is_owner") val isOwner: Boolean
) : Serializable