package com.jk.quiztime.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.jk.quiztime.data.util.Constants

object DataStoreFactory {

    fun create(context: Context) : DataStore<Preferences> {
        return PreferenceDataStoreFactory.create {
            context.preferencesDataStoreFile(name = Constants.QUIZ_TOPICS_TABLE_NAME)
        }
    }
}