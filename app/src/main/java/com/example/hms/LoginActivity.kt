package com.example.hms

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore



class LoginActivity : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var etSecretKey: EditText
    private lateinit var btnLogin: Button
    private lateinit var roleGroup: RadioGroup
//    val  db = FirebaseFirestore.getInstance()
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    private val ADMIN_SECRET_KEY = "YOUR_SECRET_KEY_HERE" // Set this key manually

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize Firebase
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        // Initialize Views
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        etSecretKey = findViewById(R.id.etSecretKey)
        btnLogin = findViewById(R.id.btnLogin)
        roleGroup = findViewById(R.id.roleGroup)

        // Role selection listener
        roleGroup.setOnCheckedChangeListener { _, checkedId ->
            etSecretKey.visibility = if (checkedId == R.id.rbAdmin) View.VISIBLE else View.GONE
        }

        // Login Button Click
        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()
            val secretKey = etSecretKey.text.toString().trim()
            val selectedRole = getSelectedRole()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter all details", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (selectedRole == "Admin" && secretKey != ADMIN_SECRET_KEY) {
                Toast.makeText(this, "Invalid Secret Key!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            loginUser(email, password, selectedRole)
        }
    }

    // Get selected role
    private fun getSelectedRole(): String {
        return when (roleGroup.checkedRadioButtonId) {
            R.id.rbAdmin -> "Admin"
            R.id.rbStudent -> "Student"
            R.id.rbWorker -> "Worker"
            else -> "Unknown"
        }
    }

    // Login function
    private fun loginUser(email: String, password: String, role: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                checkUserRole(auth.currentUser?.uid, role)
            }
            .addOnFailureListener {
                Toast.makeText(this, "Login failed: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }

    // Check if user has the correct role in Firestore
    private fun checkUserRole(uid: String?, expectedRole: String) {
        if (uid == null) return

        db.collection("users").document(uid).get()
            .addOnSuccessListener { document ->
                val userRole = document.getString("role")

                if (userRole == expectedRole) {
//                    redirectToDashboard(userRole)
                } else {
                    Toast.makeText(this, "Role mismatch!", Toast.LENGTH_SHORT).show()
                    auth.signOut()
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error fetching user data", Toast.LENGTH_SHORT).show()
            }
    }

    // Redirect to the correct dashboard
//    private fun redirectToDashboard(role: String) {
//        val intent = when (role) {
////            "Admin" -> Intent(this, AdminDashboard::class.java)
////            "Student" -> Intent(this, StudentDashboard::class.java)
////            "Worker" -> Intent(this, WorkerDashboard::class.java)
//            else -> null
//        }
//        intent?.let {
//            startActivity(it)
//            finish()
//        }
//    }
}
