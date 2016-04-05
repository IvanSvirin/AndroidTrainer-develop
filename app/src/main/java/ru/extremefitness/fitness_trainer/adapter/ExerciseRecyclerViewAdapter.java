package ru.extremefitness.fitness_trainer.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.parceler.Parcels;

import java.util.List;

import ru.extremefitness.fitness_trainer.R;
import ru.extremefitness.fitness_trainer.activity.ExerciseActivity;
import ru.extremefitness.fitness_trainer.models.Exercise;
import ru.extremefitness.fitness_trainer.models.ExerciseSet;

/**
 * Created by Osipova Ekaterina on 23.02.2016.
 */
public class ExerciseRecyclerViewAdapter extends RecyclerView.Adapter<ExerciseRecyclerViewAdapter
        .ViewHolder> {

    List<ExerciseSet> exerciseSets;

    public ExerciseRecyclerViewAdapter(List<ExerciseSet> exerciseSets) {
        this.exerciseSets = exerciseSets;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .item_exercises, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindItem(exerciseSets.get(position));
    }

    @Override
    public int getItemCount() {
        return exerciseSets.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final View itemView;
        final TextView exerciseName;
        final TextView exerciseSet;
        ExerciseSet exercise;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            exerciseName = (TextView) itemView.findViewById(R.id.exercise_name);
            exerciseSet = (TextView) itemView.findViewById(R.id.exercise_set);
            itemView.setOnClickListener(this);
        }

        public void bindItem(ExerciseSet exercise) {
            this.exercise = exercise;
            exerciseName.setText(exercise.getExercise().getName());
            exerciseSet.setText(exercise.getSet().toString());
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), ExerciseActivity.class);
            intent.putExtra(Exercise.EXERCISE_EXTRA, Parcels.wrap(exercise.getExercise()));
            v.getContext().startActivity(intent);
        }
    }
}
