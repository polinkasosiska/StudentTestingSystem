package student.testing.system.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import student.testing.system.R
import student.testing.system.databinding.ItemUserResultBinding
import student.testing.system.models.AnswerResult
import student.testing.system.models.TestResult


class UserResultAdapter(private val testResult: TestResult) :
    RecyclerView.Adapter<UserResultAdapter.CourseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val binding = ItemUserResultBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return CourseViewHolder(binding)
    }

    override fun getItemId(position: Int) = position.toLong()

    override fun getItemViewType(position: Int) = position

    override fun getItemCount() = testResult.questions.count()

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        with(holder.binding) {
            tvIndex.text = "${position + 1}"
            val question = testResult.questions[position]
            if (question.score % 1.0 != 0.0) {
                tvScore.text = root
                    .context
                    .getString(R.string.participant_result, question.score, 1)
            } else {
                tvScore.text = root
                    .context
                    .getString(R.string.participant_int_result, question.score.toInt(), 1)
            }
            tvQuestion.text = question.question
            rv.layoutManager = LinearLayoutManager(root.context)
            val adapter =
                AnswersResultAdapter(testResult.questions[position].answers as ArrayList<AnswerResult>)
            rv.adapter = adapter
        }
    }

    inner class CourseViewHolder(val binding: ItemUserResultBinding) :
        RecyclerView.ViewHolder(binding.root)
}