package ru.extremefitness.fitness_trainer.ui;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.nhaarman.listviewanimations.appearance.simple.AlphaInAnimationAdapter;

import ru.extremefitness.fitness_trainer.R;
import ru.extremefitness.fitness_trainer.models.GetGymContainer;

/**
 * Created by Александр on 18.11.2015.
 */

//Viewmodel для загрузки упражнений
public class UnderExercisesViewModel {


    private final UnderExercisesActivity context;
    private SwipeRefreshLayout rootView;
    private ListView listView1;
    private GetGymContainer.ExercisesInfo[] exercisesInfo;
    private TextView emptyView;

    public void setError(final String error) {
        emptyView.setText(error);
    }

    enum ViewModes {
        PROGRESS {
            @Override
            void apply(UnderExercisesViewModel model) {
                model.showProgress();
            }
        },
        CONTENT {
            @Override
            void apply(UnderExercisesViewModel model) {
                model.showContent();
            }
        },
        ERROR {
            @Override
            void apply(UnderExercisesViewModel model) {
                model.showError();
            }
        };

        abstract void apply(final UnderExercisesViewModel model);
    }

    UnderExercisesViewModel(final UnderExercisesActivity context) {
        this.context = context;
        prepareGui();
    }


    private void prepareGui() {
        rootView = (SwipeRefreshLayout) View.inflate(context, R.layout.screen_videos, null);
        listView1 = (ListView) rootView.findViewById(android.R.id.list);


        listView1.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int topRowVerticalPosition =
                        (listView1 == null || listView1.getChildCount() == 0) ?
                                0 : listView1.getChildAt(0).getTop();
                rootView.setEnabled(topRowVerticalPosition >= 0);
            }
        });

        emptyView = (TextView) rootView.findViewById(R.id.empty_text);

        rootView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        context.loadList();
                    }
                });
            }
        });

        assert null != listView1;

    }

    View getView() {
        return rootView;
    }

    private void showProgress() {
        rootView.post(new Runnable() {
            @Override
            public void run() {
                rootView.setRefreshing(true);
            }
        });
        listView1.setVisibility(View.GONE);

    }

    private void showContent() {
        rootView.setRefreshing(false);
        listView1.setVisibility(View.VISIBLE);
    }

    private void showError() {
        rootView.setRefreshing(false);
        listView1.setVisibility(View.GONE);
    }


    void setContent(final GetGymContainer.ExercisesInfo[] exercisesInfoResult) {

        exercisesInfo = exercisesInfoResult;

        MusculsAdapter mAdapter = new MusculsAdapter();
        AlphaInAnimationAdapter animationAdapter = new AlphaInAnimationAdapter(mAdapter);
        animationAdapter.setAbsListView(listView1);
        listView1.setAdapter(animationAdapter);
        mAdapter.notifyDataSetChanged();
    }


    private class MusculsAdapter extends ArrayAdapter<GetGymContainer.ExercisesInfo> {

        private MusculsAdapter() {

            super(context, 0, exercisesInfo);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (null == convertView) {
                convertView = View.inflate(context, R.layout.list_item_group_exercises, null);
                final Holder holder = new Holder();
                holder.title = (TextView) convertView.findViewById(R.id.tvName);
                holder.sets = (TextView) convertView.findViewById(R.id.tvSets);
                holder.reps = (TextView) convertView.findViewById(R.id.tvReps);
                holder.weight = (TextView) convertView.findViewById(R.id.tvWeight);

                convertView.setTag(holder);
            }

            final GetGymContainer.ExercisesInfo exer = getItem(position);

            final Holder holder = (Holder) convertView.getTag();


            holder.title.setText(exer.getName());

            /*if (exer.getMax_sets() == 1) {
                holder.sets.setText(String.valueOf(exer.getMax_sets()) + "сет");
                if (exer.getMax_sets() == 3) {
                    holder.sets.setText(String.valueOf(exer.getMax_sets()) + "сета");
                } else if (exer.getMax_sets() == 5) {
                    holder.sets.setText(String.valueOf(exer.getMax_sets()) + "сетов");
                } else {
                    exer.getMax_sets();
                }*/

            //Convert to Integer
            Double d = exer.getMax_sets();
            Double e = exer.getMax_reps();
            Double f = exer.getMax_weight();

            int i = d.intValue();
            int j = e.intValue();
            int k = f.intValue();


                holder.sets.setText(String.valueOf(i) + " сетов x");
                holder.reps.setText(String.valueOf(j) + " повторений");
                holder.weight.setText(String.valueOf(k) + " кг");

//            holder.text.setText(exer.getInfoAbout());



            return convertView;
        }

        private class Holder {
            private TextView title;
            private TextView sets;
            private TextView reps;
            private TextView weight;
        }
    }
}
