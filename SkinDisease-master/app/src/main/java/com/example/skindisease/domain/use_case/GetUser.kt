package com.example.skindisease.domain.use_case

import com.example.skindisease.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetUser(private val auth: FirebaseAuth) {

    operator fun invoke(): Flow<Resource<FirebaseUser?>> = flow {
        emit(Resource.Loading())
        try {
            val user = auth.currentUser
            emit(Resource.Success(user))
        } catch (e: Exception) {
            emit(Resource.Error("An error occurred"))
        }
    }
}