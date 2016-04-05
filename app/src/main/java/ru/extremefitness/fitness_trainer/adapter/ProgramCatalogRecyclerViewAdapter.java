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
import ru.extremefitness.fitness_trainer.activity.ProgramActivity;
import ru.extremefitness.fitness_trainer.models.Program;

/**
 * Created by Osipova Ekaterina on 24.02.2016.
 */
public class ProgramCatalogRecyclerViewAdapter extends RecyclerView.Adapter<ProgramCatalogRecyclerViewAdapter
        .ViewHolder> {

    List<Program> programs;

    public ProgramCatalogRecyclerViewAdapter(List<Program> programs) {
        super();
        this.programs = programs;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .item_programs_catalog, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindItem(programs.get(position));
    }

    @Override
    public int getItemCount() {
        return programs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final View itemView;
        final TextView name;
        final TextView about;
        final TextView time;
        Program program;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            name = (TextView) itemView.findViewById(R.id.program_name);
            about = (TextView) itemView.findViewById(R.id.program_about);
            time = (TextView) itemView.findViewById(R.id.program_time);
            itemView.setOnClickListener(this);
        }

        public void bindItem(Program program) {
            this.program = program;
            name.setText(program.getName());
            about.setText(program.getAbout());
            time.setText(time.getContext().getString(R.string.days, program.getTime()));
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), ProgramActivity.class);
            intent.putExtra(Program.PROGRAM_EXTRA, Parcels.wrap(program));
            intent.putExtra(ProgramActivity.EDITABLE_EXTRA, false);
            v.getContext().startActivity(intent);
        }
    }
}
