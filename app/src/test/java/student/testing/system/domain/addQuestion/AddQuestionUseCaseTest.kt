package student.testing.system.domain.addQuestion

import org.junit.Assert.*
import org.junit.Test
import student.testing.system.models.Answer
import student.testing.system.models.Question

class AddQuestionUseCaseTest {

    private val addQuestionUseCase = AddQuestionUseCase()

    @Test
    fun `empty question returns EmptyQuestion state`() {
        val expected = QuestionState.EmptyQuestion
        val actual = addQuestionUseCase(Question("", emptyList()))
        assertEquals(expected, actual)
    }

    @Test
    fun `empty answers returns NoCorrectAnswers state`() {
        val expected = QuestionState.NoCorrectAnswers
        val actual = addQuestionUseCase(Question("Some question", emptyList()))
        assertEquals(expected, actual)
    }

    @Test
    fun `if there are no correct answers return NoCorrectAnswers state`() {
        val answers = listOf(Answer("Ans 1", false), Answer("Ans 2", false))
        val expected = QuestionState.NoCorrectAnswers
        val actual = addQuestionUseCase(Question("Some question", answers))
        assertEquals(expected, actual)
    }

    @Test
    fun `if at least one answer is right return QuestionSuccess`() {
        val answers = listOf(Answer("Ans 1", false), Answer("Ans 2", true))
        val expected = QuestionState.QuestionSuccess
        val actual = addQuestionUseCase(Question("Some question", answers))
        assertEquals(expected, actual)
    }
}