package com.jk.quiztime.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jk.quiztime.data.local.converter.OptionListConverter
import com.jk.quiztime.data.local.dao.QuestionDao
import com.jk.quiztime.data.local.dao.QuizTopicDao
import com.jk.quiztime.data.local.dao.UserAnswerDao
import com.jk.quiztime.data.local.entity.QuestionEntity
import com.jk.quiztime.data.local.entity.QuizTopicEntity
import com.jk.quiztime.data.local.entity.UserAnswerEntity

@Database(version = 3, entities = [QuizTopicEntity::class, QuestionEntity::class, UserAnswerEntity::class])

@TypeConverters(OptionListConverter::class)
abstract class QuizDB : RoomDatabase() {

    abstract fun quizTopicDao() : QuizTopicDao
    abstract fun questionDao() : QuestionDao
    abstract fun userAnswerDao() : UserAnswerDao
}