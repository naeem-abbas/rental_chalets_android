package com.example.rentalchalets;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rentalchalets.utils.DBHelper;
import com.example.rentalchalets.utils.FeeCalculator;
import com.example.rentalchalets.utils.ReservationInfo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {


    //modal inputs
    private Spinner roomTypeSpinner;
    private Spinner beautyTreatmentsSpinner;
    //pages inputs
    private Spinner spinner;
    private EditText numPersonsEditTextView;
    private TextView startDateTextViewTitle;
    private TextView startDatePicker;
    private TextView endDateTextViewTitle;
    private TextView endDatePicker;
    private EditText firstNameEditTextView;
    private EditText lastNameEditTextView;
    private Button reserveButton;
    private ReservationInfo reservationInfo;

    String[] menuItems = {"Select Menu","Rooms", "Buffet", "Access", "Massage", "Sauna", "Beauty Treatment"};
    private List<String> roomsTypeList = new ArrayList<>();
    private List<String> beautyTreatmentsList = new ArrayList<>();

    private FeeCalculator feeCalculator;
    private DBHelper dbHelper;

    public void showDialogModal(Context context,String modalTitle) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setPositiveButton("Submit", null);
        //Create Dropdown for Room Type
        // Create a Spinner
        roomTypeSpinner = new Spinner(context);
        roomsTypeList.removeAll(roomsTypeList);
        roomsTypeList.add("Select Room Type");
        roomsTypeList.add("Simple Room");
        roomsTypeList.add("Luxurious Princess Room");
        roomsTypeList.add("Royal Luxurious Room");
        ArrayAdapter<String> roomTypeAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, roomsTypeList);
        roomTypeSpinner.setAdapter(roomTypeAdapter);

        beautyTreatmentsSpinner = new Spinner(context);
        beautyTreatmentsList.removeAll(beautyTreatmentsList);
        beautyTreatmentsList.add("Select Beauty Treatments");
        beautyTreatmentsList.add("Manicure and Pedicure");
        beautyTreatmentsList.add("Facial Care");
        beautyTreatmentsList.add("Hairdressing");
        ArrayAdapter<String> beautyTreatmentsAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, beautyTreatmentsList);
        beautyTreatmentsSpinner.setAdapter(beautyTreatmentsAdapter);

        // Add input fields to the dialog
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(30, 30, 30, 30);
        layout.setBackgroundColor(Color.WHITE); // Set a background color
        // Add a title to the room spinner
        TextView roomTitle = new TextView(context);
        roomTitle.setText("Room Type");
        roomTitle.setTextSize(16);
        roomTitle.setTypeface(Typeface.DEFAULT_BOLD);
        //add to view
        layout.addView(roomTitle);
        layout.addView(roomTypeSpinner);

        // Add a divider and margin
        addDividerAndMargin(context, layout);

        //add isSpecial CheckBox
        CheckBox isSpecialCheckBox = new CheckBox(context);
        isSpecialCheckBox.setText("Is Special");
        isSpecialCheckBox.setTextSize(16);
        layout.addView(isSpecialCheckBox);
        //add divider and the margin
        addDividerAndMargin(context, layout);

        TextView activityBuffetTitle = new TextView(context);
        activityBuffetTitle.setText("Activity Buffet");
        activityBuffetTitle.setTextSize(16);
        activityBuffetTitle.setTypeface(Typeface.DEFAULT_BOLD);
        layout.addView(activityBuffetTitle);

        // Add the Activity Buffet radio field
        RadioGroup activityBuffetRadioGroup = new RadioGroup(context);
        RadioButton everydayRadioButton = new RadioButton(context);
        everydayRadioButton.setText("Everyday");
        RadioButton daysOfWeekRadioButton = new RadioButton(context);
        daysOfWeekRadioButton.setText("Day of Week");
        RadioButton dayOfWeekRadioButton = new RadioButton(context);
        dayOfWeekRadioButton.setText("Weekends");

        activityBuffetRadioGroup.addView(everydayRadioButton);
        activityBuffetRadioGroup.addView(daysOfWeekRadioButton);
        activityBuffetRadioGroup.addView(dayOfWeekRadioButton);
        layout.addView(activityBuffetRadioGroup);
        //add divider and the margin
        addDividerAndMargin(context, layout);

        //Add the Activity Massage
        TextView activityMassageTitle = new TextView(context);
        activityMassageTitle.setText("Activity Massage");
        activityMassageTitle.setTextSize(16);
        activityMassageTitle.setTypeface(Typeface.DEFAULT_BOLD);
        layout.addView(activityMassageTitle);

        // Add the Activity Massage text field
        EditText activityMassageEditText = new EditText(context);
        activityMassageEditText.setHint("Enter Activity Massage");
        layout.addView(activityMassageEditText);
        //add divider and the margin
        addDividerAndMargin(context, layout);

        // Add a title for the Activity Sauna text field
        TextView activitySaunaTitle = new TextView(context);
        activitySaunaTitle.setText("Activity Sauna");
        activitySaunaTitle.setTextSize(16);
        activitySaunaTitle.setTypeface(Typeface.DEFAULT_BOLD);
        layout.addView(activitySaunaTitle);

        // Add the Activity Sauna text field
        EditText activitySaunaEditText = new EditText(context);
        activitySaunaEditText.setHint("Enter Activity Sauna");
        layout.addView(activitySaunaEditText);
        addDividerAndMargin(context, layout);


        // Add a title to the room spinner
        TextView beautyTreatmentsTitle = new TextView(context);
        beautyTreatmentsTitle.setText("Beauty Treatments");
        beautyTreatmentsTitle.setTextSize(16);
        beautyTreatmentsTitle.setTypeface(Typeface.DEFAULT_BOLD);
        //add to view
        layout.addView(beautyTreatmentsTitle);
        layout.addView(beautyTreatmentsSpinner);
        addDividerAndMargin(context, layout);


        // Wrap the layout in a CardView
        CardView cardView = new CardView(context);
        cardView.setRadius(10);
        cardView.setElevation(10);
        cardView.addView(layout);

        // Add the CardView to a ScrollView
        ScrollView  scrollView = new ScrollView(context);
        scrollView.addView(cardView);

        // Set a fading edge
        scrollView.setFadingEdgeLength(100);

        builder.setTitle(modalTitle);
        builder.setView(scrollView);

        // room Type Spinner Event Listener
        roomTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedRoom = roomsTypeList.get(position);
                // Handle the selected room option
                switch (selectedRoom) {
                    case "Simple Room":
                        // Handle Simple Room selection
                        Toast.makeText(MainActivity.this, selectedRoom.toString(), Toast.LENGTH_SHORT).show();

                        break;
                    case "Luxurious Princess Room":
                        // Handle Luxurious Princess Room selection
                        Toast.makeText(MainActivity.this, selectedRoom.toString(), Toast.LENGTH_SHORT).show();

                        break;
                    case "Royal Luxurious Room":
                        // Handle Royal Luxurious Room selection
                        Toast.makeText(MainActivity.this, selectedRoom.toString(), Toast.LENGTH_SHORT).show();

                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle the case where no option is selected
            }
        });

        // beauty treamennts Spinner Event Listener
        beautyTreatmentsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedTreatment = beautyTreatmentsList.get(position);
                // Handle the selected room option
                switch (selectedTreatment) {
                    case "Manicure and Pedicure":
                        // Handle Manicure and Pedicure selection
                        Toast.makeText(MainActivity.this, selectedTreatment.toString(), Toast.LENGTH_SHORT).show();

                        break;
                    case "Facial Care":
                        // Handle Facial Care selection
                        Toast.makeText(MainActivity.this,selectedTreatment.toString() , Toast.LENGTH_SHORT).show();
                        break;
                    case "Hairdressing":
                        // Handle Hairdressing selection
                        Toast.makeText(MainActivity.this, selectedTreatment.toString(), Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle the case where no option is selected
            }
        });
        // Create and show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();
        // Override the positive button's click listener
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ReservationInfo object to hold the values
                reservationInfo = new ReservationInfo();
                // Get the selected room type and beauty treatment
                String selectedRoom = (String) roomTypeSpinner.getSelectedItem();
                String selectedBeautyTreatment = (String) beautyTreatmentsSpinner.getSelectedItem();

                // Get the activity buffet, activity massage, and activity sauna values
                RadioButton selectedRadioButton = activityBuffetRadioGroup.findViewById(activityBuffetRadioGroup.getCheckedRadioButtonId());
                String activityBuffet = selectedRadioButton != null ? selectedRadioButton.getText().toString() : "";
                String activityMassage = activityMassageEditText.getText().toString();
                String activitySauna = activitySaunaEditText.getText().toString();
                Boolean isSpecial = isSpecialCheckBox.isChecked();


                // Perform validation
                if (!selectedRoom.equals("Select Room Type") &&
                        !selectedBeautyTreatment.equals("Select Beauty Treatments") &&
                        !activityBuffet.isEmpty() &&
                        !activityMassage.isEmpty() &&
                        !activitySauna.isEmpty()) {
                    // All required fields are filled, dismiss the dialog
                    reservationInfo.setSelectedRoom(selectedRoom);
                    reservationInfo.setSelectedBeautyTreatment(selectedBeautyTreatment);
                    reservationInfo.setActivityBuffet(activityBuffet);
                    reservationInfo.setActivityMassage(activityMassage);
                    reservationInfo.setActivitySauna(activitySauna);
                    reservationInfo.setSpecial(isSpecial);
                    reservationInfo.setIsValidFormModal(true);
                    dialog.dismiss();

                    // Proceed with your logic here or call another method
                    // e.g., processReservation(selectedRoom, selectedBeautyTreatment, activityBuffet, activityMassage, activitySauna, isSpecial);
                } else {
                    // Show error message or highlight the fields that need to be filled
                    Toast.makeText(context, "Please fill all required fields", Toast.LENGTH_SHORT).show();
                    // You can also set error states for the EditText fields or show specific messages for each field
                }
            }
        });

        // Set the negative button listener (optional)
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle the Cancel button click
                spinner.setSelection(0); // Reset spinner selection if needed
                dialog.dismiss(); // Dismiss the dialog
            }
        });

    }

    private void addDividerAndMargin(Context context, LinearLayout layout) {
        addMarginToTheLayout(context, layout);
        addDividerToTheLayout(context, layout);
        addMarginToTheLayout(context, layout);
    }

    private void addMarginToTheLayout(Context context, LinearLayout layout) {
        // Add a spacer view to add margin bottom
        View spacer = new View(context);
        spacer.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 20)); // 20dp height
        layout.addView(spacer);
    }

    private void addDividerToTheLayout(Context context, LinearLayout layout) {
        View divider = new View(context);
        divider.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1));
        divider.setBackgroundColor(Color.GRAY);
        layout.addView(divider);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        feeCalculator = new FeeCalculator();
        dbHelper=new DBHelper(this);

        // Get the Spinner view
        spinner = (Spinner) findViewById(R.id.spinner);

        // Create an ArrayAdapter using the menu items
        ArrayAdapter<String> menu_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, menuItems);

        // Set the dropdown view resource
        menu_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set the adapter for the spinner
        spinner.setAdapter(menu_adapter);

        // Set an OnItemSelectedListener for the spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item
                String selectedItem = menuItems[position];
                // Perform action based on the selected item
                switch (selectedItem) {
                    case "Rooms":
                        // Do something
                        showDialogModal(  MainActivity.this,selectedItem.toString());
                        break;
                    case "Buffet":
                        // Do something
                        showDialogModal(  MainActivity.this,selectedItem.toString());
                        System.out.println("Buffet");
                        break;
                    case "Access":
                        // Do something
                        showDialogModal(  MainActivity.this,selectedItem.toString());
                        break;
                    case "Massage":
                        // Do something
                        showDialogModal(  MainActivity.this,selectedItem.toString());
                        break;
                    case "Sauna":
                        // Do something
                        showDialogModal(  MainActivity.this,selectedItem.toString());
                        break;
                    case "Beauty Treatment":
                        // Do something
                        showDialogModal(  MainActivity.this,selectedItem.toString());
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do something when nothing is selected
            }
        });


        //Pages inputs
        startDateTextViewTitle = findViewById(R.id.start_date_title);
        startDatePicker = findViewById(R.id.start_date_picker);

        startDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStartDatePickerDialog();
            }
        });

        endDateTextViewTitle = findViewById(R.id.end_date_title);
        endDatePicker = findViewById(R.id.end_date_picker);

        endDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEndDatePickerDialog();
            }
        });


        firstNameEditTextView=findViewById(R.id.first_name_input);
        lastNameEditTextView=findViewById(R.id.last_name_input);
        numPersonsEditTextView = findViewById(R.id.num_persons_input);
        numPersonsEditTextView.setEnabled(false);
        Button decreaseButton = findViewById(R.id.decrease_button);
        Button increaseButton = findViewById(R.id.increase_button);
        reserveButton = findViewById(R.id.reserve_button);
        // Set click listeners
        decreaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decreaseNumPersons();
            }
        });

        increaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increaseNumPersons();
            }
        });

        // Set click listener for Reserve button
        reserveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reserve();
            }
        });

    }
    private void showStartDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String monthStr = String.format("%02d", month + 1); // Add 1 to month because it's 0-based
                String dayStr = String.format("%02d", dayOfMonth); // Format day to 2 digits
                startDatePicker.setText(String.format("%d-%s-%s", year, monthStr, dayStr));
            }
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void showEndDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String monthStr = String.format("%02d", month + 1); // Add 1 to month because it's 0-based
                String dayStr = String.format("%02d", dayOfMonth); // Format day to 2 digits
                endDatePicker.setText(String.format("%d-%s-%s", year, monthStr, dayStr));
            }
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void decreaseNumPersons() {
        int currentNum = Integer.parseInt(numPersonsEditTextView.getText().toString());
        if (currentNum > 1) {
            numPersonsEditTextView.setText(String.valueOf(currentNum - 1));
        }
    }

    private void increaseNumPersons() {
        int currentNum = Integer.parseInt(numPersonsEditTextView.getText().toString());
        numPersonsEditTextView.setText(String.valueOf(currentNum + 1));
    }


    private void reserve() {
        String firstName = firstNameEditTextView.getText().toString().trim();
        String lastName = lastNameEditTextView.getText().toString().trim();
        int numberOfPersons = Integer.parseInt(numPersonsEditTextView.getText().toString());
        String startDate = startDatePicker.getText().toString();
        String endDate = endDatePicker.getText().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

       if(reservationInfo == null){
           Toast.makeText(this, "Fill the modal", Toast.LENGTH_SHORT).show();
           return;
       }
        // Perform validation or additional processing if needed
        if (firstName.isEmpty() || lastName.isEmpty() || startDate.equals("YYYY-MM-DD") || endDate.equals("YYYY-MM-DD")) {
            Toast.makeText(this, "Please fill the all required fields.", Toast.LENGTH_SHORT).show();
            return;
        }


//        try {
//            Date startDateParse= sdf.parse(startDate);
//            Date endDateParse= sdf.parse(endDate);
//
////            Boolean isWeekEnd=feeCalculator.isWeekend(startDateParse);
////            double roomTypeFee=feeCalculator.calculateRoomTypeFee(isWeekEnd,startDateParse,endDateParse,selectedRoom);
////            double activityBuffetFee=feeCalculator.calculateBuffetOfKingsFee(isWeekEnd,startDateParse,endDateParse,activityBuffet);
////            double activityMassageFee=feeCalculator.calculateMassageFee(isWeekEnd,startDateParse,endDateParse);
////            double activitySaunaFee=feeCalculator.calculateSaunaFee(isWeekEnd,startDateParse,endDateParse);
////            double beautyTreatmentFee=feeCalculator.calculateBeautyTreatmentFee(isWeekEnd,startDateParse,endDateParse,selectedBeautyTreatment);
////
////            double calculateTotalFee=roomTypeFee+activityBuffetFee+activityMassageFee+activitySaunaFee+beautyTreatmentFee;
////            spinner.setSelection(0);
//
////            Boolean isInserted= dbHelper.insertBooking(selectedRoom,startDate,endDate,isSpecial,activityBuffet,activityMassage,activitySauna,selectedBeautyTreatment,calculateTotalFee);
////            if (isInserted) {
////                Log.v("ÏnsertDB_Success", "Inserted Data");
////                // Create a dialog box
////                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
////                alertDialogBuilder.setTitle("Booking Confirmation");
////                alertDialogBuilder.setMessage("Your booking is completed. Total fee: " + calculateTotalFee);
////                alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
////                    @Override
////                    public void onClick(DialogInterface dialog, int which) {
////                        dialog.dismiss();
////                    }
////                });
////                AlertDialog alertDialog = alertDialogBuilder.create();
////                alertDialog.show();
////            } else {
////                Log.v("ÏnsertDB_Faild", "Something went wrong.");
////            }
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//
//
//         Perform reservation logic here (e.g., show a confirmation message)
        String message = "Reservation successful for " + firstName + " " + lastName;
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}