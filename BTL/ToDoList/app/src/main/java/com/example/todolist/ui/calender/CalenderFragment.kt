package com.example.todolist.ui.calender

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.adapter.CalenderAdapter
import com.example.todolist.adapter.Event
import com.example.todolist.databinding.FragmentCalenderBinding

class CalenderFragment : Fragment() {
    private var _binding: FragmentCalenderBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    //private lateinit var viewModel: CalenderViewModel
    private lateinit var adapter: CalenderAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate layout và lấy ViewModel
        val calenderViewModel =
            ViewModelProvider(this)[CalenderViewModel::class.java]

        _binding = FragmentCalenderBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Khởi tạo Adapter và RecyclerView
        adapter = CalenderAdapter(emptyList()) // Bắt đầu với ds rỗng
        binding.calendarRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.calendarRecyclerView.adapter = this.adapter

        // Quan sát dữ liệu từ ViewModel
            calenderViewModel.events.observe(viewLifecycleOwner) { events ->
                if (!events.isNullOrEmpty()) {
                    Log.d("CalendarFragment", "No events found.")
                    // Hiển thị thông báo không có sự kiện
                } else {
                    Log.d("CalendarFragment", "Events fetched: ${events.size}")
                    adapter.updateEvents(events) // Cập nhật RecyclerView
                }
        }

        // Gọi hàm lấy dữ liệu
        calenderViewModel.fetchEvents(requireContext())
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}