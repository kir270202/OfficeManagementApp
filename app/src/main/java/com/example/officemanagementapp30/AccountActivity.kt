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

//        val imageViewAccount: ImageView = findViewById(R.id.imageViewAccount)
        val imageButtonBack: ImageButton = findViewById(R.id.imageButtonBack)
        val buttonLogout: Button = findViewById(R.id.ButtonLogout)
//        val imageViewAvatar: ImageView = findViewById(R.id.imageViewAvatar)
        val textViewEmail: TextView = findViewById(R.id.textViewEmail)

        // Обработчик нажатия для возвращения на предыдущую активити
        imageButtonBack.setOnClickListener {
            finish()
        }

        // Обработчик нажатия для выхода из аккаунта
        buttonLogout.setOnClickListener {
            // Выход из аккаунта (переход на страницу авторизации)
            auth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        // Остальные действия для отображения информации о пользователе
        val currentUser: FirebaseUser? = auth.currentUser
        if (currentUser != null) {
            // Пользователь авторизован
            val userEmail = currentUser.email
            textViewEmail.text = userEmail

            // Дополнительные действия для отображения аватара пользователя и другой информации
        }
    }
}