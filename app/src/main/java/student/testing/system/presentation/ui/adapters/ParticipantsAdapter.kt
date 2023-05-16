package student.testing.system.presentation.ui.adapters

import agency.tango.android.avatarview.IImageLoader
import agency.tango.android.avatarview.loader.PicassoLoader
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import student.testing.system.R
import student.testing.system.models.Participant
import student.testing.system.common.AccountSession
import student.testing.system.common.showIf
import student.testing.system.databinding.ItemParticipantBinding


class ParticipantsAdapter(
    private var dataList: List<Participant>, private val currentParticipant: Participant
) :
    RecyclerView.Adapter<ParticipantsAdapter.CourseViewHolder>() {

    var listener: (View, Participant) -> Unit = { _, _ ->

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val binding = ItemParticipantBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return CourseViewHolder(binding)
    }

    fun updateDataList(dataList: List<Participant>) {
        this.dataList = dataList
        notifyDataSetChanged()
    }

    override fun getItemId(position: Int) = position.toLong()

    override fun getItemViewType(position: Int) = position

    override fun getItemCount() = dataList.size

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        with(holder) {
            val participant: Participant = dataList[position]
            val imageLoader: IImageLoader = PicassoLoader()
            imageLoader.loadImage(binding.avatarView, "nothing", participant.username)
            binding.tvName.text = participant.username
            binding.tvMail.text = participant.email
            if (currentParticipant.isOwner && !participant.isOwner) {
                binding.btnMenu.showIf(true)
                binding.btnMenu.setOnClickListener {
                    listener.invoke(binding.btnMenu, participant)
                }
            }
            binding.ivStar.showIf(participant.isOwner || participant.isModerator)
            if (participant.isModerator) {
                binding.ivStar.setColorFilter(R.color.gray)
            }
        }
    }

    inner class CourseViewHolder(val binding: ItemParticipantBinding) :
        RecyclerView.ViewHolder(binding.root)
}