package com.example.skindisease.domain.use_case

import com.example.skindisease.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class Logout(private val auth: FirebaseAuth) {

    operator fun invoke(): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading())

        try {
            auth.signOut()
            emit(Resource.Success(true))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }
}