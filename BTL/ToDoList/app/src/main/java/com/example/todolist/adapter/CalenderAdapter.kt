package com.example.todolist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R

class CalenderAdapter(private var events: List<Event>): RecyclerView.Adapter<CalenderAdapter.CalenderViewHolder>() {
    // ViewHolder để quản lý từng item
    class CalenderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtTitle: TextView = itemView.findViewById(R.id.txtTitle)
        val txtTime: TextView = itemView.findViewById(R.id.txtTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalenderViewHolder {
        TODO("Not yet implemented")
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_calender, parent, false)
        return CalenderViewHolder(view)
    }

    override fun getItemCount(): Int {
        return events.size
    }

    // Bind dữ liệu vào ViewHolder
    override fun onBindViewHolder(holder: CalenderViewHolder, position: Int) {
        TODO("Not yet implemented")
        val event = events[position] // Đây là đối tượng Event
        holder.txtTitle.text = event.title
        holder.txtTime.text = event.time
    }

    // Hàm để cập nhật danh sách sự kiện
    fun updateEvents(newEvents: List<Event>) {
        events = newEvents
        notifyDataSetChanged()
    }

}