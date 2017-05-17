package com.pts.greenya.eventmanager.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pts.greenya.eventmanager.R;
import com.pts.greenya.eventmanager.managers.DataManager;
import com.pts.greenya.eventmanager.network.res.Event;
import com.pts.greenya.eventmanager.network.res.Person;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventInfoActivity extends AppCompatActivity {

    private TextView evName;
    private TextView startTime;
    private TextView endTime;
    private TextView eventCategory;
    private TextView description;
    private TextView personFirstName;
    private TextView personLastName;
    private TextView personVK;
    private TextView city;
    private TextView amount;
    private int eventID;
    private DataManager dataManager;
    private Button button;
    private List<Person> participants;
    private Intent newParticipantIntent;
    private LinearLayout ll;
    EventInfoActivity eventInfoActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_info);

        Intent intent = getIntent();

        newParticipantIntent = new Intent(this, NewParticipantActivity.class);

        eventInfoActivity = this;
        evName = (TextView) findViewById(R.id.ev_name);
        startTime = (TextView) findViewById(R.id.start_time);
        endTime = (TextView) findViewById(R.id.end_time);
        eventCategory = (TextView) findViewById(R.id.category);
        description  = (TextView) findViewById(R.id.desc);
        personFirstName = (TextView) findViewById(R.id.first_name);
        personLastName = (TextView) findViewById(R.id.last_name);
        personVK = (TextView) findViewById(R.id.vk_ref);
        city = (TextView) findViewById(R.id.city);
        amount = (TextView) findViewById(R.id.participants_amount);
        button = (Button) findViewById(R.id.button);
        ll = (LinearLayout) findViewById(R.id.participants_list);

        evName.setText(intent.getStringExtra("eventName"));
        startTime.setText(intent.getStringExtra("startTime"));
        endTime.setText(intent.getStringExtra("endTime"));
        eventCategory.setText(intent.getStringExtra("eventCategory"));
        description.setText(intent.getStringExtra("description"));
        personFirstName.setText(intent.getStringExtra("personFirstName"));
        personLastName.setText(intent.getStringExtra("personLastName"));
        personVK.setText("vk.com/id"+intent.getIntExtra("personVK",0));
        personVK.setMovementMethod(LinkMovementMethod.getInstance());
        city.setText(intent.getStringExtra("city"));

        button.setOnClickListener(makeParticipant);

        eventID = intent.getIntExtra("eventID",0);
        newParticipantIntent.putExtra("eventID",eventID);

        dataManager = DataManager.getINSTANCE();
        participants = new ArrayList<>();
        Call<List<Person>> call = dataManager.getParticipants(eventID);

        call.enqueue(new Callback<List<Person>>() {
            @Override
            public void onResponse(Call<List<Person>> call, Response<List<Person>> response) {
                participants.addAll(response.body());
                //System.out.println(participants.size());
                amount.setText(String.valueOf(participants.size()));
                //helper.show(participants);
                //LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        0.5f
                );
                for(Person user : participants) {

                    LinearLayout layout = new LinearLayout(eventInfoActivity);
                    layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0.5f));
                    ll.setOrientation(LinearLayout.VERTICAL);


                    TextView tvName = new TextView(eventInfoActivity);
                    TextView tvVk = new TextView(eventInfoActivity);

                    tvName.setText(user.getFirstName() + " " + user.getLastName());
                    //tvlName.setText();
                    tvVk.setText("vk.com/id" + user.getVkRef());

                    //layout.setGravity(Gravity.END);
                    tvVk.setGravity(Gravity.END);
                    tvVk.setLayoutParams(param);
                    Linkify.addLinks(tvVk, Linkify.WEB_URLS);
                    tvVk.setLinksClickable(true);
                    tvName.setGravity(Gravity.START);
                    tvName.setLayoutParams(param);
                    //tvlName.setGravity(Gravity.CENTER);

                    ll.addView(layout);

                    //tvlName.
                    //tvName.setGravity(Gravity.END);
                    //tvlName.setGravity(Gravity.END);
                    //llp.setMargins(0,0,0,0);
                    //ll.setLayoutParams(llp);
                    //tv.setId(i+5);
                    layout.addView(tvName);
                    layout.addView(tvVk);
                }
            }

            @Override
            public void onFailure(Call<List<Person>> call, Throwable t) {
                showToast("Ошибка при соединении с сервером");
            }
        });
    }

    private View.OnClickListener makeParticipant = new View.OnClickListener() {
        public void onClick(View v) {
            v.getContext().startActivity(newParticipantIntent);
        }
    };

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.action_refresh:
                finish();startActivity(getIntent());
                break;
        }
        return true;
    }

}
