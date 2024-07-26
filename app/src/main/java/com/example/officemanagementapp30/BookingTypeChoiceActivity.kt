package com.example.officemanagementapp30

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class BookingTypeChoiceActivity : AppCompatActivity() {

    private lateinit var buttonConferenceRoomChoice: Button
    private lateinit var buttonWorkspaceChoice: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_boooking_type_choice)

        buttonConferenceRoomChoice = findViewById(R.id.buttonChooseConferenceRoom)
        buttonWorkspaceChoice = findViewById(R.id.buttonChooseWorkspace)

        buttonConferenceRoomChoice.setOnClickListener {
            val intent = Intent(this, WorkspacesChoiceActivity::class.java)
            intent.putExtra("type", "1")
            startActivity(intent)
            finish()
        }

        buttonWorkspaceChoice.setOnClickListener {
            val intent = Intent(this, WorkspacesChoiceActivity::class.java)
            intent.putExtra("type", "2")
            startActivity(intent)
            finish()
        }
    }
}