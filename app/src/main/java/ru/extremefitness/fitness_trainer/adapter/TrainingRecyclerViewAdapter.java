package ru.extremefitness.fitness_trainer.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.parceler.Parcels;

import java.util.List;

import ru.extremefitness.fitness_trainer.AdapterItemSelectedListener;
import ru.extremefitness.fitness_trainer.R;
import ru.extremefitness.fitness_trainer.activity.ExercisesActivity;
import ru.extremefitness.fitness_trainer.models.Training;

/**
 * Created by Osipova Ekaterina on 23.02.2016.
 */
public class TrainingRecyclerViewAdapter extends RecyclerView.Adapter<TrainingRecyclerViewAdapter
        .ViewHolder> {

    List<Training> trainings;
    AdapterItemSelectedListener<Training> listener;

    public TrainingRecyclerViewAdapter(List<Training> trainings,
                                       AdapterItemSelectedListener<Training> listener) {
        super();
        this.trainings = trainings;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .item_trainings, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindItem(trainings.get(position), position);
    }

    @Override
    public int getItemCount() {
        return trainings.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final View itemView;
        final TextView trainingDay;
        final TextView trainingDesc;
        Training training;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            trainingDay = (TextView) itemView.findViewById(R.id.training_day);
            trainingDesc = (TextView) itemView.findViewById(R.id.training_desc);
            itemView.setOnClickListener(this);
        }

        public void bindItem(Training training, int position) {
            this.training = training;
            trainingDay.setText(training.getName());
            trainingDesc.setText(training.isRest() ? "Отдых" : "");
        }

        @Override
        public void onClick(View v) {
            listener.onItemSelected(training);
        }
    }
}
