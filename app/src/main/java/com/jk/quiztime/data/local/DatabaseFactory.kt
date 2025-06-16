package com.jk.quiztime.data.local

import android.content.Context
import androidx.room.Room

object DatabaseFactory {

    fun create(context: Context) : QuizDB {
        return Room.databaseBuilder(
            context = context,
            klass = QuizDB::class.java,
            name = "quiz.db",
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}