package com.jk.quiztime.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.jk.quiztime.data.util.Constants.CORRECT_ANSWER_PREF_KEY
import com.jk.quiztime.data.util.Constants.QUESTIONS_ATTEMPTED_PREF_KEY
import com.jk.quiztime.domain.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferencesRepositoryImpl(
    private val prefs : DataStore<Preferences>
) : UserPreferencesRepository {

    companion object {
        private val QUESTIONS_ATTEMPTED_KEY = intPreferencesKey(QUESTIONS_ATTEMPTED_PREF_KEY)
        private val CORRECT_ANSWER_KEY = intPreferencesKey(CORRECT_ANSWER_PREF_KEY)
    }

    override fun getQuestionsAttempted(): Flow<Int> {
        return prefs.data.map { preferences ->
            preferences[QUESTIONS_ATTEMPTED_KEY] ?: 0
        }
    }

    override fun getCorrectAnswers(): Flow<Int> {
        return prefs.data.map { preferences ->
            preferences[CORRECT_ANSWER_KEY] ?: 0
        }
    }

    override suspend fun saveScore(questionsAttempted: Int, correctAnswers: Int) {

        prefs.edit {preferences ->
            preferences[QUESTIONS_ATTEMPTED_KEY] = questionsAttempted
            preferences[CORRECT_ANSWER_KEY] = correctAnswers
        }
    }
}