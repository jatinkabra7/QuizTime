package com.jk.quiztime.data.mapper

import com.jk.quiztime.data.local.entity.UserAnswerEntity
import com.jk.quiztime.domain.model.UserAnswer

fun UserAnswer.toUserAnswerEntity() = UserAnswerEntity(
    questionId, selectedOption
)

fun UserAnswerEntity.toUserAnswer() = UserAnswer(
    questionId, selectedOption
)