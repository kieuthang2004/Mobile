package com.example.todolist.ui.calender

import android.accounts.AccountManager
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todolist.adapter.Event
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.calendar.Calendar

class CalenderViewModel : ViewModel() {

    // LiveData chứa ds event
    private val _events = MutableLiveData<List<Event>>()
    val events: LiveData<List<Event>> = _events

    // Hàm lấy tên tài khoản Google
    fun getAccountName(context: Context): String? {
        val accountManager = AccountManager.get(context)
        val accounts = accountManager.getAccountsByType("com.google")
        return if (accounts.isNotEmpty()) accounts[0].name else null
    }

    // Hàm để lấy dữ liệu (giả sử từ API hoặc cơ sở dữ liệu)
    fun fetchEvents(context: Context) {

        val thread = Thread {
            try {
                // Cấu hình HTTP Transport và Jackson Factory
                val transport = NetHttpTransport()
                val jsonFactory = JacksonFactory.getDefaultInstance()

                // Lấy thông tin xc thực từ OAuth từ tệp JSON
                val credentials = GoogleAccountCredential.usingOAuth2(
                    context,
                    listOf("https://www.googleapis.com/auth/calendar.readonly")
                )
                credentials.selectedAccountName = getAccountName(context)

                val calendarService = Calendar.Builder(transport, jsonFactory, credentials)
                    .setApplicationName("My Calendar App")
                    .build()

                // Lấy ds sự kiện từ gg Calendar
                val events = calendarService.events().list("primary")
                    .setMaxResults(10)
                    .setOrderBy("startTime")
                    .setSingleEvents(true)
                    .execute()

                val eventList = events.items.map { event ->
                    Event(
                        title = event.summary ?: "No Title",
                        time = event.start.dateTime?.toString() ?: "No Time"
                    )
                }

                // Cập nhật LiveData
                _events.postValue(/* value = */ eventList)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        thread.start()
    }

}