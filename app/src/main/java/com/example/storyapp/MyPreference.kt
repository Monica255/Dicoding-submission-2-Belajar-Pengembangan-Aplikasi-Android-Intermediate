package com.example.storyapp

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MyPreference private constructor(private val dataStore: DataStore<Preferences>) {

    fun getLoginState(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[LOGIN_STATE] ?: false
        }
    }


    suspend fun saveLoginState(isDarkModeActive: Boolean) {
        dataStore.edit { preferences ->
            preferences[LOGIN_STATE] = isDarkModeActive
        }
    }

    fun getToken(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[TOKEN] ?: ""
        }
    }


    suspend fun saveToken(token: String) {
        dataStore.edit { preferences ->
            preferences[TOKEN] = token
        }
    }

    fun getName(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[NAME] ?: ""
        }
    }


    suspend fun saveName(name: String) {
        dataStore.edit { preferences ->
            preferences[NAME] = name
        }
    }



    companion object {
        @Volatile
        private var INSTANCE: MyPreference? = null

        fun getInstance(dataStore: DataStore<Preferences>): MyPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = MyPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }

        private val LOGIN_STATE = booleanPreferencesKey("login_state")
        private val TOKEN = stringPreferencesKey("token")
        private val NAME = stringPreferencesKey("name")

    }
}