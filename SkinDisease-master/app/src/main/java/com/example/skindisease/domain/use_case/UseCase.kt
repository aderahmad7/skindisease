package com.example.skindisease.domain.use_case

import com.example.skindisease.data.local.SkinDiseaseDb
import com.example.skindisease.domain.remote.repository.AiRepository
import com.google.firebase.auth.FirebaseAuth

// This class is used to provide a single point of access to all use case classes

class UseCase(aiRepository: AiRepository, firebaseAuth: FirebaseAuth, db: SkinDiseaseDb) {
    private val dao = db.detectionResultDao()
    val login = Login(firebaseAuth)
    val getUser = GetUser(firebaseAuth)
    val register = Register(firebaseAuth)
    val logout = Logout(firebaseAuth)
    val getDetectionResult = GetDetectionResults(dao)
    val insertResult = InsertResult(dao)
    val getLatestResult = GetLatestResult(dao)
    val getDetectionResultById = GetDetectionResult(dao)
    val updateProfile = UpdateProfile(firebaseAuth)
    val resetPassword = ResetPassword(firebaseAuth)
    val predict = PredictImage(aiRepository)
}