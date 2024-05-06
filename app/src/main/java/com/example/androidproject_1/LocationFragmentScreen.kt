package com.example.androidproject_1

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AlertDialog
import java.text.SimpleDateFormat
import java.util.*

class LocationFragmentScreen : Fragment() {

    private lateinit var tvDatePicker: TextView
    private lateinit var btnDatePicker: Button
    private lateinit var tvTimePicker: TextView
    private lateinit var btnTimePicker: Button
    private lateinit var etVehicleNumber: EditText
    private lateinit var etVehicleModel: EditText
    private lateinit var btnBookAppointment: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_location_screen2, container, false)

        tvDatePicker = view.findViewById(R.id.tvDate)
        btnDatePicker = view.findViewById(R.id.btnDatePicker)
        tvTimePicker = view.findViewById(R.id.tvTime)
        btnTimePicker = view.findViewById(R.id.btnTimePicker)
        etVehicleNumber = view.findViewById(R.id.vehicle_number)
        etVehicleModel = view.findViewById(R.id.vehicle_model)
        btnBookAppointment = view.findViewById(R.id.btnBookAppointment)

        val myCalendar = Calendar.getInstance()
        val datePickerListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateDateLabel(myCalendar)
        }

        btnDatePicker.setOnClickListener {
            DatePickerDialog(requireContext(), datePickerListener, myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        val timePickerListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
            myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
            myCalendar.set(Calendar.MINUTE, minute)
            updateTimeLabel(myCalendar)
        }

        btnTimePicker.setOnClickListener {
            TimePickerDialog(
                requireContext(),
                timePickerListener,
                myCalendar.get(Calendar.HOUR_OF_DAY),
                myCalendar.get(Calendar.MINUTE),
                true
            ).show()
        }

        btnBookAppointment.setOnClickListener {
            showAppointmentDetailsDialog()
        }

        return view
    }

    private fun updateDateLabel(myCalendar: Calendar) {
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
        tvDatePicker.text = sdf.format(myCalendar.time)
    }

    private fun updateTimeLabel(myCalendar: Calendar) {
        val myFormat = "HH:mm"
        val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
        tvTimePicker.text = sdf.format(myCalendar.time)
    }

    private fun showAppointmentDetailsDialog() {
        val vehicleNumber = etVehicleNumber.text.toString()
        val vehicleModel = etVehicleModel.text.toString()
        val date = tvDatePicker.text.toString()
        val time = tvTimePicker.text.toString()

        if (vehicleNumber.isNotEmpty() && vehicleModel.isNotEmpty() && date.isNotEmpty() && time.isNotEmpty()) {
            val alertDialogBuilder = AlertDialog.Builder(requireContext())
            alertDialogBuilder.setTitle("Appointment Details")
            alertDialogBuilder.setMessage("Vehicle Number: $vehicleNumber\nVehicle Model: $vehicleModel\nDate: $date\nTime: $time")
            alertDialogBuilder.setPositiveButton("SUBMIT") { _, _ ->
                Toast.makeText(requireContext(), "Appointment Booked", Toast.LENGTH_SHORT).show()
            }

            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        } else {
            Toast.makeText(requireContext(), "Please fill all the details", Toast.LENGTH_SHORT).show()
            // Show an error message or handle the case when the fields are empty.
        }
    }
}