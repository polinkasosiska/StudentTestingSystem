package student.testing.system.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import student.testing.system.domain.addQuestion.AddQuestionUseCase
import student.testing.system.domain.addQuestion.QuestionState
import student.testing.system.models.Question
import student.testing.system.models.Test
import javax.inject.Inject

@HiltViewModel
class TestCreationViewModel @Inject constructor(
    private val addQuestionUseCase: AddQuestionUseCase
) : ViewModel() {

    private val questions: ArrayList<Question> = arrayListOf()
    val questionsFlow = MutableSharedFlow<ArrayList<Question>>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    fun addQuestion(question: Question): QuestionState {
        val state = addQuestionUseCase(question)
        if (state is QuestionState.QuestionSuccess) {
            questions += question
            viewModelScope.launch {
                questionsFlow.tryEmit(questions)
            }
        }
        return state
    }
}