package com.example.skindisease.domain.use_case

import com.example.skindisease.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class Login(private val auth: FirebaseAuth) {
    operator fun invoke(email: String, password: String): Flow<Resource<FirebaseUser?>> = flow {
        emit(Resource.Loading())
        try {
            val data = auth.signInWithEmailAndPassword(email, password).await()
            emit(Resource.Success(data.user))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }
}