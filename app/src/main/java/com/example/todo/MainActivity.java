package com.example.todo;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private EditText editTextTask;
    private Button buttonAdd, buttonPickDateTime;
    private ListView listViewTasks;
    private ArrayList<Task> taskList;
    private ArrayAdapter<String> adapter;
    private Calendar selectedDateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextTask = findViewById(R.id.editTextTask);
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonPickDateTime = findViewById(R.id.buttonPickDateTime);
        listViewTasks = findViewById(R.id.listViewTasks);

        taskList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>());
        listViewTasks.setAdapter(adapter);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTask();
            }
        });

        // Set an item click listener for the ListView
        listViewTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Remove the clicked task from the list
                taskList.remove(position);
                // Notify the adapter that the data set has changed
                updateTaskList();
            }
        });

        // Set click listener for picking date and time
        buttonPickDateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateTimePickerDialog();
            }
        });
    }

    private void addTask() {
        String taskDescription = editTextTask.getText().toString().trim();
        if (!taskDescription.isEmpty() && selectedDateTime != null) {
            Task task = new Task(taskDescription, selectedDateTime);
            taskList.add(task);
            // Log added task for debugging
            Log.d("TaskApp", "Task added: " + task.getDescription());
            updateTaskList();
            editTextTask.getText().clear();
            selectedDateTime = null; // Reset selectedDateTime after adding a task
        }
    }

    private void updateTaskList() {
        ArrayList<String> taskDetails = new ArrayList<>();
        for (Task task : taskList) {
            taskDetails.add(task.getDescription() + " - " + task.getFormattedDateTime());
        }
        adapter.clear();
        adapter.addAll(taskDetails);
        adapter.notifyDataSetChanged();
    }

    private void showDateTimePickerDialog() {
        // Get current date and time
        final Calendar currentDate = Calendar.getInstance();

        // Show DatePicker dialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        // Set selected date
                        currentDate.set(Calendar.YEAR, year);
                        currentDate.set(Calendar.MONTH, month);
                        currentDate.set(Calendar.DAY_OF_MONTH, day);

                        // Show TimePicker dialog
                        showTimePickerDialog(currentDate);
                    }
                },
                currentDate.get(Calendar.YEAR),
                currentDate.get(Calendar.MONTH),
                currentDate.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void showTimePickerDialog(final Calendar selectedDate) {
        // Show TimePicker dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        // Set selected time
                        selectedDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        selectedDate.set(Calendar.MINUTE, minute);

                        // Set selectedDateTime
                        selectedDateTime = selectedDate;

                        // Log selected date and time for debugging
                        Log.d("TaskApp", "Selected Date and Time: " + selectedDateTime.getTime());

                        // You can also display the selected date and time to the user if needed

                    }
                },
                selectedDate.get(Calendar.HOUR_OF_DAY),
                selectedDate.get(Calendar.MINUTE),
                true
        );
        timePickerDialog.show();
    }
}
