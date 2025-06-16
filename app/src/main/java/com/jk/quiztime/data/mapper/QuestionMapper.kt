package com.jk.quiztime.data.mapper

import com.jk.quiztime.data.local.entity.QuestionEntity
import com.jk.quiztime.data.remote.dto.QuestionDto
import com.jk.quiztime.domain.model.Question

fun QuestionDto.toQuestion() : Question {
    return Question(
        id = id,
        topicCode = topicCode,
        question = question,
        allOptions = (incorrectAnswers + correctAnswer).shuffled(),
        correctAnswer = correctAnswer,
        explanation = explanation
    )
}

fun QuestionDto.toQuestionEntity() : QuestionEntity {
    return QuestionEntity(
        id = id,
        topicCode = topicCode,
        question = question,
        incorrectAnswers = incorrectAnswers,
        correctAnswer = correctAnswer,
        explanation = explanation
    )
}

fun QuestionEntity.entityToQuestion() : Question {
    return Question(
        id = id,
        topicCode = topicCode,
        question = question,
        allOptions = (incorrectAnswers + correctAnswer).shuffled(),
        correctAnswer = correctAnswer,
        explanation = explanation
    )
}

fun List<QuestionDto>.entityToQuestions() : List<Question> {
    return map { it.toQuestion() }
}

fun List<QuestionEntity>.toQuestions() : List<Question> {
    return map { it.entityToQuestion() }
}

fun List<QuestionDto>.toQuestionsEntity() : List<QuestionEntity> {
    return map { it.toQuestionEntity() }
}