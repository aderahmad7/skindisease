package com.example.skindisease.domain.use_case

import com.example.skindisease.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class ResetPassword(private val auth: FirebaseAuth) {

    operator fun invoke(email: String): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading())

        try {
            auth.sendPasswordResetEmail(email).await()
            emit(Resource.Success(true))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }
}