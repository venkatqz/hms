package com.example.hms.models

import com.google.firebase.Timestamp

data class Complaint(
    val id: String = "",
    val studentId: String = "",
    val roomNumber: String = "",
    val block: String = "",
    val complaintType: ComplaintType = ComplaintType.ELECTRICAL,
    val description: String = "",
    val status: ComplaintStatus = ComplaintStatus.PENDING,
    val createdAt: Timestamp = Timestamp.now(),
    val updatedAt: Timestamp = Timestamp.now(),
    val assignedWorkerId: String? = null,
    val notes: String = ""
)

enum class ComplaintType {
    ELECTRICAL,
    PLUMBING,
    CARPENTER,
    CIVIL,
    CLEANING,
    OTHER
}

enum class ComplaintStatus {
    PENDING,
    ASSIGNED,
    IN_PROGRESS,
    COMPLETED,
    POSTPONED,
    NOT_IN_ROOM
} 