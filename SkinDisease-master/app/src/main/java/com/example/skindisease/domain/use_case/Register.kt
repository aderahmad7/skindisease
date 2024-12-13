package com.example.skindisease.domain.use_case

import com.example.skindisease.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class Register(private val auth: FirebaseAuth) {

    operator fun invoke(
        email: String,
        password: String,
        fullName: String
    ): Flow<Resource<FirebaseUser?>> = flow {
        emit(Resource.Loading())
        try {
            val user = auth.createUserWithEmailAndPassword(email, password).await()
            user.user?.updateProfile(
                UserProfileChangeRequest.Builder()
                    .setDisplayName(fullName).build()
            )
            emit(Resource.Success(user.user))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }
}