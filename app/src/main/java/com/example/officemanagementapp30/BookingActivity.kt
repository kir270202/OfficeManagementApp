package com.example.officemanagementapp30

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CalendarContract
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener



import android.view.View
import com.google.firebase.database.*
import java.time.LocalTime
import java.util.Calendar


class BookingActivity : AppCompatActivity() {

    private lateinit var RoomNameTextView: TextView
    private lateinit var bookingCalendarView: CalendarView
    private lateinit var addToCalendarSwitch: Switch
    private lateinit var enableNotificationsSwitch: Switch
    private lateinit var commentsEditText: EditText
    private lateinit var bookButton: Button
    private lateinit var backImageButton: ImageButton

    private lateinit var selectedTimeCardViews: Array<CardView>

    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var database: FirebaseDatabase

    private lateinit var bookingDate: String
    private lateinit var clickedStates: BooleanArray
    private lateinit var bookingStartTime: String
    private lateinit var bookingEndTime: String
    private lateinit var workspaceId: String
    private lateinit var workspaceName: String
    private lateinit var workspaceLocation: String

//    private lateinit var date: String
//    private lateinit var startTime: String
//    private lateinit var endTime: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)

        val extras = intent.extras
        if (extras != null) {
            workspaceId = extras.getString("workspaceId").toString()
            workspaceName = extras.getString("workspaceName").toString()
            workspaceLocation = extras.getString("workspaceLocation").toString()
        }

        RoomNameTextView = findViewById(R.id.textViewConferenceRoomNameBooking)
        bookingCalendarView = findViewById(R.id.datePickerBooking)
        addToCalendarSwitch = findViewById(R.id.switchAddToCalendar)
        enableNotificationsSwitch = findViewById(R.id.switchNotification)
        commentsEditText = findViewById(R.id.editTextComments)
        bookButton = findViewById(R.id.buttonBook)
        backImageButton = findViewById(R.id.buttonBackBooking)

        RoomNameTextView.text = workspaceName

        selectedTimeCardViews = arrayOf(
                findViewById(R.id.cardViewBooking8),
                findViewById(R.id.cardViewBooking9),
                findViewById(R.id.cardViewBooking10),
                findViewById(R.id.cardViewBooking11),
                findViewById(R.id.cardViewBooking12),
                findViewById(R.id.cardViewBooking13),
                findViewById(R.id.cardViewBooking14),
                findViewById(R.id.cardViewBooking15),
                findViewById(R.id.cardViewBooking16),
                findViewById(R.id.cardViewBooking17),
                findViewById(R.id.cardViewBooking18),
                findViewById(R.id.cardViewBooking19),
                findViewById(R.id.cardViewBooking20),
                findViewById(R.id.cardViewBooking21),
                findViewById(R.id.cardViewBooking22)
        )

        clickedStates = BooleanArray(selectedTimeCardViews.size) { false }

        for ((index, cardView) in selectedTimeCardViews.withIndex()) {
            cardView.setOnClickListener {
                // Сброс всех цветов CardView и флагов нажатия
                for (i in selectedTimeCardViews.indices) {
                    selectedTimeCardViews[i].setCardBackgroundColor(ContextCompat.getColor(this, R.color.white))
                    clickedStates[i] = false
                }

                // Устанавливаем новый цвет и флаг нажатия для выбранного CardView
                cardView.setCardBackgroundColor(ContextCompat.getColor(this, R.color.purple_200))
                clickedStates[index] = true
                bookingStartTime = (index+8).toString()+":00"
                bookingEndTime = (index+9).toString()+":00"
            }
        }


        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database.getReference("bookings")

        bookingCalendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            bookingDate = "$year-${month + 1}-${dayOfMonth}"
        }

        bookButton.setOnClickListener {
            sendBookingData()
            if (addToCalendarSwitch.isChecked){
                addBookingToCalendar(bookingStartTime, bookingEndTime)
            }

            finish()
        }

        backImageButton.setOnClickListener{
            finish()
        }

//        updateCardAvailability()
    }

//    private fun updateCardAvailability() {
//        val databaseReferenceBookings = database.getReference("bookings")
//        databaseReferenceBookings.addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                for (bookingSnapshot in dataSnapshot.children) {
//                    val bookingDate = bookingSnapshot.child("date").value.toString()
//                    val bookingStartTime = bookingSnapshot.child("startTime").value.toString()
//                    val bookingEndTime = bookingSnapshot.child("endTime").value.toString()
//
//                    for ((index, cardView) in selectedTimeCardViews.withIndex()) {
//                        val time = (index + 8).toString() + ":00"
//
//                        if (date == bookingDate && time >= startTime && time < endTime) {
//                            cardView.visibility = View.INVISIBLE
//                            cardView.isClickable = false
//                        } else {
//                            cardView.visibility = View.VISIBLE
//                            cardView.isClickable = true
//                        }
//                    }
//                }
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//                // Обработка ошибок, если они возникнут
//            }
//        })
//    }

    private fun sendBookingData() {
        val bookingId = databaseReference.push().key!!
        val currentUser: FirebaseUser? = auth.currentUser
        var userEmail: String? = currentUser?.email
        val calendarIntegrationEnabled = addToCalendarSwitch.isChecked
        val comment = commentsEditText.text.toString()
        val notificationsEnabled = enableNotificationsSwitch.isChecked

        val booking = Booking(calendarIntegrationEnabled = calendarIntegrationEnabled, comment = comment,
            date = bookingDate, notificationsEnabled = notificationsEnabled, startTime = bookingStartTime,
            endTime = bookingEndTime, userEmail = userEmail, workspaceId = workspaceId)

        databaseReference.child(bookingId).setValue(booking)
            .addOnCompleteListener {
                Toast.makeText(this, "Бронирование успешно создано", Toast.LENGTH_LONG).show()
            }.addOnFailureListener { err ->
                Toast.makeText(this, "Ошибка при создании бронирования: ${err.message}", Toast.LENGTH_LONG).show()
            }
    }

    private fun addBookingToCalendar(startTime: String, endTime: String){
        val startTimeInMillis: Long = convertTimeToMillis(startTime)
        val endTimeInMillis: Long = convertTimeToMillis(endTime)
        val intent = Intent(Intent.ACTION_INSERT)
        intent.data = CalendarContract.Events.CONTENT_URI
        intent.putExtra(CalendarContract.Events.TITLE, workspaceName)
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION, workspaceLocation)
        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startTimeInMillis)
        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTimeInMillis)
        if (enableNotificationsSwitch.isChecked){
            intent.putExtra(CalendarContract.Events.HAS_ALARM, 1)
        }
        else{
            intent.putExtra(CalendarContract.Events.HAS_ALARM, 0)
        }
        startActivity(intent)
    }

    private fun convertTimeToMillis(time: String): Long {
        val calendar = Calendar.getInstance()

        val parts = time.split(":")
        val hour = parts[0].toInt()
        val minute = parts[1].toInt()

        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        return calendar.timeInMillis
    }

}