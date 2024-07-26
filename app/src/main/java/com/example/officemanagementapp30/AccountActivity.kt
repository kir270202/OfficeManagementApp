package com.example.officemanagementapp30

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AccountActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var dbRef: DatabaseReference
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        auth = FirebaseAuth.getInstance()
        dbRef = FirebaseDatabase.getInstance().getReference("users")

        val imageButtonBack: ImageButton = findViewById(R.id.imageButtonBack)
        val buttonLogout: Button = findViewById(R.id.ButtonLogout)
        val textViewEmail: TextView = findViewById(R.id.textViewEmail)
        val sendTestDataButton: Button = findViewById(R.id.ButtonSendTestData)

        imageButtonBack.setOnClickListener {
            finish()
        }

        buttonLogout.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        sendTestDataButton.setOnClickListener {
            sendTestData()
        }

        val currentUser: FirebaseUser? = auth.currentUser
        if (currentUser != null) {
            val userEmail = currentUser.email
            textViewEmail.text = userEmail
        }
    }

    private fun sendTestData() {

        val empId = dbRef.push().key!!

        val currentUser: FirebaseUser? = auth.currentUser
        var userEmail: String? = currentUser?.email
        if (currentUser != null) {
            userEmail = currentUser.email
        }

        val user = User(name="John", lastname = "Cena", email=userEmail)

        dbRef.child(empId).setValue(user)
            .addOnCompleteListener {
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()

            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }

    }


}