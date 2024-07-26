package com.example.officemanagementapp30

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var addBookingImageView: ImageView
    private lateinit var myBookingsImage1: ImageView
    private lateinit var myBookingsImage2: ImageView
    private lateinit var myBookingsImage3: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addBookingImageView = findViewById(R.id.imageViewAddBooking)
        myBookingsImage1 = findViewById(R.id.mainActivityBookingImage1)
        myBookingsImage2 = findViewById(R.id.mainActivityBookingImage2)
        myBookingsImage3 = findViewById(R.id.mainActivityBookingImage3)

        val userEmail = intent.getStringExtra("user_email")
        if (userEmail != null) {
            val emailTextView: TextView = findViewById(R.id.textViewWelcome)
            emailTextView.text = "Добро пожаловать, $userEmail!"
        }

        auth = FirebaseAuth.getInstance()

        val imageViewAccount: ImageView = findViewById(R.id.imageViewAccount)
        imageViewAccount.setOnClickListener {
            val currentUser: FirebaseUser? = auth.currentUser

            if (currentUser != null) {
                val userEmail = currentUser.email
                Toast.makeText(this, "Пользователь авторизован. Email: $userEmail", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, AccountActivity::class.java))
            } else {
                Toast.makeText(this, "Пользователь не авторизован", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        addBookingImageView.setOnClickListener {
//            val currentUser: FirebaseUser? = auth.currentUser
//
//            if (currentUser != null) {
//                val userEmail = currentUser.email
//                Toast.makeText(this, "Пользователь авторизован. Email: $userEmail", Toast.LENGTH_SHORT).show()
//                startActivity(Intent(this, BookingActivity::class.java))
//            } else {
//                Toast.makeText(this, "Пользователь не авторизован", Toast.LENGTH_SHORT).show()
//                val intent = Intent(this, LoginActivity::class.java)
//                startActivity(intent)
//                finish()
//            }
            val currentUser: FirebaseUser? = auth.currentUser
            if (currentUser != null) {
                val userEmail = currentUser.email
                Toast.makeText(this, "Пользователь авторизован. Email: $userEmail", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, BookingTypeChoiceActivity::class.java))
            } else {
                Toast.makeText(this, "Пользователь не авторизован", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, BookingTypeChoiceActivity::class.java)
                startActivity(intent)
                finish()
            }
        }


        myBookingsImage1.setOnClickListener{
            val currentUser: FirebaseUser? = auth.currentUser
            if (currentUser != null) {
                val userEmail = currentUser.email
                Toast.makeText(this, "Пользователь авторизован. Email: $userEmail", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MyBookingsActivity::class.java))
            } else {
                Toast.makeText(this, "Пользователь не авторизован", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MyBookingsActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        myBookingsImage2.setOnClickListener{
            val currentUser: FirebaseUser? = auth.currentUser
            if (currentUser != null) {
                val userEmail = currentUser.email
                Toast.makeText(this, "Пользователь авторизован. Email: $userEmail", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MyBookingsActivity::class.java))
            } else {
                Toast.makeText(this, "Пользователь не авторизован", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MyBookingsActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        myBookingsImage3.setOnClickListener{
            val currentUser: FirebaseUser? = auth.currentUser
            if (currentUser != null) {
                val userEmail = currentUser.email
                Toast.makeText(this, "Пользователь авторизован. Email: $userEmail", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MyBookingsActivity::class.java))
            } else {
                Toast.makeText(this, "Пользователь не авторизован", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MyBookingsActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

    }
}