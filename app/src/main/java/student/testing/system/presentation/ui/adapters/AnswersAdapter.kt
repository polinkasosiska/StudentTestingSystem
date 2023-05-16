package student.testing.system.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import student.testing.system.databinding.ItemMultiAnswerBinding
import student.testing.system.models.Answer


class AnswersAdapter(
    val dataList: ArrayList<Answer>,
    private val forCreating: Boolean,
    private val listener: (Int) -> Unit = {}
) :
    RecyclerView.Adapter<AnswersAdapter.CourseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val binding = ItemMultiAnswerBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return CourseViewHolder(binding)
    }

    fun addItem(item: Answer) {
        dataList += item
        notifyItemChanged(itemCount)
    }

    fun deleteItem(position: Int) {
        dataList.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun getItemId(position: Int) = position.toLong()

    override fun getItemViewType(position: Int) = position

    override fun getItemCount() = dataList.size

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        with(holder) {
            val answer = dataList[position]
            binding.checkBox.text = answer.answer
            binding.checkBox.setOnCheckedChangeListener { _, isChecked ->
                answer.isRight = isChecked
            }
            if (!forCreating) return
            binding.checkBox.setOnLongClickListener {
                listener.invoke(position)
                true
            }
        }
    }

    inner class CourseViewHolder(val binding: ItemMultiAnswerBinding) :
        RecyclerView.ViewHolder(binding.root)
}