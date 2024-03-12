package com.example.officemanagementapp30

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException

class RegiterUserActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var registerButton: Button

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_user)

        // Инициализация элементов интерфейса
        emailEditText = findViewById(R.id.email_input_reg)
        passwordEditText = findViewById(R.id.password_input_reg)
        confirmPasswordEditText = findViewById(R.id.password_input_repeat_reg)
        registerButton = findViewById(R.id.register_button)
        val backToLoginTextView: TextView = findViewById(R.id.back_to_login_text_view)

        // Инициализация Firebase Authentication
        auth = FirebaseAuth.getInstance()

        // Обработчик нажатия на кнопку регистрации
        registerButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
                if (password == confirmPassword) {
                    // Попытка регистрации нового пользователя через Firebase
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                // Регистрация успешна
                                Toast.makeText(this, "Регистрация успешна", Toast.LENGTH_SHORT).show()
                                // Вместо комментария "Дополнительные действия при успешной авторизации/регистрации"
                                val intent = Intent(this, MainActivity::class.java)
                                intent.putExtra("user_email", email)
                                startActivity(intent)
                                finish() // Опционально, чтобы закрыть текущую активность
                            } else {
                                // В случае ошибки выводим сообщение
                                when (task.exception) {
                                    is FirebaseAuthUserCollisionException -> {
                                        Toast.makeText(this, "Пользователь с таким email уже зарегистрирован", Toast.LENGTH_SHORT).show()
                                    }
                                    is FirebaseAuthInvalidCredentialsException -> {
                                        Toast.makeText(this, "Неверный формат email", Toast.LENGTH_SHORT).show()
                                    }
                                    else -> {
                                        Toast.makeText(this, "Ошибка регистрации", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        }
                } else {
                    Toast.makeText(this, "Пароли не совпадают", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show()
            }
        }

        backToLoginTextView.setOnClickListener {
            finish()
        }

    }
}