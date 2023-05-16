package student.testing.system.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import student.testing.system.databinding.ItemTestBinding
import student.testing.system.models.Test

class TestsAdapter(private val isUserModerator: Boolean, private val listener: ClickListener) :
    RecyclerView.Adapter<TestsAdapter.CourseViewHolder>() {

    private var dataList = mutableListOf<Test>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val binding = ItemTestBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return CourseViewHolder(binding)
    }

    fun addItem(item: Test) {
        dataList += item
        notifyItemChanged(itemCount)
    }

    fun setDataList(dataList: MutableList<Test>) {
        this.dataList = dataList
        notifyDataSetChanged()
    }

    fun deleteById(id: Int) {
        val position = getPositionById(id)
        dataList.removeAt(position)
        notifyItemRemoved(position)
    }

    private fun getPositionById(id: Int): Int {
        return dataList.indexOf(dataList.find { it.id == id })
    }

    override fun getItemId(position: Int) = position.toLong()

    override fun getItemViewType(position: Int) = position

    override fun getItemCount() = dataList.size

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        with(holder) {
            val test = dataList[position]
            binding.name.text = test.name
            binding.date.text = test.creationTime
            holder.itemView.setOnClickListener() {
                listener.onClick(test)
            }
            if (!isUserModerator) return
            holder.itemView.setOnLongClickListener() {
                listener.onLongClick(test)
                true
            }
        }
    }

    inner class CourseViewHolder(val binding: ItemTestBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface ClickListener {
        fun onClick(test: Test)
        fun onLongClick(test: Test)
    }

}