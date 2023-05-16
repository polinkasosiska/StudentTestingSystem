package student.testing.system.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AnswerResult(
    val answer: String,
    @SerializedName("is_right") var isRight: Boolean,
    @SerializedName("is_selected") var isSelected: Boolean, val id: Int
): Parcelable