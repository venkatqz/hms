package com.example.hms.models

data class User(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val userType: UserType = UserType.STUDENT,
    val block: String = "", // For admin users
    val registrationNumber: String = "", // For students
    val roomNumber: String = "", // For students
    val dateOfBirth: String = "", // For students
    val workerType: WorkerType? = null, // For workers
    val workerId: String = "" // For workers
)

enum class UserType {
    STUDENT,
    ADMIN,
    WORKER
}

enum class WorkerType {
    ELECTRICAL,
    PLUMBING,
    CARPENTER,
    CIVIL,
    CLEANING
} 