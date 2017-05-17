package com.pts.greenya.eventmanager.activities;

import android.app.Fragment;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.pts.greenya.eventmanager.R;
import com.pts.greenya.eventmanager.network.res.Event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Greenya on 10.05.2017.
 */

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> implements Filterable {

    private List<Event> events;
    private EventsListActivity eventsListActivity;
    Intent myIntent;
    private List<Event> filteredEvents;


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mEventName;
        private TextView mEventStartTime;
        private TextView mEventAddress;
        private TextView mCity;

        private ImageButton imageButton;


        public ViewHolder(View eventName) {
            super(eventName);
            this.mEventName = (TextView) itemView.findViewById(R.id.event_name);
            this.mEventStartTime = (TextView) itemView.findViewById(R.id.event_start_time);
            this.mEventAddress = (TextView) itemView.findViewById(R.id.event_address);
            this.imageButton = (ImageButton) itemView.findViewById(R.id.imageButton);
            this.mCity = (TextView) itemView.findViewById(R.id.city3);
        }

    }

    public EventAdapter(List<Event> events, EventsListActivity eventsListActivity) {
        this.events = events;
        this.filteredEvents = events;
        this.eventsListActivity = eventsListActivity;
        //eventsCopy = new ArrayList<>();
        //eventsCopy.addAll(events);
        //eventName.setOnClickListener(this);
    }

    @Override
    public EventAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_events_list, parent, false);
        //...
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Event ev = filteredEvents.get(position);
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.imageButton.setVisibility(View.INVISIBLE);
        holder.mEventName.setText(ev.getEventName());
        holder.mEventStartTime.setText(ev.getStartTime());
        holder.mEventAddress.setText(ev.getAddress());
        holder.mCity.setText(ev.getCity());

        myIntent = new Intent(this.eventsListActivity, EventInfoActivity.class);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myIntent.putExtra("eventName", ev.getEventName());
                myIntent.putExtra("startTime", ev.getStartTime());
                myIntent.putExtra("endTime", ev.getEndTime());
                myIntent.putExtra("eventID", ev.getEventID());
                myIntent.putExtra("eventCategory", ev.getEventCategory().getCategoryName());
                myIntent.putExtra("city", ev.getCity());
                myIntent.putExtra("description", ev.getDescription());

                myIntent.putExtra("personFirstName", ev.getPerson().getFirstName());
                myIntent.putExtra("personLastName", ev.getPerson().getLastName());
                myIntent.putExtra("personVK", ev.getPerson().getVkRef());

                v.getContext().startActivity(myIntent);

            }
        });

    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredEvents = (ArrayList<Event>) filterResults.values;
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filteredEvents = events;
                } else {
                    ArrayList<Event> filteredList = new ArrayList<>();
                    for (Event event : events) {
                        if (event.getEventName().toLowerCase().contains(charString) || event.getCity().toLowerCase().contains(charString) || event.getEventCategory().getCategoryName().toLowerCase().contains(charString)) {
                            filteredList.add(event);
                        }
                    }

                    filteredEvents = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredEvents;
                return filterResults;
            }
        };
    }

    @Override
    public int getItemCount() {
        if (filteredEvents == null)
            return 0;
        return filteredEvents.size();
    }

}
