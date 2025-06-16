package com.jk.quiztime.data.mapper

import com.jk.quiztime.data.local.entity.QuizTopicEntity
import com.jk.quiztime.data.remote.dto.QuizTopicDto
import com.jk.quiztime.data.util.Constants
import com.jk.quiztime.data.util.Constants.BASE_URL
import com.jk.quiztime.domain.model.QuizTopic

private fun QuizTopicDto.toQuizTopic() = QuizTopic(
    id = id,
    name = name,
    imageUrl = BASE_URL + imageUrl,
    code = code
)

private fun QuizTopicDto.toQuizTopicEntity() = QuizTopicEntity(
    id = id,
    name = name,
    imageUrl = BASE_URL + imageUrl,
    code = code
)

fun QuizTopicEntity.entityToQuizTopic() = QuizTopic(
    id = id,
    name = name,
    imageUrl = imageUrl,
    code = code
)

fun List<QuizTopicDto>.toQuizTopics() = map { it.toQuizTopic() }

fun List<QuizTopicDto>.toQuizTopicsEntity() = map { it.toQuizTopicEntity() }

fun List<QuizTopicEntity>.entityToQuizTopics() = map { it.entityToQuizTopic() }