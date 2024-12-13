package com.example.skindisease.domain.use_case

import com.example.skindisease.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UpdateProfile(private val auth: FirebaseAuth) {
    operator fun invoke(detail: UserProfileChangeRequest): Flow<Resource<Boolean>> = flow {

        emit(Resource.Loading())
        try {
            auth.currentUser!!.updateProfile(detail)
            emit(Resource.Success(true))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }
}