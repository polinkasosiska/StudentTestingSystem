package student.testing.system.domain.addQuestion

import student.testing.system.models.Question
import javax.inject.Inject

class AddQuestionUseCase @Inject constructor() {

    operator fun invoke(question: Question): QuestionState {
        if (question.question.isEmpty()) {
            return QuestionState.EmptyQuestion
        }
        for (ans in question.answers) {
            if (ans.isRight) {
                return QuestionState.QuestionSuccess
            }
        }
        return QuestionState.NoCorrectAnswers
    }
}