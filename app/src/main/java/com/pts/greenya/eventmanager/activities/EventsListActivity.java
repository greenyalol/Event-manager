package com.pts.greenya.eventmanager.activities;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.pts.greenya.eventmanager.R;
import com.pts.greenya.eventmanager.managers.DataManager;
import com.pts.greenya.eventmanager.network.res.Event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.security.AccessController.getContext;


public class EventsListActivity extends AppCompatActivity {// implements View.OnClickListener {

    private TextView info;
    private DataManager dataManager;
    private List<Event> events;
    private ImageButton ib;
    MenuItem searchItem;
    SearchView searchView;
    RecyclerView recyclerView;
    Intent intent;
    EventAdapter adapter;
    EventsListActivity eventsListActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_list);

        intent = new Intent(this, EventAddActivity.class);

        ib = (ImageButton) findViewById(R.id.imageButton);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.getContext().startActivity(intent);
            }
        });
        dataManager = DataManager.getINSTANCE();
        eventsListActivity = this;

        events = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.events_recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        mDividerItemDecoration.setDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.list_rect, null));

        recyclerView.addItemDecoration(mDividerItemDecoration);

        Call<List<Event>> call = dataManager.getEvents();

        call.enqueue(new Callback<List<Event>>() {

            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                events.addAll(response.body());
                adapter = new EventAdapter(events,eventsListActivity);
                recyclerView.setAdapter(adapter);
                recyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                showToast("Ошибка при соединении с сервером");
            }
        });
    }


    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        search(searchView);

        return true;
    }


    private void search(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapter.getFilter().filter(newText);
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //return super.onOptionsItemSelected(item);
            case R.id.action_refresh:
                finish();startActivity(getIntent());
                break;
            case R.id.activity_a:
                break;
        }
        return true;
    }
}
