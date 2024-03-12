package com.example.officemanagementapp30

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.google.android.gms.common.internal.RootTelemetryConfigManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference

class BookingActivity : AppCompatActivity() {

    private lateinit var RoomNameTextView: TextView
    private lateinit var bookingCalendarView: CalendarView
    private lateinit var addToCalendarSwitch: Switch
    private lateinit var enableNotificationsSwitch: Switch
    private lateinit var commentsEditText: EditText
    private lateinit var bookButton: Button

    private lateinit var time8CardView: CardView
    private lateinit var time9CardView: CardView
    private lateinit var time10CardView: CardView
    private lateinit var time11CardView: CardView
    private lateinit var time12CardView: CardView
    private lateinit var time13CardView: CardView
    private lateinit var time14CardView: CardView
    private lateinit var time15CardView: CardView
    private lateinit var time16CardView: CardView
    private lateinit var time17CardView: CardView
    private lateinit var time18CardView: CardView
    private lateinit var time19CardView: CardView
    private lateinit var time20CardView: CardView
    private lateinit var time21CardView: CardView
    private lateinit var time22CardView: CardView

    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)

        RoomNameTextView = findViewById(R.id.textViewConferenceRoomNameBooking)
        bookingCalendarView = findViewById(R.id.textViewConferenceRoomNameBooking)
    }
}