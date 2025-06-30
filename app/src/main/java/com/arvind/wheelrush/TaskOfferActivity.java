package com.arvind.wheelrush;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class TaskOfferActivity extends AppCompatActivity {

    private RecyclerView taskRecyclerView;
    private List<String> offers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_offer);

        taskRecyclerView = findViewById(R.id.taskRecyclerView);
        taskRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Sample task offers
        offers = Arrays.asList(
                "📱 Install App & Earn 20 Coins",
                "📝 Complete Survey & Earn 30 Coins",
                "👥 Invite 5 Friends & Get 100 Coins"
        );

        TaskAdapter adapter = new TaskAdapter(offers);
        taskRecyclerView.setAdapter(adapter);
    }

    // Custom Adapter class for task offers
    private static class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

        private final List<String> offerList;

        public TaskAdapter(List<String> offerList) {
            this.offerList = offerList;
        }

        @NonNull
        @Override
        public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(android.R.layout.simple_list_item_1, parent, false);
            return new TaskViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
            holder.textView.setText(offerList.get(position));
        }

        @Override
        public int getItemCount() {
            return offerList.size();
        }

        static class TaskViewHolder extends RecyclerView.ViewHolder {
            TextView textView;

            public TaskViewHolder(@NonNull View itemView) {
                super(itemView);
                textView = itemView.findViewById(android.R.id.text1);
            }
        }
    }
}
