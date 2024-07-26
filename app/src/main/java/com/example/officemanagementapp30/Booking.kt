package com.example.officemanagementapp30

data class Booking(val calendarIntegrationEnabled: Boolean?=null, val comment: String?=null, val date: String?=null,
                   val notificationsEnabled: Boolean?= null, val startTime: String?=null, val endTime: String?=null,
                   val userEmail: String?=null, val workspaceId: String?=null) {
}