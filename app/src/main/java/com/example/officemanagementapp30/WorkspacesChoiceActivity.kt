package com.example.officemanagementapp30

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class WorkspacesChoiceActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var database: FirebaseDatabase

    private lateinit var bookingType: String
//    private lateinit var workspaceId: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workspaces_choice)

        val extras = intent.extras
        if (extras != null) {
            bookingType =
                extras.getString("type").toString() // "key" должен совпадать с ключом, который вы использовали для передачи данных
            // Теперь вы можете использовать переменную data, чтобы получить переданные данные
        }

        // Получаем ссылку на базу данных
        val databaseReference = FirebaseDatabase.getInstance().reference.child("workspaces")

        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val linearLayout = findViewById<LinearLayout>(R.id.linearLayout)

                for (workspaceSnapshot in dataSnapshot.children) {
                    val workspaceId = workspaceSnapshot.key?.toString()
                    val name = workspaceSnapshot.child("name").value.toString()
                    val type = workspaceSnapshot.child("type").value.toString()
                    val capacity = workspaceSnapshot.child("capacity").value.toString()
                    val location = workspaceSnapshot.child("location").value.toString()
                    val description = workspaceSnapshot.child("description").value.toString()
                    var typeInterpretation = "Тип"
                    if (type=="1"){
                        typeInterpretation = "Переговорная комната"
                    }
                    else
                    {
                        typeInterpretation = "Рабочее место"
                    }

                    if (bookingType == type){
                        val cardView = CardView(this@WorkspacesChoiceActivity)
                        val cardParams = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        )
                        cardParams.setMargins(16, 16, 16, 16)
                        cardView.layoutParams = cardParams

                        val textView = TextView(this@WorkspacesChoiceActivity)
                        textView.text = "$name\n$typeInterpretation"

                        cardView.addView(textView)
                        linearLayout.addView(cardView)

                        cardView.setOnClickListener{
                            var intent = Intent(this@WorkspacesChoiceActivity, BookingActivity::class.java)
                            intent.putExtra("workspaceId", workspaceId)
                            intent.putExtra("workspaceName", name)
                            intent.putExtra("workspaceLocation", location)
                            startActivity(intent)
                        }
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })

    }
}