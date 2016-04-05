package ru.extremefitness.fitness_trainer.ui;

import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import ru.extremefitness.fitness_trainer.R;
import ru.extremefitness.fitness_trainer.network.ModelsEnum;
import ru.extremefitness.fitness_trainer.network.NetworkDispatcher;

/**
 * Created by Comp on 18.08.2015.
 */
public class VideosActivity extends AppCompatActivity {

    private VideosViewModel viewModel;
    private VideosViewModel.ViewModes currentMode = VideosViewModel.ViewModes.CONTENT;

    public static void start(final Context context) {
        final Intent intent = new Intent(context, VideosActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new VideosViewModel(this);
        setContentView(viewModel.getView());

        if (savedInstanceState == null) {
            loadList();
        }

        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(final Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(final Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            viewModel.update(query);
        }
    }

    void loadList() {
        NetworkDispatcher.invoke(ModelsEnum.VIDEO_INFO);
        currentMode = VideosViewModel.ViewModes.PROGRESS;
        currentMode.apply(viewModel);
    }

    @Override
    protected void onResume() {
        super.onResume();

        registerReceiver(loadReceiver,loadReceiver.intentFilter);

        currentMode.apply(viewModel);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(loadReceiver);
    }

    private LoadReceiver loadReceiver = new LoadReceiver();

    private final class LoadReceiver extends BroadcastReceiver {

        private IntentFilter intentFilter;

        LoadReceiver() {
            intentFilter = new IntentFilter();
            intentFilter.addAction(NetworkDispatcher.ACTION_LOAD_DONE);
            intentFilter.addAction(NetworkDispatcher.ACTION_LOAD_ERROR);
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            if(NetworkDispatcher.ACTION_LOAD_DONE.equals(intent.getAction())) {
                viewModel.setContent(NetworkDispatcher.getResult());
                currentMode = VideosViewModel.ViewModes.CONTENT;
                currentMode.apply(viewModel);
            } else if (NetworkDispatcher.ACTION_LOAD_ERROR.equals(intent.getAction())) {
                viewModel.setError(intent.getStringExtra(NetworkDispatcher.EXTRA_ERROR));
                currentMode = VideosViewModel.ViewModes.ERROR;
                currentMode.apply(viewModel);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        final MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_vieos, menu);

        // Associate searchable configuration with the SearchView
        final SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final MenuItem item = menu.findItem(R.id.search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                viewModel.update(newText);
                return false;
            }
        });


        return true;
    }
}
