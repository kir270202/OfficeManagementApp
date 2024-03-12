package com.example.officemanagementapp30

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AccountActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        auth = FirebaseAuth.getInstance()

        val imageButtonBack: ImageButton = findViewById(R.id.imageButtonBack)
        val buttonLogout: Button = findViewById(R.id.ButtonLogout)
        val textViewEmail: TextView = findViewById(R.id.textViewEmail)

        imageButtonBack.setOnClickListener {
            finish()
        }

        buttonLogout.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        val currentUser: FirebaseUser? = auth.currentUser
        if (currentUser != null) {
            val userEmail = currentUser.email
            textViewEmail.text = userEmail
        }
    }
}