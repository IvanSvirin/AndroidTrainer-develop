package ru.extremefitness.fitness_trainer.ui;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.idunnololz.widgets.AnimatedExpandableListView;

import ru.extremefitness.fitness_trainer.R;
import ru.extremefitness.fitness_trainer.models.GetGymContainer;
import ru.extremefitness.fitness_trainer.models.RootContainer;

//Viewmodel для загрузки групп мышц

public class ExercisesViewModel {

    private final ExercisesActivity context;
    private SwipeRefreshLayout rootView;
    private AnimatedExpandableListView listView;
    private TextView emptyView;
    ExpListAdapter expListAdapter;
    GetGymContainer.GymInfo gymInfo;


    public void setError(final String error) {
        emptyView.setText(error);
    }

    enum ViewModes {
        PROGRESS {
            @Override
            void apply(ExercisesViewModel model) {
                model.showProgress();
            }
        },
        CONTENT {
            @Override
            void apply(ExercisesViewModel model) {
                model.showContent();
            }
        },
        ERROR {
            @Override
            void apply(ExercisesViewModel model) {
                model.showError();
            }
        };

        abstract void apply(final ExercisesViewModel model);
    }

    ExercisesViewModel(final ExercisesActivity context) {
        this.context = context;
        prepareGui();
    }

    private void prepareGui() {
        rootView = (SwipeRefreshLayout) View.inflate(context, R.layout.screen_videoa, null);
        listView = (AnimatedExpandableListView) rootView.findViewById(android.R.id.list);


        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                if (listView.isGroupExpanded(i)) {
                    listView.collapseGroupWithAnimation(i);
                } else {
                    listView.expandGroupWithAnimation(i);
                }
                return true;
            }
        });


        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                GetGymContainer.Musculs musculsId = expListAdapter.getChild(groupPosition, childPosition);
                GetGymContainer.Exercises[] exercises = musculsId.getExercises();
                GetGymContainer.ExercisesInfo[] exercisesInfo = gymInfo.getExercisesInfo();
                GetGymContainer.ExercisesInfo[] TransferData = new GetGymContainer.ExercisesInfo[musculsId.getExercises().length];

                try {

                    for (int i = 0; i < exercises.length; ++i) {

                        for (GetGymContainer.ExercisesInfo info : exercisesInfo) {
                            if (info.getId() == exercises[i].getIdexercise()) {

                                TransferData[i] = info;
                                break;

                            }
                        }

                    }


                } catch (Exception e) {
                    System.out.println("Error" + e);
                }


                Intent intent = new Intent(context, UnderExercisesActivity.class);


                intent.putExtra("id", musculsId.getId());
                intent.putExtra("name", musculsId.getName());
                intent.putExtra("exercises_info", TransferData);

                context.startActivity(intent);


                return true;
            }
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int topRowVerticalPosition =
                        (listView == null || listView.getChildCount() == 0) ?
                                0 : listView.getChildAt(0).getTop();
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

        rootView.setColorSchemeResources(R.color.blue, R.color.green, R.color.yellow, R.color.red);

        assert null != listView;


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
        listView.setVisibility(View.GONE);

    }

    private void showContent() {
        rootView.setRefreshing(false);
        listView.setVisibility(View.VISIBLE);
    }

    private void showError() {
        rootView.setRefreshing(false);
        listView.setVisibility(View.GONE);
        showSearchEmpty(true);
    }

    private void showSearchEmpty(final boolean isShow) {
        if (isShow) {
            emptyView.setVisibility(View.VISIBLE);
        } else {
            emptyView.setVisibility(View.GONE);
        }
    }


    void setContent(final RootContainer result) {
        gymInfo = result.getData();
        expListAdapter = new ExpListAdapter(context, gymInfo.getBody_parts(), gymInfo.getExercisesInfo());
        listView.setAdapter(expListAdapter);
        expListAdapter.notifyDataSetChanged();

        System.out.println(expListAdapter.isChildSelectable(0, 0));


    }


}
