package student.testing.system.domain.addQuestion

sealed class QuestionState {
    object QuestionSuccess : QuestionState()
    object EmptyQuestion : QuestionState()
    object NoCorrectAnswers : QuestionState()
}