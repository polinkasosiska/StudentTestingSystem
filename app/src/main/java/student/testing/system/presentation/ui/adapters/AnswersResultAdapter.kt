package student.testing.system.presentation.ui.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import student.testing.system.databinding.ItemMultiAnswerBinding
import student.testing.system.models.AnswerResult


class AnswersResultAdapter(val dataList: ArrayList<AnswerResult>) :
    RecyclerView.Adapter<AnswersResultAdapter.CourseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val binding = ItemMultiAnswerBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return CourseViewHolder(binding)
    }

    override fun getItemId(position: Int) = position.toLong()

    override fun getItemViewType(position: Int) = position

    override fun getItemCount() = dataList.size

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        with(holder) {
            val answer = dataList[position]
            binding.checkBox.text = answer.answer
            binding.checkBox.isChecked = answer.isSelected
            binding.checkBox.setTextColor(if (answer.isRight) Color.GREEN else Color.RED)
            binding.checkBox.isEnabled = false
        }
    }

    inner class CourseViewHolder(val binding: ItemMultiAnswerBinding) :
        RecyclerView.ViewHolder(binding.root)
}