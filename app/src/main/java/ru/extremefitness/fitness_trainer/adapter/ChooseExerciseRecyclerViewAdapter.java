package ru.extremefitness.fitness_trainer.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ru.extremefitness.fitness_trainer.R;
import ru.extremefitness.fitness_trainer.AdapterItemSelectedListener;
import ru.extremefitness.fitness_trainer.models.Exercise;

/**
 * Created by Osipova Ekaterina on 4.03.2016.
 */
public class ChooseExerciseRecyclerViewAdapter extends RecyclerView.Adapter<ChooseExerciseRecyclerViewAdapter
        .ViewHolder> {

    List<Exercise> exercises;
    AdapterItemSelectedListener<Exercise> listener;

    public ChooseExerciseRecyclerViewAdapter(List<Exercise> exercises,
                                             AdapterItemSelectedListener<Exercise> listener) {
        this.exercises = exercises;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .item_choose_exercises, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindItem(exercises.get(position));
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final View itemView;
        final TextView exerciseName;
        Exercise exercise;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            exerciseName = (TextView) itemView.findViewById(R.id.exercise_name);
            itemView.setOnClickListener(this);
        }

        public void bindItem(Exercise exercise) {
            this.exercise = exercise;
            exerciseName.setText(exercise.getName());
        }

        @Override
        public void onClick(View v) {
            listener.onItemSelected(exercise);
        }
    }
}
