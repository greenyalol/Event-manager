package com.pts.greenya.eventmanager.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pts.greenya.eventmanager.R;
import com.pts.greenya.eventmanager.managers.DataManager;
import com.pts.greenya.eventmanager.network.req.ParticipantAddReq;
import com.pts.greenya.eventmanager.network.res.Event;
import com.pts.greenya.eventmanager.network.res.Person;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewParticipantActivity extends AppCompatActivity {

    private Button button;
    private EditText fName;
    private EditText lName;
    private EditText vkID;

    private int eventID;

    private DataManager dataManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_participant);

        dataManager = DataManager.getINSTANCE();

        button = (Button) findViewById(R.id.button2);
        fName = (EditText) findViewById(R.id.editText);
        lName = (EditText) findViewById(R.id.editText2);
        vkID = (EditText) findViewById(R.id.editText3);

        Intent intent = getIntent();
        eventID = intent.getIntExtra("eventID",0);

        button.setOnClickListener(makeParticipant);

    }

    private View.OnClickListener makeParticipant = new View.OnClickListener() {
        public void onClick(View v) {
            if (fName.getText().toString().length() == 0) {
                fName.setError("Введите Ваше имя");
            } else if (lName.getText().toString().length() == 0) {
                lName.setError("Введите Вашy фамилию");
            } else if (vkID.getText().toString().length() == 0) {
                vkID.setError("Введите Ваш id Вконтакте. По нему сможете узнать информацию о мероприятиях, на которые Вы зарегистрировались");
            } else {
                Call<Void> call = dataManager.addParticipant(new ParticipantAddReq(fName.getText().toString(),
                        lName.getText().toString(),
                        Integer.parseInt(vkID.getText().toString())), eventID);

                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        //events.addAll(response.body());
                        //recyclerView.getAdapter().notifyDataSetChanged();
                        showToast("Вы добавлены в качестве участника");
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        showToast("Ошибка при соединении с сервером");
                    }
                });
                finish();}
        }};

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
