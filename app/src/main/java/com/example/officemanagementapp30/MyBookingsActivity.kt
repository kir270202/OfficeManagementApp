package com.example.officemanagementapp30

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

class MyBookingsActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private lateinit var workspacesList: ArrayList<Workspace>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_bookings)

//        val extras = intent.extras
//        if (extras != null) {
//            bookingType =
//                extras.getString("type").toString() // "key" должен совпадать с ключом, который вы использовали для передачи данных
//            // Теперь вы можете использовать переменную data, чтобы получить переданные данные
//        }

        // Получаем ссылку на базу данных
        val databaseReference = FirebaseDatabase.getInstance().reference.child("bookings")
        val databaseReferenceWorkspaces = FirebaseDatabase.getInstance().reference.child("workspaces")
        auth = FirebaseAuth.getInstance()
//        workspacesList = mutableListOf<Workspace>()

        databaseReferenceWorkspaces.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                workspacesList = ArrayList()
                // Проходимся по всем записям в базе данных
                for (workspaceSnapshot in dataSnapshot.children) {
                    val workspaceId = workspaceSnapshot.key?.toString()
                    // Получаем данные из текущей записи
                    val name = workspaceSnapshot.child("name").value.toString()
                    val type = workspaceSnapshot.child("type").value.toString()
                    val capacity = workspaceSnapshot.child("capacity").value.toString().toInt()
                    val location = workspaceSnapshot.child("location").value.toString()
                    val description = workspaceSnapshot.child("description").value.toString()

                    var workspace = Workspace(id = workspaceId, name = name, type = type, location = location, capacity = capacity, description = description)
                    if (workspaceId != null) {
                        workspacesList.add(workspace)
                    }
                }
                var size = workspacesList.size
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Обработка ошибок, если они возникнут
            }
        })

// Добавляем слушатель для получения данных из базы данных
        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Создаем LinearLayout, который будет содержать все CardView
                val linearLayout = findViewById<LinearLayout>(R.id.linearLayoutMyBookings)

                // Проходимся по всем записям в базе данных
                for (bookingsSnapshot in dataSnapshot.children) {
                    // Получаем данные из текущей записи
                    val calendarIntegrationEnabled = bookingsSnapshot.child("calendarIntegrationEnabled").value.toString()
                    val comment = bookingsSnapshot.child("comment").value.toString()
                    val date = bookingsSnapshot.child("date").value.toString()
                    val notificationsEnabled = bookingsSnapshot.child("notificationsEnabled").value.toString()
                    val startTime = bookingsSnapshot.child("startTime").value.toString()
                    val endTime = bookingsSnapshot.child("endTime").value.toString()
                    val userEmail = bookingsSnapshot.child("userEmail").value.toString()
                    val workspaceId = bookingsSnapshot.child("workspaceId").value.toString()

                    var workspaceName: String = ""
                    var workspaceLocation: String = ""
                    for (workspace in workspacesList){
                        if (workspaceId == workspace.id){
                            workspaceName = workspace.name.toString()
                            workspaceLocation = workspace.location.toString()
                            break
                        }
                    }
//                    workspaceName = workspacesList.get(workspaceId.toInt()).name.toString()
//                    workspaceLocation = workspacesList.get(workspaceId.toInt()).location

                    if (userEmail == auth.currentUser?.email){
                        // Создаем новый CardView
                        val cardView = CardView(this@MyBookingsActivity)
                        val cardParams = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        )
                        cardParams.setMargins(16, 16, 16, 16)
                        cardView.layoutParams = cardParams
                        // Настройка внешнего вида CardView
                        // cardView.radius = resources.getDimension(R.dimen.card_corner_radius)
                        // cardView.cardElevation = resources.getDimension(R.dimen.card_elevation)

                        // Создаем TextView для отображения данных о рабочем пространстве
                        val textView = TextView(this@MyBookingsActivity)

                        textView.text = "$workspaceName\n$workspaceLocation\n$startTime-$endTime"
                        // Другие данные могут быть добавлены аналогичным образом

                        // Добавляем TextView в CardView
                        cardView.addView(textView)

                        // Добавляем CardView в LinearLayout
                        linearLayout.addView(cardView)
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Обработка ошибок, если они возникнут
            }
        })


    }
}