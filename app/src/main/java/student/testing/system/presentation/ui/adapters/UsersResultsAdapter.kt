package student.testing.system.presentation.ui.adapters

import agency.tango.android.avatarview.IImageLoader
import agency.tango.android.avatarview.loader.PicassoLoader
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import student.testing.system.R
import student.testing.system.databinding.ItemParticipantBinding
import student.testing.system.models.ParticipantResult


class UsersResultsAdapter(
    private val dataList: List<ParticipantResult>,
    private val maxScore: Int
) :
    RecyclerView.Adapter<UsersResultsAdapter.CourseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val binding = ItemParticipantBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return CourseViewHolder(binding)
    }

    override fun getItemId(position: Int) = position.toLong()

    override fun getItemViewType(position: Int) = position

    override fun getItemCount() = dataList.size

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        with(holder.binding) {
            val participantResult: ParticipantResult = dataList[position]
            val imageLoader: IImageLoader = PicassoLoader()
            imageLoader.loadImage(avatarView, "nothing", participantResult.username)
            tvName.text = participantResult.username
            tvMail.text = participantResult.email
            if (participantResult.score % 1.0 != 0.0) {
                tvScore.text = root
                    .context
                    .getString(R.string.participant_result, participantResult.score, maxScore)
            } else {
                tvScore.text = root
                    .context
                    .getString(
                        R.string.participant_int_result,
                        participantResult.score.toInt(),
                        maxScore
                    )
            }
        }
    }

    inner class CourseViewHolder(val binding: ItemParticipantBinding) :
        RecyclerView.ViewHolder(binding.root)
}