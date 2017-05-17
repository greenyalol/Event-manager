package com.pts.greenya.eventmanager.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.pts.greenya.eventmanager.R;
import com.pts.greenya.eventmanager.managers.DataManager;
import com.pts.greenya.eventmanager.network.req.EventAddReq;
import com.pts.greenya.eventmanager.network.res.EventCategory;
import com.pts.greenya.eventmanager.network.res.Person;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventAddActivity extends AppCompatActivity {

    Calendar myCalendar = Calendar.getInstance();
    private EditText startTime;
    private EditText startDate;
    private EditText endDate;
    private EditText endTime;
    private Spinner category;
    private EditText desc;
    private EditText fName;
    private EditText lName;
    private EditText evName;
    private EditText city;
    private EditText address;
    private EditText vkID;
    private Button button;
    String startDateTime;
    String endDateTime;

    Person person;
    EventCategory eventCategory;


    DataManager dataManager;

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }
    };

    DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel2();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_add);

        dataManager = DataManager.getINSTANCE();

        startDate = (EditText) findViewById(R.id.start_date2);
        startTime = (EditText) findViewById(R.id.start_time2);
        endDate = (EditText) findViewById(R.id.end_date2);
        endTime = (EditText) findViewById(R.id.end_time2);
        this.address = (EditText) findViewById(R.id.address2);
        this.category = (Spinner) findViewById(R.id.spinner);
        this.city = (EditText) findViewById(R.id.city2);
        this.evName = (EditText) findViewById(R.id.event_name2);
        this.desc = (EditText) findViewById(R.id.desc2);
        this.fName = (EditText) findViewById(R.id.f_name2);
        this.lName = (EditText) findViewById(R.id.l_name2);
        this.vkID = (EditText) findViewById(R.id.vk_ref2);
        button = (Button) findViewById(R.id.button4);

        button.setOnClickListener(addEvent);

        endDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(EventAddActivity.this, date2, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        endTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(EventAddActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        endTime.setText( pad(selectedHour) + ":" + pad(selectedMinute));
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Выберите время");
                mTimePicker.show();

            }
        });


        startTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(EventAddActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        startTime.setText( pad(selectedHour) + ":" + pad(selectedMinute));
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Выберите время");
                mTimePicker.show();

            }
        });

        startDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(EventAddActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

        startDate.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateLabel2() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

        endDate.setText(sdf.format(myCalendar.getTime()));
    }

    private String pad(int value){

        if(value < 10){
            return "0"+value;
        }


        return ""+value;
    }

    private View.OnClickListener addEvent = new View.OnClickListener() {
        public void onClick(View v) {

            if (fName.getText().toString().length() == 0) {
                fName.setError("Введите Ваше имя");
            } else if (lName.getText().toString().length() == 0) {
                lName.setError("Введите Вашy фамилию");
            } else if (vkID.getText().toString().length() == 0) {
                vkID.setError("Введите Ваш id Вконтакте.");
            } else if (address.getText().toString().length() == 0) {
                address.setError("Введите адрес");
            } else if (city.getText().toString().length() == 0) {
                city.setError("Введите город");
            } else if (evName.getText().toString().length() == 0) {
                evName.setError("Введите название");
            } else if (desc.getText().toString().length() == 0) {
                desc.setError("Введите описание");
            } else if (startTime.getText().toString().length() == 0) {
                startTime.setError("Введите время");
            } else if (endDate.getText().toString().length() == 0) {
                endDate.setError("Введите дату");
            } else if (endTime.getText().toString().length() == 0) {
                endTime.setError("Введите дату");
            } else if (startDate.getText().toString().length() == 0) {
                startDate.setError("Введите дату");
            } else if (Date.valueOf(startDate.getText().toString()).after(Date.valueOf(endDate.getText().toString()))){
                startDate.setError("Дата начала не может быть раньше даты окончания");
            } else {
            startDateTime = startDate.getText().toString() + " " + startTime.getText().toString();
            endDateTime = endDate.getText().toString() + " " + endTime.getText().toString();

            person = new Person();
            person.setFirstName(fName.getText().toString());
            person.setLastName(lName.getText().toString());
            person.setVkRef(Integer.parseInt(vkID.getText().toString()));

            eventCategory = new EventCategory();
            eventCategory.setCategoryName(category.getSelectedItem().toString());

            Call<Void> call = dataManager.addEvent(

                    new EventAddReq(evName.getText().toString(),
                    eventCategory, person,
                    startDateTime, endDateTime,
                    desc.getText().toString(),
                    city.getText().toString(),
                    address.getText().toString())
            );

                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        //events.addAll(response.body());
                        //recyclerView.getAdapter().notifyDataSetChanged();
                        showToast("Мероприятие добавлено");
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        showToast("Ошибка при соединении с сервером");
                    }
                });
                finish();
            }
        }};

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
