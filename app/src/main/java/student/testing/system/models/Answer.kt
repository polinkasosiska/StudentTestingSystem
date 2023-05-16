package student.testing.system.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Answer(
    val answer: String,
    @field:SerializedName("is_right") var isRight: Boolean, val id: Int? = null
): Parcelable