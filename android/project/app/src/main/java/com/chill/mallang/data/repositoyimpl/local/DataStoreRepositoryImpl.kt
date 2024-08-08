package com.chill.mallang.data.repositoyimpl.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.chill.mallang.data.model.entity.User
import com.chill.mallang.data.model.response.ApiResponse
import com.chill.mallang.data.repository.local.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreRepositoryImpl
    @Inject
    constructor(
        private val dataStore: DataStore<Preferences>,
    ) : DataStoreRepository {
        override suspend fun saveAccessToken(token: String) {
            dataStore.edit { prefs ->
                prefs[ACCESS_TOKEN_KEY] = token
            }
        }

        override suspend fun deleteAccessToken() {
            dataStore.edit { prefs ->
                prefs.remove(ACCESS_TOKEN_KEY)
            }
        }

        override suspend fun getAccessToken() =
            flow {
                emit(
                    dataStore.data
                        .map { prefs ->
                            prefs[ACCESS_TOKEN_KEY]
                        }.first(),
                )
            }

        override suspend fun saveUserEmail(email: String) {
            dataStore.edit { prefs ->
                prefs[USER_EMAIL_KEY] = email
            }
        }

        override suspend fun deleteUserEmail() {
            dataStore.edit { prefs ->
                prefs.remove(USER_EMAIL_KEY)
            }
        }

        override suspend fun getUserEmail() =
            dataStore.data
                .map { prefs ->
                    prefs[USER_EMAIL_KEY]
                }.first()

        override suspend fun logout(): Flow<ApiResponse<Unit>> =
            flow {
                val emailDeletionResult = runCatching { deleteUserEmail() }
                val tokenDeletionResult = runCatching { deleteAccessToken() }
                if (emailDeletionResult.isSuccess && tokenDeletionResult.isSuccess) {
                    emit(ApiResponse.Success(Unit))
                } else {
                    val errors = mutableListOf<String>()
                    emailDeletionResult.exceptionOrNull()?.message?.let { errors.add(it) }
                    tokenDeletionResult.exceptionOrNull()?.message?.let { errors.add(it) }
                    if (errors.isNotEmpty()) {
                        emit(
                            ApiResponse.Error(
                                errorCode = 500,
                                errorMessage = errors.joinToString(", "),
                            ),
                        )
                    }
                }
            }

        override suspend fun saveUser(user: User) {
            dataStore.edit { prefs ->
                prefs[USER_ID_KEY] = user.id.toString()
                prefs[USER_FACTION_ID] = user.factionId.toString()
            }
        }

        override suspend fun getUserId() =
            dataStore.data
                .map { prefs ->
                    prefs[USER_ID_KEY].toString().toLongOrNull()
                }.first()

        override suspend fun getFactionId() =
            dataStore.data
                .map { prefs ->
                    prefs[USER_FACTION_ID].toString().toLongOrNull()
                }.first()

        companion object {
            val ACCESS_TOKEN_KEY = stringPreferencesKey("ACCESS_TOKEN_KEY")
            val USER_EMAIL_KEY = stringPreferencesKey("USER_EMAIL_KEY")
            val USER_FACTION_ID = stringPreferencesKey("USER_FACTION_ID_KEY")
            val USER_ID_KEY = stringPreferencesKey("USER_ID_KEY")
        }
    }