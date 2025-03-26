package com.example.hms

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        val currentUser = auth.currentUser

        if (currentUser == null) {
            // No user is logged in, go to login page
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        } else {
            // User is logged in, check their role and redirect
            checkUserRole(currentUser.uid)
        }
    }

    private fun checkUserRole(uid: String) {
        db.collection("users").document(uid).get()
            .addOnSuccessListener { document ->
                val userRole = document.getString("role")

                val intent = when (userRole) {
//                    "Admin" -> Intent(this, AdminDashboard::class.java)
//                    "Student" -> Intent(this, StudentDashboard::class.java)
//                    "Worker" -> Intent(this, WorkerDashboard::class.java)
                    else -> Intent(this, LoginActivity::class.java) // Fallback
                }

                startActivity(intent)
                finish()
            }
            .addOnFailureListener {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
    }
}
