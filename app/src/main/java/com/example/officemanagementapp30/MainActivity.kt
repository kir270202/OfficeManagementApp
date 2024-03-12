package com.example.officemanagementapp30

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val userEmail = intent.getStringExtra("user_email")
        if (userEmail != null) {
            val emailTextView: TextView = findViewById(R.id.textViewWelcome)
            emailTextView.text = "Добро пожаловать, $userEmail!"
        }
        // Дополнительные действия в MainActivity

        auth = FirebaseAuth.getInstance()

        val imageViewAccount: ImageView = findViewById(R.id.imageViewAccount)
        imageViewAccount.setOnClickListener {
            val currentUser: FirebaseUser? = auth.currentUser

            if (currentUser != null) {
                // Пользователь авторизован
                val userEmail = currentUser.email
                // Выполните необходимые действия для авторизованного пользователя
                Toast.makeText(this, "Пользователь авторизован. Email: $userEmail", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, AccountActivity::class.java))
            } else {
                // Пользователь не авторизован
                // Выполните необходимые действия для неавторизованного пользователя
                Toast.makeText(this, "Пользователь не авторизован", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

    }
}